package nl.gertontenham.magnolia.templating.servlets;

import com.google.common.net.HttpHeaders;
import freemarker.template.TemplateException;
import info.magnolia.context.MgnlContext;
import info.magnolia.freemarker.FreemarkerHelper;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.module.resources.ResourceLinker;
import info.magnolia.resourceloader.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;

/**
 * Servlet endpoint for templates processed by Freemarker engine.
 */
public class FreemarkerTemplatesResourcesServlet extends StaticResourcesServlet {

    private static final Logger log = LoggerFactory.getLogger(FreemarkerTemplatesResourcesServlet.class);
    private final FreemarkerHelper fmHelper;
    private final SimpleTranslator simpleTranslator;

    @Inject
    public FreemarkerTemplatesResourcesServlet(FreemarkerHelper fmHelper, ResourceLinker linker, SimpleTranslator simpleTranslator) {
        super(linker);
        this.fmHelper = fmHelper;
        this.simpleTranslator = simpleTranslator;
    }

    public FreemarkerHelper getFmHelper() {
        return fmHelper;
    }

    @Override
    protected void serveResource(HttpServletResponse response, Resource resource) throws IOException {
        response.setDateHeader(HttpHeaders.LAST_MODIFIED, resource.getLastModified());

        try {
            // Fetch template through Freemarker engine (server side parsing)
            renderFreemarker(response.getWriter(), resource);

        } catch (TemplateException e) {
            log.error("Error during rendering freemarker template", e);
        } catch (IOException e) {
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    protected void renderFreemarker(Writer writer, Resource resource) throws IOException, TemplateException {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("i18n", simpleTranslator);
        fmHelper.render(resource.openReader(), map, writer);
    }

    private Locale checkLocale(Locale locale) {
        if (locale != null) {
            return locale;
        } else if (MgnlContext.hasInstance()) {
            return MgnlContext.getLocale();
        } else {
            return Locale.getDefault();
        }
    }
}
