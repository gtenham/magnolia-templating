package nl.gertontenham.magnolia.templating.servlets;

import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.helper.PrecompileHelper;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import freemarker.template.TemplateException;
import info.magnolia.freemarker.FreemarkerHelper;
import nl.gertontenham.magnolia.templating.utils.HandlebarsPrecompileHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gtenham on 2015-07-11.
 */
public class HandlebarsTemplatesResourcesServlet extends StaticResourcesServlet {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsTemplatesResourcesServlet.class);
    private final FreemarkerHelper fmHelper;

    protected String suffix;
    protected String jswrapper;

    @Inject
    public HandlebarsTemplatesResourcesServlet(FreemarkerHelper fmHelper) {
        this.fmHelper = fmHelper;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        suffix = StringUtils.defaultIfEmpty(getInitParameter("suffix"), ".hbs");
        jswrapper = StringUtils.defaultIfEmpty(getInitParameter("jswrapper"), "anonymous");
    }

    /**
     * All handlebars templates requests are handled here.
     * @throws java.io.IOException for error in accessing the resource or the servlet output stream
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        StringWriter freemarkerWriter = new StringWriter();

        String filePath = getFilePath(request);
        TemplateLoader loader = new ClassPathTemplateLoader(resourcesRoot, suffix);
        Handlebars handlebars = new Handlebars(loader);

        try {

            // Fetch template through Freemarker engine (server side parsing)
            fmHelper.render(resourcesRoot + StringUtils.substringBeforeLast(filePath, ".") + suffix, map, freemarkerWriter);

            // Compile freemarker parsed string as Inline handlebars template
            Template template = handlebars.compileInline(freemarkerWriter.toString());
            freemarkerWriter.flush();

            Context nullContext = Context.newContext(null);

            // Set Javascript wrapper: "none", "anonymous" and "amd"
            Map<String, Object> hash = new HashMap<String, Object>();
            hash.put("wrapper", StringUtils.lowerCase(jswrapper));

            String js = HandlebarsPrecompileHelper.INSTANCE.applyWithCurrentTemplate(filePath,
                    new Options
                            .Builder(handlebars, PrecompileHelper.NAME, TagType.VAR, nullContext, template)
                            .setHash(hash)
                            .build()).toString();

            response.setContentType("text/javascript");
            response.getWriter().print(js);

        } catch (FileNotFoundException e) {
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (TemplateException e) {
            log.error("Error during rendering freemarker template", e);
        } finally {
            freemarkerWriter.close();
        }

    }


}
