package com.github.czyzby.setup.data.libs.unofficial

import com.github.czyzby.setup.data.platforms.Core
import com.github.czyzby.setup.data.platforms.GWT
import com.github.czyzby.setup.data.platforms.Headless
import com.github.czyzby.setup.data.project.Project
import com.github.czyzby.setup.views.Extension

/**
 * UI toolkit.
 * @author Kotcrab
 */
@Extension
class VisUI : ThirdPartyExtension() {
    override val id = "visUi"
    override val defaultVersion = "1.3.0"
    override val url = "https://github.com/kotcrab/VisEditor/wiki/VisUI"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.kotcrab.vis:vis-ui")

        addDependency(project, GWT.ID, "com.kotcrab.vis:vis-ui:sources")
        addGwtInherit(project, "com.kotcrab.vis.vis-ui")
    }
}

