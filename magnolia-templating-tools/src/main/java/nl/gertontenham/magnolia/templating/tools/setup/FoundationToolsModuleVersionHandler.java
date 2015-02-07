package nl.gertontenham.magnolia.templating.tools.setup;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.CheckAndModifyPropertyValueTask;
import info.magnolia.module.delta.Task;
import info.magnolia.repository.RepositoryConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is optional and lets you manager the versions of your module,
 * by registering "deltas" to maintain the module's configuration, or other type of content.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 */
public class FoundationToolsModuleVersionHandler extends DefaultModuleVersionHandler {


    @Override
    protected List<Task> getExtraInstallTasks(InstallContext ctx) {
        final List<Task> tasks = new ArrayList<Task>();
        tasks.addAll(super.getExtraInstallTasks(ctx));

        tasks.add(new CheckAndModifyPropertyValueTask("Use different URI2Repository manager", "Updates URI2RepositoryManager", RepositoryConstants.CONFIG, "/server/URI2RepositoryMapping",
                "class", "info.magnolia.cms.beans.config.URI2RepositoryManager", "nl.gertontenham.magnolia.templating.tools.managers.SiteURI2RepositoryManager"));

        return tasks;
    }

}