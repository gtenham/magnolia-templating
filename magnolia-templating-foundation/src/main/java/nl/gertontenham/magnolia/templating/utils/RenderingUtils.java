package nl.gertontenham.magnolia.templating.utils;

import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.registry.RegistrationException;
import info.magnolia.rendering.engine.RenderException;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.renderer.Renderer;
import info.magnolia.rendering.renderer.RenderingModelBasedRenderer;
import info.magnolia.rendering.renderer.registry.RendererRegistry;
import info.magnolia.rendering.template.RenderableDefinition;
import info.magnolia.rendering.template.TemplateDefinition;
import info.magnolia.rendering.template.assignment.TemplateDefinitionAssignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.servlet.ServletException;

/**
 * Created by gtenham on 2015-04-07.
 */
public class RenderingUtils {

    private static final Logger log = LoggerFactory.getLogger(RenderingUtils.class);

    private RendererRegistry rendererRegistry;

    private TemplateDefinitionAssignment templateDefinitionAssignment;

    @Inject
    public RenderingUtils(RendererRegistry rendererRegistry, TemplateDefinitionAssignment templateDefinitionAssignment) {
        this.rendererRegistry = rendererRegistry;
        this.templateDefinitionAssignment = templateDefinitionAssignment;
    }

    public RenderingModel getRenderingModel(Node node) throws ServletException {

        TemplateDefinition templateDefinition = getTemplateDefinition(node);

        RenderingModelBasedRenderer renderingModelBasedRenderer = getRenderingModelBasedRenderer(templateDefinition);

        RenderingModel renderingModel;
        try {
            renderingModel = renderingModelBasedRenderer.newModel(node, templateDefinition, null);
        }
        catch (RenderException e) {
            throw new ServletException(e.getMessage(), e);
        }

        return renderingModel;

    }

    /**
     * Returns the TemplateDefinition for the supplied content. Never returns null.
     */
    private TemplateDefinition getTemplateDefinition(Node content) throws ServletException {

        TemplateDefinition templateDefinition;
        try {
            templateDefinition = templateDefinitionAssignment.getAssignedTemplateDefinition(content);
        } catch (RegistrationException e) {
            throw new ServletException("No template set or template not available for node with identifier: " + NodeUtil.getNodeIdentifierIfPossible(content));
        }

        if (templateDefinition == null) {
            throw new ServletException("Template not available for node with identifier: " + NodeUtil.getNodeIdentifierIfPossible(content));
        }

        return templateDefinition;
    }

    /**
     * Returns the Renderer for the supplied renderable if it supports RenderingModel. Never returns null.
     * @throws IllegalArgumentException if there is no renderer registered for the renderable
     * @throws ServletException if the renderer does not support RenderingModel
     */
    private RenderingModelBasedRenderer getRenderingModelBasedRenderer(RenderableDefinition renderableDefinition) throws ServletException {

        Renderer renderer;
        try {
            renderer = rendererRegistry.getRenderer(renderableDefinition.getRenderType());
        } catch (RegistrationException e) {
            throw new ServletException(e);
        }

        if (!(renderer instanceof RenderingModelBasedRenderer)) {
            throw new ServletException("Renderer [" + renderableDefinition.getRenderType() + "] does not support RenderingModel");
        }

        return (RenderingModelBasedRenderer) renderer;
    }
}
