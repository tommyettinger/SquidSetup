package com.github.czyzby.setup.data.libs.unofficial

import com.github.czyzby.setup.data.platforms.Core
import com.github.czyzby.setup.data.platforms.GWT
import com.github.czyzby.setup.data.project.Project
import com.github.czyzby.setup.views.Extension
import com.jcabi.github.Coordinates
import com.jcabi.github.RtGithub
import java.util.*

//import com.jcabi.github.Coordinates
//import com.jcabi.github.RtGithub
//import java.util.*


/**
 * Version of SquidLib libraries.
 * @author Eben Howard
 * @author Tommy Ettinger
 */
val SQUID_LIB_VERSION = //"3.0.3"
        RtGithub().repos().get(Coordinates.Simple("yellowstonegames", "SquidLib"))
        .commits().iterate(Collections.emptyMap()).first().sha().substring(0, 10)

/**
 * URL of SquidLib libraries.
 * @author Eben Howard
 * @author Tommy Ettinger
 */
const val SQUID_LIB_URL = "https://github.com/yellowstonegames/SquidLib"

const val REPO_PATH = "com.github.yellowstonegames.SquidLib"
//const val REPO_PATH = "com.squidpony"

/**
 * Utilities for grid-based games.
 * @author Eben Howard
 * @author Tommy Ettinger
 */
@Extension()
class SquidLibUtil : ThirdPartyExtension() {
    override val id = "squidLibUtil"
    override var defaultVersion = SQUID_LIB_VERSION
    override val url = SQUID_LIB_URL

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "$REPO_PATH:squidlib-util")

        addDependency(project, GWT.ID, "$REPO_PATH:squidlib-util:sources")
        addGwtInherit(project, "squidlib-util")

        RegExodus().initiate(project)
    }
}

/**
 * Text-based display for roguelike games.
 * @author Eben Howard
 * @author Tommy Ettinger
 */
@Extension()
class SquidLib : ThirdPartyExtension() {
    override val id = "squidLib"
    override var defaultVersion = SQUID_LIB_VERSION
    override val url = SQUID_LIB_URL

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "$REPO_PATH:squidlib")

        addDependency(project, GWT.ID, "$REPO_PATH:squidlib:sources")
        addGwtInherit(project, "squidlib")

        SquidLibUtil().initiate(project)
        Anim8().initiate(project)

        defaultVersion = SQUID_LIB_VERSION
    }
}

/**
 * Extra save/load support for SquidLib objects.
 * @author Eben Howard
 * @author Tommy Ettinger
 */
@Extension()
class SquidLibExtra : ThirdPartyExtension() {
    override val id = "squidLibExtra"
    override var defaultVersion = SQUID_LIB_VERSION
    override val url = SQUID_LIB_URL

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "$REPO_PATH:squidlib-extra")

        addDependency(project, GWT.ID, "$REPO_PATH:squidlib-extra:sources")
        addGwtInherit(project, "squidlib-extra")

        SquidLibUtil().initiate(project)
        defaultVersion = SQUID_LIB_VERSION
    }
}
