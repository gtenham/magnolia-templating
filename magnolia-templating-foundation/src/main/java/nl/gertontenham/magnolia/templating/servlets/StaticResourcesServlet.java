package nl.gertontenham.magnolia.templating.servlets;

import com.google.common.net.HttpHeaders;
import info.magnolia.cms.filters.SelfMappingServlet;
import info.magnolia.cms.util.ClasspathResourcesUtil;
import info.magnolia.module.resources.ResourceLinker;
import info.magnolia.resourceloader.Resource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Servlet endpoint for any web resource under the default path <code>/static-resources</code> or path configured in servlet
 * parameter "resourcesRoot".
 * Note: This ia a copy of the ResourcesServlet in Magnolia 5.4 as this Servlet is hard to extend with its private properties and
 * protected methods.
 */
public class StaticResourcesServlet extends HttpServlet implements SelfMappingServlet {

    private static final Logger log = LoggerFactory.getLogger(StaticResourcesServlet.class);

    /**
     * Default root directory for resources streamed from the classpath. Resources folder is configurable via the servlet <code>resourcesRoot</code> init parameter.
     */
    public static final String DEFAULT_RESOURCES_ROOT = "/magnolia-templating-foundation/static-resources";

    protected String resourcesRoot;
    protected String requestedResourcePath;
    private final ResourceLinker linker;

    @Inject
    public StaticResourcesServlet(ResourceLinker linker) {
        this.linker = linker;
    }

    /**
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
        super.init();
        resourcesRoot = StringUtils.defaultIfEmpty(getInitParameter("resourcesRoot"), DEFAULT_RESOURCES_ROOT);
        //test if the folder is really there, else log warning and fall back to default.
        URL url = ClasspathResourcesUtil.getResource(resourcesRoot);
        log.debug("resources root is {}", resourcesRoot);
        if(url == null) {
            log.warn("Resource classpath root {} does not seem to exist. Some resources might not be available, please check your configuration. Falling back to default resources root {}", resourcesRoot, DEFAULT_RESOURCES_ROOT);
            // in case of misconfiguration, this should mitigate the risk of ending up with an unusable Magnolia instance.
            resourcesRoot = DEFAULT_RESOURCES_ROOT;
        }
    }

    @Override
    public String getSelfMappingPath() {
        return linker.getServletMapping();
    }

    /**
     * All static resource get requests are handled here.
     * @throws java.io.IOException for error in accessing the resource or the servlet output stream
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        requestedResourcePath = getResourcePathFromRequest(request);

        if (StringUtils.isBlank(requestedResourcePath)) {
            log.warn("Invalid resource request : {} ", request.getRequestURI());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        final Resource resource = linker.getResource(requestedResourcePath);

        if (resource == null) {
            log.debug("Requested resource not found for path {}", requestedResourcePath);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (resource.isDirectory()) {
            log.warn("Invalid resource request: {} is a directory", request.getRequestURI());
            // send 404 instead to avoid disclosing resource existence ?
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        serveResource(response, resource);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void serveResource(HttpServletResponse response, Resource resource) throws IOException {
        response.setDateHeader(HttpHeaders.LAST_MODIFIED, resource.getLastModified());

        try (InputStream in = resource.openStream(); OutputStream out = response.getOutputStream()) {
            IOUtils.copy(in, out);
            out.flush();
        } catch (IOException e) {
            log.debug("Can't load resource {} : {}", resource, e, e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);

            // tomcat usually throws a ClientAbortException anytime the user stop loading the page
            log.debug("Unable to serve resource due to a {} exception: ", e, e);
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    public String getResourcePathFromRequest(HttpServletRequest request) {
        // handle includes
        String resourcePath = (String) request.getAttribute("javax.servlet.include.path_info");

        // handle forwards
        if (resourcePath == null) {
            resourcePath = (String) request.getAttribute("javax.servlet.forward.path_info");
        }

        // standard request
        if (resourcePath == null) {
            resourcePath = request.getPathInfo();
        }

        return resourcesRoot + resourcePath;
    }

    public ResourceLinker getLinker() {
        return linker;
    }
}
