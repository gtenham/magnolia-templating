package nl.gertontenham.magnolia.templating.servlets;

import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.helper.PrecompileHelper;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.net.HttpHeaders;
import freemarker.template.TemplateException;
import info.magnolia.freemarker.FreemarkerHelper;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.module.resources.ResourceLinker;
import info.magnolia.resourceloader.Resource;
import nl.gertontenham.magnolia.templating.utils.HandlebarsPrecompileHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet endpoint for compiled handlebars templates pre-processed by Freemarker engine.
 */
public class HandlebarsTemplatesResourcesServlet extends FreemarkerTemplatesResourcesServlet {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsTemplatesResourcesServlet.class);
    //private final FreemarkerHelper fmHelper;

    protected String suffix;
    protected String jswrapper;

    @Inject
    public HandlebarsTemplatesResourcesServlet(FreemarkerHelper fmHelper, ResourceLinker linker, SimpleTranslator simpleTranslator) {
        super(fmHelper, linker, simpleTranslator);
        //this.fmHelper = fmHelper;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        suffix = StringUtils.defaultIfEmpty(getInitParameter("suffix"), ".hbs");
        jswrapper = StringUtils.defaultIfEmpty(getInitParameter("jswrapper"), "anonymous");
    }

    @Override
    protected String getResourcePathFromRequest(HttpServletRequest request) {
        return StringUtils.substringBeforeLast(super.getResourcePathFromRequest(request), ".") + suffix;
    }

    @Override
    protected void serveResource(HttpServletResponse response, Resource resource) throws IOException {
        StringWriter freemarkerWriter = new StringWriter();
        TemplateLoader loader = new ClassPathTemplateLoader(resourcesRoot, suffix);
        Handlebars handlebars = new Handlebars(loader);

        response.setDateHeader(HttpHeaders.LAST_MODIFIED, resource.getLastModified());

        try {
            // Fetch template through Freemarker engine (server side parsing)
            renderFreemarker(freemarkerWriter, resource);

            // Compile freemarker parsed string as Inline handlebars template
            Template template = handlebars.compileInline(freemarkerWriter.toString());
            freemarkerWriter.flush();

            Context nullContext = Context.newContext(null);

            // Set Javascript wrapper: "none", "anonymous" and "amd"
            Map<String, Object> hash = new HashMap<String, Object>();
            hash.put("wrapper", StringUtils.lowerCase(jswrapper));

            String js = HandlebarsPrecompileHelper.INSTANCE.applyWithCurrentTemplate(requestedResourcePath,
                    new Options
                            .Builder(handlebars, PrecompileHelper.NAME, TagType.VAR, nullContext, template)
                            .setHash(hash)
                            .build()).toString();

            response.setContentType("text/javascript");
            response.getWriter().print(js);

        } catch (TemplateException e) {
            log.error("Error during rendering freemarker template", e);
        } catch (IOException e) {
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } finally {
            freemarkerWriter.close();
        }
    }
}
