package nl.gertontenham.magnolia.templating.servlets;

import freemarker.template.TemplateException;
import info.magnolia.freemarker.FreemarkerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Servlet endpoint for templates processed by Freemarker engine.
 */
public class FreemarkerTemplatesResourcesServlet extends StaticResourcesServlet {

    private static final Logger log = LoggerFactory.getLogger(FreemarkerTemplatesResourcesServlet.class);
    private final FreemarkerHelper fmHelper;

    @Inject
    public FreemarkerTemplatesResourcesServlet(FreemarkerHelper fmHelper) {
        this.fmHelper = fmHelper;
    }

    /**
     * All freemarker templates requests are handled here.
     * @throws java.io.IOException for error in accessing the resource or the servlet output stream
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String filePath = getFilePath(request);
        try {
            fmHelper.render(resourcesRoot + filePath, map, response.getWriter());
        } catch (TemplateException e) {
            log.error("Error during rendering freemarker template", e);
        } catch (IOException e) {
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
