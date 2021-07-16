package com.github.czyzby.setup.data.libs.unofficial

import com.github.czyzby.setup.data.files.CopiedFile
import com.github.czyzby.setup.data.files.path
import com.github.czyzby.setup.data.libs.Library
import com.github.czyzby.setup.data.libs.official.Controllers
import com.github.czyzby.setup.data.platforms.*
import com.github.czyzby.setup.data.project.Project
import com.github.czyzby.setup.views.Extension

/**
 * Abstract base for unofficial extensions.
 * @author MJ
 */
abstract class ThirdPartyExtension : Library {
    override val official = false

    override fun initiate(project: Project) {
        project.properties[id + "Version"] = project.extensions.getVersion(id)
        initiateDependencies(project)
    }

    abstract fun initiateDependencies(project: Project)

    override fun addDependency(project: Project, platform: String, dependency: String) {
        if(dependency.count { it == ':' } > 1) {
            super.addDependency(project, platform, dependency.substringBeforeLast(':') + ":\$${id}Version:" + dependency.substringAfterLast(':'))
        } else {
            super.addDependency(project, platform, dependency + ":\$${id}Version")
        }
    }

    fun addExternalDependency(project: Project, platform: String, dependency: String) {
        super.addDependency(project, platform, dependency)
    }
}

/**
 * A high performance Entity-Component-System framework.
 * If you target GWT, this setup tool handles some of this library's complicated steps for you.
 * @author junkdog
 */
@Extension
class ArtemisOdb : ThirdPartyExtension() {
    override val id = "artemisOdb"
    override val defaultVersion = "2.3.0"
    override val url = "https://github.com/junkdog/artemis-odb"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "net.onedaybeard.artemis:artemis-odb");

        addDependency(project, GWT.ID, "net.onedaybeard.artemis:artemis-odb-gwt")
        addDependency(project, GWT.ID, "net.onedaybeard.artemis:artemis-odb-gwt:sources")
        addDependency(project, GWT.ID, "net.onedaybeard.artemis:artemis-odb:sources")
        addGwtInherit(project, "com.artemis.backends.artemis_backends_gwt")
        if (project.hasPlatform(GWT.ID)) {
            project.files.add(
                CopiedFile(
                    projectName = GWT.ID,
                    original = path("generator", GWT.ID, "jsr305.gwt.xml"),
                    path = path("src", "main", "java", "jsr305.gwt.xml")
                )
            )
            addGwtInherit(project, "jsr305")
        }
    }
}

/**
 * General libGDX utilities.
 * @author Dermetfan
 * @author Maintained by Tommy Ettinger
 */
@Extension
class LibgdxUtils : ThirdPartyExtension() {
    override val id = "utils"
    override val defaultVersion = "0.13.7"
    override val url = "https://github.com/tommyettinger/gdx-utils"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.tommyettinger:libgdx-utils")

        addDependency(project, GWT.ID, "com.github.tommyettinger:libgdx-utils:sources")
        addGwtInherit(project, "libgdx-utils")
    }
}

/**
 * Box2D libGDX utilities.
 * @author Dermetfan
 * @author Maintained by Tommy Ettinger
 */
@Extension
class LibgdxUtilsBox2D : ThirdPartyExtension() {
    override val id = "utilsBox2d"
    override val defaultVersion = "0.13.7"
    override val url = "https://github.com/tommyettinger/gdx-utils"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.tommyettinger:libgdx-utils-box2d")

        addDependency(project, GWT.ID, "com.github.tommyettinger:libgdx-utils-box2d:sources")
        addGwtInherit(project, "libgdx-utils-box2d")

        LibgdxUtils().initiate(project)
    }
}

/**
 * Facebook graph API wrapper. iOS-incompatible! Also, out-of-date.
 * @author Tom Grill
 */
@Extension
class Facebook : ThirdPartyExtension() {
    override val id = "facebook"
    override val defaultVersion = "1.5.0"
    override val url = "https://github.com/TomGrill/gdx-facebook"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "de.tomgrill.gdxfacebook:gdx-facebook-core")

        addDependency(project, Android.ID, "de.tomgrill.gdxfacebook:gdx-facebook-android")

        addDesktopDependency(project, "de.tomgrill.gdxfacebook:gdx-facebook-desktop")
//// This is a problem for the App Store, removed.
//        addDependency(project, iOS.ID, "de.tomgrill.gdxfacebook:gdx-facebook-ios")

        addDependency(project, GWT.ID, "de.tomgrill.gdxfacebook:gdx-facebook-core:sources")
        addDependency(project, GWT.ID, "de.tomgrill.gdxfacebook:gdx-facebook-html")
        addDependency(project, GWT.ID, "de.tomgrill.gdxfacebook:gdx-facebook-html:sources")
        addGwtInherit(project, "de.tomgrill.gdxfacebook.html.gdx_facebook_gwt")
    }
}

/**
 * Native dialogs support.
 * @author Tom Grill
 */
@Extension
class Dialogs : ThirdPartyExtension() {
    override val id = "dialogs"
    override val defaultVersion = "1.3.0"
    override val url = "https://github.com/TomGrill/gdx-dialogs"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "de.tomgrill.gdxdialogs:gdx-dialogs-core")

        addDependency(project, Android.ID, "de.tomgrill.gdxdialogs:gdx-dialogs-android")

        addDesktopDependency(project, "de.tomgrill.gdxdialogs:gdx-dialogs-desktop")

        addDependency(project, iOS.ID, "de.tomgrill.gdxdialogs:gdx-dialogs-ios")

        addDependency(project, GWT.ID, "de.tomgrill.gdxfacebook:gdx-dialogs-core:sources")
        addDependency(project, GWT.ID, "de.tomgrill.gdxfacebook:gdx-dialogs-html")
        addDependency(project, GWT.ID, "de.tomgrill.gdxfacebook:gdx-dialogs-html:sources")
        addGwtInherit(project, "de.tomgrill.gdxfacebook.html.gdx_dialogs_html")
    }
}

/**
 * In-game console implementation; GWT-incompatible. If you target GWT, you can use JACI GWT.
 * @author StrongJoshua
 */
@Extension
class InGameConsole : ThirdPartyExtension() {
    override val id = "inGameConsole"
    override val defaultVersion = "1.0.0"
    override val url = "https://github.com/StrongJoshua/libgdx-inGameConsole"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.strongjoshua:libgdx-inGameConsole")
    }
}

/**
 * Java Annotation Console Interface. In-game console implementation, for non-GWT usage.
 * If you target GWT, use JaciGwt instead.
 * @author Yevgeny Krasik
 */
@Extension
class Jaci : ThirdPartyExtension() {
    override val id = "jaci"
    override val defaultVersion = "0.4.0"
    override val url = "https://github.com/ykrasik/jaci"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.ykrasik:jaci-libgdx-cli-java")
    }
}

/**
 * Java Annotation Console Interface. GWT-compatible in-game console implementation.
 * Don't use this at the same time as JACI (the non-GWT version).
 * @author Yevgeny Krasik
 */
@Extension
class JaciGwt : ThirdPartyExtension() {
    override val id = "jaciGwt"
    override val defaultVersion = "0.4.0"
    override val url = "https://github.com/ykrasik/jaci"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.ykrasik:jaci-libgdx-cli-gwt")

        addDependency(project, GWT.ID, "com.github.ykrasik:jaci-libgdx-cli-gwt:sources")
        addGwtInherit(project, "com.github.ykrasik.jaci")
    }
}

/**
 * Simple map generators. Noise4J can be used as a continuous noise generator, but you're better served by
 * joise or make-some-noise in that case. There are also many kinds of map generator in squidlib-util.
 * @author MJ
 */
@Extension
class Noise4J : ThirdPartyExtension() {
    override val id = "noise4j"
    override val defaultVersion = "0.1.0"
    override val url = "https://github.com/czyzby/noise4j"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.czyzby:noise4j")

        addDependency(project, GWT.ID, "com.github.czyzby:noise4j:sources")
        addGwtInherit(project, "com.github.czyzby.noise4j.Noise4J")
    }
}

/**
 * Java implementation of Ink language: a scripting language for writing interactive narrative.
 * @author bladecoder
 */
@Extension
class BladeInk : ThirdPartyExtension() {
    override val id = "bladeInk"
    override val defaultVersion = "0.7.4"
    override val url = "https://github.com/bladecoder/blade-ink"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.bladecoder.ink:blade-ink")
    }
}

/**
 * 2D, 3D, 4D and 6D modular noise library written in Java.
 * Joise can combine noise in versatile ways, and can serialize the "recipes" for a particular type of noise generator.
 * @author SudoPlayGames
 */
@Extension
class Joise : ThirdPartyExtension() {
    override val id = "joise"
    override val defaultVersion = "1.1.0"
    override val url = "https://github.com/SudoPlayGames/Joise"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.sudoplay.joise:joise")

        addDependency(project, GWT.ID, "com.sudoplay.joise:joise:sources")
        addGwtInherit(project, "joise")
    }
}

/**
 * Another 2D, 3D, 4D, 5D, and 6D noise library, supporting some unusual types of noise.
 * The API is more "raw" than Joise, and is meant as a building block for things that use noise, rather
 * than something that generates immediately-usable content. It is still a more convenient API than
 * Noise4J when making fields of noise.
 * @author Tommy Ettinger
 */
@Extension
class MakeSomeNoise : ThirdPartyExtension() {
    override val id = "makeSomeNoise"
    override val defaultVersion = "0.2"
    override val url = "https://github.com/tommyettinger/make-some-noise"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.tommyettinger:make_some_noise")

        addDependency(project, GWT.ID, "com.github.tommyettinger:make_some_noise:sources")
        addGwtInherit(project, "make.some.noise")
    }
}

/**
 * An animated Label equivalent that appears as if it was being typed in real time.
 * This is really just a wonderful set of effects for games to have.
 * @author Rafa Skoberg
 */
@Extension
class TypingLabel : ThirdPartyExtension() {
    override val id = "typingLabel"
    override val defaultVersion = "1.2.0"
    override val url = "https://github.com/rafaskb/typing-label"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.rafaskoberg.gdx:typing-label")

        addDependency(project, GWT.ID, "com.rafaskoberg.gdx:typing-label:sources")
        addGwtInherit(project, "com.rafaskoberg.gdx.typinglabel.typinglabel")
        RegExodus().initiate(project)
    }
}

/**
 * A high-performance alternative to libGDX's built-in ShapeRenderer, with smoothing and more shapes.
 * Better in just about every way when compared with ShapeRenderer.
 * @author earlygrey
 */
@Extension
class ShapeDrawer : ThirdPartyExtension() {
    override val id = "shapeDrawer"
    override val defaultVersion = "2.4.0"
    override val url = "https://github.com/earlygrey/shapedrawer"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "space.earlygrey:shapedrawer")

        addDependency(project, GWT.ID, "space.earlygrey:shapedrawer:sources")
        addGwtInherit(project, "space.earlygrey.shapedrawer")
    }
}

/**
 * Provides various frequently-used graph algorithms, aiming to be lightweight, fast, and intuitive.
 * A good substitute for the pathfinding in gdx-ai, but it doesn't include path smoothing or any of the
 * non-pathfinding AI tools in gdx-ai.
 * @author earlygrey
 */
@Extension
class SimpleGraphs : ThirdPartyExtension() {
    override val id = "simpleGraphs"
    override val defaultVersion = "3.0.0"
    override val url = "https://github.com/earlygrey/simple-graphs"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "space.earlygrey:simple-graphs")

        addDependency(project, GWT.ID, "space.earlygrey:simple-graphs:sources")
        addGwtInherit(project, "simple_graphs")
    }
}

/**
 * Provides a replacement for GWT's missing String.format() with its Stringf.format().
 * Only relevant if you target the HTML platform or intend to in the future.
 * @author Tommy Ettinger
 */
@Extension
class Formic : ThirdPartyExtension() {
    override val id = "formic"
    override val defaultVersion = "0.1.4"
    override val url = "https://github.com/tommyettinger/formic"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.tommyettinger:formic")

        addDependency(project, GWT.ID, "com.github.tommyettinger:formic:sources")
        addGwtInherit(project, "formic")
    }
}

/**
 * Alternative color models for changing the colors of sprites and scenes, including brightening.
 * @author Tommy Ettinger
 */
@Extension
class Colorful : ThirdPartyExtension() {
    override val id = "colorful"
    override val defaultVersion = "0.6.0"
    override val url = "https://github.com/tommyettinger/colorful-gdx"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.tommyettinger:colorful")

        addDependency(project, GWT.ID, "com.github.tommyettinger:colorful:sources")
        addGwtInherit(project, "com.github.tommyettinger.colorful.colorful")
    }
}

/**
 * Support for writing animated GIF and animated PNG images from libGDX, as well as 8-bit-palette PNGs.
 * This can be useful for making short captures of gameplay, or making animated characters into GIFs.
 * @author Tommy Ettinger
 */
@Extension
class Anim8 : ThirdPartyExtension() {
    override val id = "anim8"
    override val defaultVersion = "0.2.10"
    override val url = "https://github.com/tommyettinger/anim8-gdx"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.tommyettinger:anim8-gdx")

        addDependency(project, GWT.ID, "com.github.tommyettinger:anim8-gdx:sources")
        addGwtInherit(project, "anim8")
    }
}

/**
 * Bonus features for 9-patch images, filling significant gaps in normal 9-patch functionality.
 * @author Raymond Buckley
 */
@Extension
class TenPatch : ThirdPartyExtension() {
    override val id = "tenPatch"
    override val defaultVersion = "5.0.1"
    override val url = "https://github.com/raeleus/TenPatch"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.raeleus.TenPatch:tenpatch")

        addDependency(project, GWT.ID, "com.github.raeleus.TenPatch:tenpatch:sources")
        addGwtInherit(project, "com.ray3k.tenpatch.tenpatch")
    }
}

/**
 * The libGDX runtime for Spine, a commercial (and very powerful) skeletal-animation editor.
 * You must have a license for Spine to use the runtime in your code.
 * @author Esoteric Software
 */
@Extension
class SpineRuntime : ThirdPartyExtension() {
    override val id = "spineRuntime"
    override val defaultVersion = "3.8.55.1"
    override val url = "https://github.com/EsotericSoftware/spine-runtimes/tree/3.8/spine-libgdx"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.esotericsoftware.spine:spine-libgdx")

        addDependency(project, GWT.ID, "com.esotericsoftware.spine:spine-libgdx:sources")
        addGwtInherit(project, "com.esotericsoftware.spine")
    }
}


/**
 * Legacy: MrStahlfelge's upgrades to controller support, now part of the official controllers extension.
 * This is here so older projects that don't use the official controllers can be ported more easily.
 * Change the version to 1.0.1 if you use libGDX 1.9.10 or earlier!
 * @author MrStahlfelge
 */
@Extension
class ControllerUtils : ThirdPartyExtension() {
    override val id = "controllerUtils"
    override val defaultVersion = "2.2.1"
    override val url = "https://github.com/MrStahlfelge/gdx-controllerutils"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "de.golfgl.gdxcontrollerutils:gdx-controllers-advanced")
        addDependency(project, Desktop.ID, "de.golfgl.gdxcontrollerutils:gdx-controllers-jamepad")
        addDependency(project, LWJGL3.ID, "de.golfgl.gdxcontrollerutils:gdx-controllers-jamepad")
        addDependency(project, Android.ID, "de.golfgl.gdxcontrollerutils:gdx-controllers-android")
        addDependency(project, iOS.ID, "de.golfgl.gdxcontrollerutils:gdx-controllers-iosrvm")

        addDependency(project, GWT.ID, "de.golfgl.gdxcontrollerutils:gdx-controllers-gwt")
        addDependency(project, GWT.ID, "de.golfgl.gdxcontrollerutils:gdx-controllers-gwt:sources")
        addDependency(project, GWT.ID, "de.golfgl.gdxcontrollerutils:gdx-controllers-advanced:sources")
        addGwtInherit(project, "com.badlogic.gdx.controllers.controllers-gwt")
    }
}


/**
 * MrStahlfelge's controller-imitating Scene2D widgets, for players who don't have a controller.
 * <a href="https://github.com/MrStahlfelge/gdx-controllerutils/wiki/Button-operable-Scene2d">See the docs before using</a>.
 * Change the version to 1.0.1 if you use libGDX 1.9.10 or earlier!
 * @author MrStahlfelge
 */
@Extension
class ControllerScene2D : ThirdPartyExtension() {
    override val id = "controllerScene2D"
    override val defaultVersion = "2.3.0"
    override val url = "https://github.com/MrStahlfelge/gdx-controllerutils/wiki/Button-operable-Scene2d"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "de.golfgl.gdxcontrollerutils:gdx-controllerutils-scene2d")

        addDependency(project, GWT.ID, "de.golfgl.gdxcontrollerutils:gdx-controllerutils-scene2d:sources")
        addGwtInherit(project, "de.golfgl.gdx.controllers.controller_scene2d")
    }
}

/**
 * MrStahlfelge's configurable mapping for game controllers.
 * Not compatible with libGDX 1.9.10 or older!
 * @author MrStahlfelge
 */
@Extension
class ControllerMapping : ThirdPartyExtension() {
    override val id = "controllerMapping"
    override val defaultVersion = "2.3.0"
    override val url = "https://github.com/MrStahlfelge/gdx-controllerutils/wiki/Configurable-Game-Controller-Mappings"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "de.golfgl.gdxcontrollerutils:gdx-controllerutils-mapping")

        addDependency(project, GWT.ID, "de.golfgl.gdxcontrollerutils:gdx-controllerutils-mapping:sources")
        Controllers().initiate(project)
    }
}

/**
 * Code for making post-processing effects without so much hassle.
 * @author crashinvaders
 * @author metaphore
 */
@Extension
class GdxVfxCore : ThirdPartyExtension() {
    override val id = "gdxVfxCore"
    override val defaultVersion = "0.5.0"
    override val url = "https://github.com/crashinvaders/gdx-vfx"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.crashinvaders.vfx:gdx-vfx-core")

        addDependency(project, GWT.ID, "com.crashinvaders.vfx:gdx-vfx-core:sources")
        addDependency(project, GWT.ID, "com.crashinvaders.vfx:gdx-vfx-gwt")
        addDependency(project, GWT.ID, "com.crashinvaders.vfx:gdx-vfx-gwt:sources")
        addGwtInherit(project, "com.crashinvaders.vfx.GdxVfxCore")
        addGwtInherit(project, "com.crashinvaders.vfx.GdxVfxGwt")
    }
}

/**
 * A wide range of predefined post-processing effects using gdx-vfx core.
 * @author crashinvaders
 * @author metaphore
 */
@Extension
class GdxVfxStandardEffects : ThirdPartyExtension() {
    override val id = "gdxVfxEffects"
    override val defaultVersion = "0.5.0"
    override val url = "https://github.com/crashinvaders/gdx-vfx"

    override fun initiateDependencies(project: Project) {
        GdxVfxCore().initiate(project)
        addDependency(project, Core.ID, "com.crashinvaders.vfx:gdx-vfx-effects")

        addDependency(project, GWT.ID, "com.crashinvaders.vfx:gdx-vfx-effects:sources")
        addGwtInherit(project, "com.crashinvaders.vfx.GdxVfxEffects")
    }
}

/**
 * Cross-platform regex utilities that work the same on HTML as they do on desktop or mobile platforms.
 * This is not 100% the same as the java.util.regex package, but is similar, and sometimes offers more.
 * @author Tommy Ettinger
 * @author based on JRegex by Sergey A. Samokhodkin
 */
@Extension()
class RegExodus : ThirdPartyExtension() {
    override val id = "regExodus"
    override val defaultVersion = "0.1.12"
    override val url = "https://github.com/tommyettinger/RegExodus"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.tommyettinger:regexodus")

        addDependency(project, GWT.ID, "com.github.tommyettinger:regexodus:sources")
        addGwtInherit(project, "regexodus")
    }
}


/**
 * UI toolkit with extra widgets and a different theme style.
 * Check the vis-ui changelog for what vis-ui versions are compatible
 * with which libGDX versions; vis-ui 1.5.0 is the default and is
 * compatible with libGDX 1.10.0.
 * @author Kotcrab
 */
@Extension
class VisUI : ThirdPartyExtension() {
    override val id = "visUi"
    //You may need to skip a check: VisUI.setSkipGdxVersionCheck(true);
    override val defaultVersion = "1.5.0"
    override val url = "https://github.com/kotcrab/vis-ui"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.kotcrab.vis:vis-ui")

        addDependency(project, GWT.ID, "com.kotcrab.vis:vis-ui:sources")
        addGwtInherit(project, "com.kotcrab.vis.vis-ui")
    }
}

/**
 * A library to obtain a circular WidgetGroup or context menu using scene2d.ui.
 * Pie menus can be easier for players to navigate with a mouse than long lists.
 * @author Jérémi Grenier-Berthiaume
 */
@Extension
class PieMenu : ThirdPartyExtension() {
    override val id = "pieMenu"
    override val defaultVersion = "5.0.0"
    override val url = "https://github.com/payne911/PieMenu"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.payne911:PieMenu")

        addDependency(project, GWT.ID, "com.github.payne911:PieMenu:sources")
        addGwtInherit(project, "PieMenu")
        ShapeDrawer().initiate(project)
    }
}

/**
 * A 2D AABB collision detection and response library; like a basic/easy version of box2d.
 * Note, AABB means this only handles non-rotated rectangular collision boxes.
 * @author implicit-invocation
 * @author Raymond Buckley
 */
@Extension
class JBump : ThirdPartyExtension() {
    override val id = "jbump"
    override val defaultVersion = "v1.0.1"
    override val url = "https://github.com/tommyettinger/jbump"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.tommyettinger:jbump")
        addDependency(project, GWT.ID, "com.github.tommyettinger:jbump:sources")
        addGwtInherit(project, "com.dongbat.jbump")
    }
}

/**
 * A fast and efficient binary object graph serialization framework for Java.
 * @author Nathan Sweet
 */
@Extension
class Kryo : ThirdPartyExtension() {
    override val id = "kryo"
    override val defaultVersion = "5.1.1"
    override val url = "https://github.com/EsotericSoftware/kryo"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.esotericsoftware:kryo")
    }
}

/**
 * A Java library that provides a clean and simple API for efficient network communication, using Kryo.
 * This is crykn's fork (AKA damios), which is much more up-to-date than the official repo.
 * @author Nathan Sweet
 * @author damios/crykn
 */
@Extension
class KryoNet : ThirdPartyExtension() {
    override val id = "kryoNet"
    override val defaultVersion = "2.22.7"
    override val url = "https://github.com/crykn/kryonet"

    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.crykn:kryonet")
    }
}

/**
 * A small collection of some common and very basic utilities for libGDX games.
 * @author damios/crykn
 */
@Extension
class Guacamole : ThirdPartyExtension() {
    override val id = "guacamole"
    override val defaultVersion = "0.3.1"
    override val url = "https://github.com/crykn/guacamole"
    override fun initiateDependencies(project: Project) {
        addDependency(project, Core.ID, "com.github.crykn.guacamole:core")
        addDependency(project, Core.ID, "com.github.crykn.guacamole:gdx")
        addDependency(project, Desktop.ID, "com.github.crykn.guacamole:gdx-desktop")
        addDependency(project, LWJGL3.ID, "com.github.crykn.guacamole:gdx-desktop")
        addDependency(project, GWT.ID, "com.github.crykn.guacamole:core:sources")
        addDependency(project, GWT.ID, "com.github.crykn.guacamole:gdx:sources")
        addDependency(project, GWT.ID, "com.github.crykn.guacamole:gdx-gwt")
        addDependency(project, GWT.ID, "com.github.crykn.guacamole:gdx-gwt:sources")
        addGwtInherit(project, "guacamole_gdx_gwt")
    }
}
//
//    /**
//     * An immediate-mode GUI library (LWJGL3-only!) that can be an alternative to scene2d.ui.
//     * NOTE: this is only accessible from the lwjgl3 project, and may require unusual
//     * project configuration to use.
//     * @author SpaiR
//     */
//    @Extension
//    class Imgui : ThirdPartyExtension() {
//        override val id = "imgui"
//        override val defaultVersion = "1.82.2"
//        override val url = "https://github.com/SpaiR/imgui-java"
//
//        override fun initiateDependencies(project: Project) {
//
//            addDependency(project, LWJGL3.ID, "io.github.spair:imgui-java-binding");
//            addDependency(project, LWJGL3.ID, "io.github.spair:imgui-java-lwjgl3");
//            addDependency(project, LWJGL3.ID, "io.github.spair:imgui-java-natives-linux");
//            addDependency(project, LWJGL3.ID, "io.github.spair:imgui-java-natives-linux-x86");
//            addDependency(project, LWJGL3.ID, "io.github.spair:imgui-java-natives-macos");
//            addDependency(project, LWJGL3.ID, "io.github.spair:imgui-java-natives-windows");
//            addDependency(project, LWJGL3.ID, "io.github.spair:imgui-java-natives-windows-x86");
//
////            addDependency(project, Core.ID, "com.github.kotlin-graphics.imgui:core")
////            addDependency(project, LWJGL3.ID, "com.github.kotlin-graphics.imgui:gl")
////            addDependency(project, LWJGL3.ID, "com.github.kotlin-graphics.imgui:glfw")
//        }
//    }
//}