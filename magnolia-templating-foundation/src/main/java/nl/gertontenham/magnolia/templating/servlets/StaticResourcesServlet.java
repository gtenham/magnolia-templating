package nl.gertontenham.magnolia.templating.servlets;

import info.magnolia.cms.util.ClasspathResourcesUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by gtenham on 2015-01-08.
 */
public class StaticResourcesServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(StaticResourcesServlet.class);

    /**
     * Default root directory for resources streamed from the classpath. Resources folder is configurable via the servlet <code>resourcesRoot</code> init parameter.
     */
    public static final String MTF_DEFAULT_RESOURCES_ROOT = "/static-resources";

    protected String resourcesRoot;

    /**
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
        super.init();
        resourcesRoot = StringUtils.defaultIfEmpty(getInitParameter("resourcesRoot"), MTF_DEFAULT_RESOURCES_ROOT);
        //test if the folder is really there, else log warning and fall back to default.
        URL url = ClasspathResourcesUtil.getResource(resourcesRoot);
        log.debug("resources root is {}", resourcesRoot);
        if(url == null) {
            log.warn("Resource classpath root {} does not seem to exist. Some resources might not be available, please check your configuration. Falling back to default resources root {}", resourcesRoot, MTF_DEFAULT_RESOURCES_ROOT);
            // in case of misconfiguration, this should mitigate the risk of ending up with an unusable Magnolia instance.
            resourcesRoot = MTF_DEFAULT_RESOURCES_ROOT;
        }
    }

    /**
     * All static resource requests are handled here.
     * @throws java.io.IOException for error in accessing the resource or the servlet output stream
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String filePath = getFilePath(request);

        streamSingleFile(request, response, filePath);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void streamSingleFile(HttpServletRequest request, HttpServletResponse response, String filePath) throws IOException {
        InputStream in = null;
        // this method caches content if possible and checks the magnolia.develop property to avoid
        // caching during the development process
        try {
            in = ClasspathResourcesUtil.getStream(resourcesRoot + filePath);
        }
        catch (IOException e) {
            IOUtils.closeQuietly(in);
        }

        if (in == null) {
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            return;
        }

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();

            IOUtils.copy(in, out);
            out.flush();
        }
        catch (IOException e) {
            // only log at debug level
            // tomcat usually throws a ClientAbortException anytime the user stop loading the page
            log.debug("Unable to spool resource due to a {} exception", e.getClass().getName());
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    }

    protected String getFilePath(HttpServletRequest request) {
        // handle includes
        String filePath = (String) request.getAttribute("javax.servlet.include.path_info");

        // handle forwards
        if (StringUtils.isEmpty(filePath)) {
            filePath = (String) request.getAttribute("javax.servlet.forward.path_info");
        }

        // standard request
        if (StringUtils.isEmpty(filePath)) {
            filePath = request.getPathInfo();
        }
        return filePath;
    }

}
