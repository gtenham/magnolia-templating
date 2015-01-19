package nl.gertontenham.magnolia.templating.setup;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.Task;
import info.magnolia.module.inplacetemplating.setup.TemplatesInstallTask;
import info.magnolia.module.model.Version;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is optional and lets you manager the versions of your module,
 * by registering "deltas" to maintain the module's configuration, or other type of content.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 */
public class FoundationTemplatingModuleVersionHandler extends DefaultModuleVersionHandler {

    private final static String MODULE_NAME = "magnolia-templating-foundation";
    private final static String DEFAULT_MOD_PATH = "/magnolia-templating-foundation/";

    @Override
    protected List<Task> getExtraInstallTasks(InstallContext ctx) {
        final List<Task> tasks = new ArrayList<Task>();
        tasks.addAll(super.getExtraInstallTasks(ctx));

        tasks.addAll(getGenericTasks());

        return tasks;
    }

    @Override
    protected List<Task> getDefaultUpdateTasks(Version forVersion) {
        final List<Task> tasks = new ArrayList<Task>();
        tasks.addAll(super.getDefaultUpdateTasks(forVersion));

        // Always update templates, resources no matter what version is updated!
        tasks.addAll(getGenericTasks());
        //tasks.add(new UpdateModuleBootstrapTask(MODULE_NAME, "apps, dialogs, templates"));

        return tasks;
    }

    protected List<Task> getGenericTasks() {
        final List<Task> tasks = new ArrayList<Task>();
        tasks.add(new TemplatesInstallTask(DEFAULT_MOD_PATH+".*\\.ftl", true));
        //tasks.add(new ModuleDependencyBootstrapTask("/mgnl-bootstrap/optional", "tricode-tags"));

        return tasks;
    }
}