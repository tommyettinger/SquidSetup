package com.github.czyzby.setup.data.project

import com.badlogic.gdx.Files
import com.badlogic.gdx.utils.GdxRuntimeException
import com.github.czyzby.setup.data.files.*
import com.github.czyzby.setup.data.gradle.GradleFile
import com.github.czyzby.setup.data.gradle.RootGradleFile
import com.github.czyzby.setup.data.langs.Java
import com.github.czyzby.setup.data.platforms.Android
import com.github.czyzby.setup.data.platforms.Assets
import com.github.czyzby.setup.data.platforms.Platform
import com.github.czyzby.setup.data.templates.Template
import com.github.czyzby.setup.views.AdvancedData
import com.github.czyzby.setup.views.BasicProjectData
import com.github.czyzby.setup.views.ExtensionsData
import com.github.czyzby.setup.views.LanguagesData
import com.kotcrab.vis.ui.util.OsUtils
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * Contains data about the generated project.
 * @author MJ
 */
class Project(val basic: BasicProjectData, val platforms: Map<String, Platform>, val advanced: AdvancedData,
              val languages: LanguagesData, val extensions: ExtensionsData, val template: Template) {
    private val gradleFiles: Map<String, GradleFile>
    val files = mutableListOf<ProjectFile>()
    val rootGradle: RootGradleFile
    val properties = mutableMapOf(
            "org.gradle.daemon" to "false",
            "org.gradle.jvmargs" to "-Xms128m -Xmx2g",
            "org.gradle.configureondemand" to "false")
    val postGenerationTasks = mutableListOf<(Project) -> Unit>()
    val gwtInherits = mutableSetOf<String>()
    val androidPermissions = mutableSetOf<String>()

    val reflectedClasses = mutableSetOf<String>()
    val reflectedPackages = mutableSetOf<String>()

    // README.md:
    var readmeDescription = ""
    val gradleTaskDescriptions = mutableMapOf<String, String>()

    init {
        gradleFiles = mutableMapOf<String, GradleFile>()
        rootGradle = RootGradleFile(this)
        platforms.forEach { gradleFiles[it.key] = it.value.createGradleFile(this) }
        addBasicGradleTasksDescriptions()
    }

    private fun addBasicGradleTasksDescriptions() {
        if (advanced.generateReadme) {
            arrayOf("idea" to "generates IntelliJ project data.",
                    "cleanIdea" to "removes IntelliJ project data.",
                    "eclipse" to "generates Eclipse project data.",
                    "cleanEclipse" to "removes Eclipse project data.",
                    "clean" to "removes `build` folders, which store compiled classes and built archives.",
                    "test" to "runs unit tests (if any).",
                    "build" to "builds sources and archives of every project.",
                    "--daemon" to "thanks to this flag, Gradle daemon will be used to run chosen tasks.",
                    "--offline" to "when using this flag, cached dependency archives will be used.",
                    "--continue" to "when using this flag, errors will not stop the tasks from running.",
                    "--refresh-dependencies" to "this flag forces validation of all dependencies. Useful for snapshot versions.")
                    .forEach { gradleTaskDescriptions[it.first] = it.second }
        }
    }

    fun hasPlatform(id: String): Boolean = platforms.containsKey(id)

    fun getGradleFile(id: String): GradleFile = gradleFiles.get(id)!!

    fun addGradleTaskDescription(task: String, description: String) {
        if (advanced.generateReadme) {
            gradleTaskDescriptions[task] = description
        }
    }

    fun generate() {
        addBasicFiles()
        addJvmLanguagesSupport()
        addExtensions()
        template.apply(this)
        addPlatforms()
        ////SQUIDSETUP CHANGE
"""
7-12-serif.fnt
7-12-serif.png
7-12-serif-license.txt
7-12-serif-readme.txt
a-starry-license.txt
a-starry-msdf.fnt
a-starry-msdf.png
awesome-solid-msdf.fnt
awesome-solid-msdf.png
bloccus-license.txt
bloccus-msdf.fnt
bloccus-msdf.png
BoxedIn-distance.fnt
BoxedIn-distance.png
BoxedIn-License.txt
Cascadia-license.txt
CascadiaMono-msdf.fnt
CascadiaMono-msdf.png
CM-Custom-distance.fnt
CM-Custom-distance.png
ComputerSaysNo.fnt
ComputerSaysNo.png
ComputerSaysNo-license.txt
ComputerSaysNo-readme.txt
Cozette.fnt
Cozette.png
Cozette-license.txt
CozetteOutlined.fnt
CozetteOutlined.png
DejaVuSansMono-distance.fnt
DejaVuSansMono-distance.png
DejaVuSansMono-License.txt
DejaVuSansMono-msdf.fnt
DejaVuSansMono-msdf.png
Font-Awesome-license.txt
Gentium-distance.fnt
Gentium-distance.png
GoMono-Family-distance.fnt
GoMono-Family-distance.png
GoMono-License.txt
icons.atlas
icons.png
icons-license.txt
Inconsolata-LGC-8x18.fnt
Inconsolata-LGC-8x18.png
Inconsolata-LGC-12x24.fnt
Inconsolata-LGC-12x24.png
Inconsolata-LGC-Custom-distance.fnt
Inconsolata-LGC-Custom-distance.png
Inconsolata-LGC-Square-25x25.fnt
Inconsolata-LGC-Square-25x25.png
Inconsolata-LGC-Square-distance.fnt
Inconsolata-LGC-Square-distance.png
Iosevka-distance.fnt
Iosevka-distance.png
Iosevka-Family-distance.fnt
Iosevka-Family-distance.png
Iosevka-Family-msdf.fnt
Iosevka-Family-msdf.png
Iosevka-License.md
Iosevka-Light-distance.fnt
Iosevka-Light-distance.png
Iosevka-msdf.fnt
Iosevka-msdf.png
Iosevka-Slab-distance.fnt
Iosevka-Slab-distance.png
Iosevka-Slab-Family-distance.fnt
Iosevka-Slab-Family-distance.png
Iosevka-Slab-Family-msdf.fnt
Iosevka-Slab-Family-msdf.png
Iosevka-Slab-Light-distance.fnt
Iosevka-Slab-Light-distance.png
Iosevka-Slab-msdf.fnt
Iosevka-Slab-msdf.png
Mandrill-6x16.fnt
Mandrill-6x16.png
Mandrill-12x32.fnt
Mandrill-12x32.png
Monty-4x10.fnt
Monty-4x10.png
Monty-8x20.fnt
Monty-8x20.png
Monty-license.txt
Noto-Sans-distance.fnt
Noto-Sans-distance.png
NotoSerif-Family-msdf.fnt
NotoSerif-Family-msdf.png
NotoSerif-license.txt
Orbitron-distance.fnt
Orbitron-distance.png
README.md
Roboto-Bold-msdf.fnt
Roboto-Bold-msdf.png
Roboto-License.txt
Roboto-Regular-msdf.fnt
Roboto-Regular-msdf.png
Rogue-Zodiac-6x12.fnt
Rogue-Zodiac-6x12_0.png
Rogue-Zodiac-12x24.fnt
Rogue-Zodiac-12x24_0.png
Rogue-Zodiac-18x36.fnt
Rogue-Zodiac-18x36_0.png
SourceCodePro-License.txt
SourceCodePro-Medium-distance.fnt
SourceCodePro-Medium-distance.png
SourceHanCodeJP-license.txt
SourceHanCodeJP-Regular-distance.fnt
SourceHanCodeJP-Regular-distance.png
square-License.txt
square-msdf.fnt
square-msdf.png
Tentacle.png
Tentacle-16.png
Tentacle-32.png
Tentacle-64.png
Tentacle-128.png
Zodiac-Square-12x12.fnt
Zodiac-Square-12x12.png
Zodiac-Square-24x24.fnt
Zodiac-Square-24x24.png
""".split('\n')
        .forEach {
            files.add(CopiedFile(projectName = "assets", path = it,
                    original = path("generator", "assets", it)))
        }
        ////END SQUIDSETUP CHANGE

        addSkinAssets()
        addReadmeFile()
        saveProperties()
        saveFiles()
        // Invoking post-generation tasks:
        postGenerationTasks.forEach { it(this) }
    }

    private fun addBasicFiles() {
        // Adding global assets folder:
        files.add(SourceDirectory(Assets.ID, ""))
        // Adding .gitignore:
        files.add(CopiedFile(path = ".gitignore", original = path("generator", "gitignore")))
    }

    private fun addJvmLanguagesSupport() {
        Java().initiate(this) // Java is supported by default.
        languages.getSelectedLanguages().forEach {
            it.initiate(this)
            properties[it.id + "Version"] = languages.getVersion(it.id)
        }
        languages.appendSelectedLanguagesVersions(this)
    }

    private fun addExtensions() {
        extensions.getSelectedOfficialExtensions().forEach { it.initiate(this) }
        extensions.getSelectedThirdPartyExtensions().forEach { it.initiate(this) }
    }

    private fun addPlatforms() {
        platforms.values.forEach { it.initiate(this) }
        SettingsFile(platforms.values).save(basic.destination)
    }

    private fun saveFiles() {
        rootGradle.save(basic.destination)
        gradleFiles.values.forEach { it.save(basic.destination) }
        files.forEach { it.save(basic.destination) }
    }

    private fun saveProperties() {
        // Adding LibGDX version property:
        properties["gdxVersion"] = advanced.gdxVersion
        PropertiesFile(properties).save(basic.destination)
    }

    private fun addSkinAssets() {
        if (advanced.generateSkin) {
            // Adding GUI assets directory:
            files.add(SourceDirectory(Assets.ID, "ui"))

            files.add(CopiedFile(projectName = Assets.ID, path = path("ui", "uiskin.atlas"),
                    original = path("generator", "assets", "ui", "uiskin.atlas")))
            files.add(CopiedFile(projectName = Assets.ID, path = path("ui", "uiskin.json"),
                    original = path("generator", "assets", "ui", "uiskin.json")))
            files.add(CopiedFile(projectName = Assets.ID, path = path("ui", "uiskin.png"),
                    original = path("generator", "assets", "ui", "uiskin.png")))
            files.add(CopiedFile(projectName = Assets.ID, path = path("ui", "font.fnt"),
                    original = path("generator", "assets", "ui", "font.fnt")))
            files.add(CopiedFile(projectName = Assets.ID, path = path("ui", "font-list.fnt"),
                    original = path("generator", "assets", "ui", "font-list.fnt")))
            files.add(CopiedFile(projectName = Assets.ID, path = path("ui", "font-subtitle.fnt"),
                    original = path("generator", "assets", "ui", "font-subtitle.fnt")))
            files.add(CopiedFile(projectName = Assets.ID, path = path("ui", "font-window.fnt"),
                    original = path("generator", "assets", "ui", "font-window.fnt")))

            // Android does not support classpath fonts loading through skins.
            // Explicitly copying Arial font if Android platform is included:
            if (hasPlatform(Android.ID)) {
                arrayOf("png", "fnt").forEach {
                    val path = path("com", "badlogic", "gdx", "utils", "arial-15.$it")
                    files.add(CopiedFile(projectName = Assets.ID, path = path, original = path,
                            fileType = Files.FileType.Classpath))
                }
            }
        }
    }

    private fun addReadmeFile() {
        if (advanced.generateReadme) {
            files.add(SourceFile(projectName = "", fileName = "README.md", content = """# ${basic.name}

A [LibGDX](http://libgdx.badlogicgames.com/) project generated with [SquidSetup](https://github.com/tommyettinger/SquidSetup).

${readmeDescription}

## Gradle

This project uses [Gradle](http://gradle.org/) to manage dependencies. ${if (advanced.addGradleWrapper)
                "Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands."
            else
                "Gradle wrapper was not included by default, so you have to install Gradle locally."} Useful Gradle tasks and flags:

${gradleTaskDescriptions.map { "- `${it.key}`: ${it.value}" }.sorted().joinToString(separator = "\n")}

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project."""))
        }
    }

    fun includeGradleWrapper(logger: ProjectLogger) {
        if (advanced.addGradleWrapper) {
            arrayOf("gradlew", "gradlew.bat", path("gradle", "wrapper", "gradle-wrapper.jar"),
                    path("gradle", "wrapper", "gradle-wrapper.properties")).forEach {
                CopiedFile(path = it, original = path("generator", it)).save(basic.destination)
            }
            basic.destination.child("gradlew").file().setExecutable(true)
            basic.destination.child("gradlew.bat").file().setExecutable(true)
            logger.logNls("copyGradle")
        }
        val gradleTasks = advanced.gradleTasks
        if (gradleTasks.isNotEmpty()) {
            logger.logNls("runningGradleTasks")
            val commands = determineGradleCommand() + advanced.gradleTasks
            logger.log(commands.joinToString(separator = " "))
            val process = ProcessBuilder(*commands).directory(basic.destination.file())
                    .redirectErrorStream(true).start()
            val stream = BufferedReader(InputStreamReader(process.inputStream))
            var line = stream.readLine();
            while (line != null) {
                logger.log(line)
                line = stream.readLine();
            }
            process.waitFor()
            if (process.exitValue() != 0) {
                throw GdxRuntimeException("Gradle process ended with non-zero value.")
            }
        }
    }

    private fun determineGradleCommand(): Array<String> {
        return if (OsUtils.isWindows()) {
            arrayOf("cmd", "/c", if (advanced.addGradleWrapper) "gradlew" else "gradle")
        } else {
            arrayOf(if (advanced.addGradleWrapper) "./gradlew" else "gradle")
        }
    }
}

interface ProjectLogger {
    fun log(message: String)
    fun logNls(bundleLine: String)
}
