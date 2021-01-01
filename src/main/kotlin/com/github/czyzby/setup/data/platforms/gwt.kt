package com.github.czyzby.setup.data.platforms

import com.github.czyzby.setup.config.LibGdxVersion
import com.github.czyzby.setup.data.files.CopiedFile
import com.github.czyzby.setup.data.files.SourceFile
import com.github.czyzby.setup.data.files.path
import com.github.czyzby.setup.data.gradle.GradleFile
import com.github.czyzby.setup.data.project.Project
import com.github.czyzby.setup.views.GdxPlatform
import java.util.*

/**
 * Represents GWT backend.
 * @author MJ
 */
@GdxPlatform
class GWT : Platform {
	companion object {
		const val ID = "html"
		const val BASIC_INHERIT = "com.badlogic.gdx.backends.gdx_backends_gwt"
		val INHERIT_COMPARATOR = Comparator<kotlin.String> { a, b ->
			// Basic GWT inherit has to be first:
			if (a == BASIC_INHERIT) {
				-1
			} else if (b == BASIC_INHERIT) {
				1
			} else {
				a.compareTo(b)
			}
		}
	}

	override val id = ID
	override val isStandard = false
	
	override fun createGradleFile(project: Project): GradleFile = GWTGradleFile(project)

	override fun initiate(project: Project) {
		project.rootGradle.buildDependencies.add("\"org.wisepersist:gwt-gradle-plugin:\$gwtPluginVersion\"")

		addGradleTaskDescription(project, "superDev", "compiles GWT sources and runs the application in SuperDev mode. It will be available at [localhost:8080/${id}](http://localhost:8080/${id}). Use only during development.")
		addGradleTaskDescription(project, "dist", "compiles GWT sources. The compiled application can be found at `${id}/build/dist`: you can use any HTTP server to deploy it.")

		project.gwtInherits.add(BASIC_INHERIT)
		project.properties["gwtFrameworkVersion"] = "2.9.0"
		project.properties["gwtPluginVersion"] = project.advanced.gwtPluginVersion

		// Adding GWT definition to core project:
		project.files.add(SourceFile(projectName = Core.ID, packageName = project.basic.rootPackage,
				fileName = "${project.basic.mainClass}.gwt.xml", content = """<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.9.0//EN" "http://www.gwtproject.org/doctype/2.9.0/gwt-module.dtd">
<module>
	<source path="" />${(project.reflectedClasses + project.reflectedPackages).joinToString(separator = "\n", prefix = "\n") { "    <extend-configuration-property name=\"gdx.reflect.include\" value=\"$it\" />" }}
</module>"""))
		project.gwtInherits.add("${project.basic.rootPackage}.${project.basic.mainClass}")

		// Adding GWT definition to shared project:
		if (project.hasPlatform(Shared.ID)) {
			project.files.add(SourceFile(projectName = Shared.ID, packageName = project.basic.rootPackage,
					fileName = "Shared.gwt.xml", content = """<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.9.0//EN" "http://www.gwtproject.org/doctype/2.9.0/gwt-module.dtd">
<module>
	<source path="" />
</module>"""))
			project.gwtInherits.add("${project.basic.rootPackage}.Shared")
		}

		// Adding GWT definition:
		project.files.add(SourceFile(projectName = ID, packageName = project.basic.rootPackage,
				fileName = "GdxDefinition.gwt.xml", content = """<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.9.0//EN" "http://www.gwtproject.org/doctype/2.9.0/gwt-module.dtd">
<module rename-to="html">
	<source path="" />
${project.gwtInherits.sortedWith(INHERIT_COMPARATOR).joinToString(separator = "\n") { "\t<inherits name=\"$it\" />" }}
	<entry-point class="${project.basic.rootPackage}.gwt.GwtLauncher" />
	<set-configuration-property name="gdx.assetpath" value="../assets" />
	<set-configuration-property name="xsiframe.failIfScriptTag" value="FALSE"/>
	<!-- These two lines reduce the work GWT has to do during compilation and also shrink output size. -->
	<set-property name="user.agent" value="gecko1_8, safari"/>
	<collapse-property name="user.agent" values="*" />
	<!-- Remove the "user.agent" lines above if you encounter issues with Safari or other Gecko browsers. -->
</module>"""))

		// Adding SuperDev definition:
		project.files.add(SourceFile(projectName = ID, packageName = project.basic.rootPackage,
				fileName = "GdxDefinitionSuperdev.gwt.xml", content = """<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.9.0//EN" "http://www.gwtproject.org/doctype/2.9.0/gwt-module.dtd">
<module rename-to="html">
	<inherits name="${project.basic.rootPackage}.GdxDefinition" />
	<collapse-all-properties />
	<add-linker name="xsiframe"/>
	<set-configuration-property name="devModeRedirectEnabled" value="true"/>
	<set-configuration-property name="xsiframe.failIfScriptTag" value="FALSE"/>
</module>"""))

		// Copying webapp files:
		addCopiedFile(project, "webapp", "refresh.png")
		val version = LibGdxVersion.parseLibGdxVersion(project.advanced.gdxVersion)
		if(version != null && version < LibGdxVersion(major = 1, minor = 9, revision = 12))
		{
			addCopiedFile(project, "webapp", "soundmanager2-setup.js")
			project.files.add(CopiedFile(projectName = id,
					original = path("generator", id, "webapp", "index_old.html"), 
					path = path("webapp", "index.html")))
		}
		else
			addCopiedFile(project, "webapp", "index.html")
		addSoundManagerSource(project)
		addCopiedFile(project, "webapp", "styles.css")
		addCopiedFile(project, "webapp", "WEB-INF", "web.xml")
	}

	private fun addSoundManagerSource(project: Project) {
		val version = LibGdxVersion.parseLibGdxVersion(project.advanced.gdxVersion)
		val soundManagerSource = when {
		// Invalid, user-entered libGDX version - defaulting to current lack of SoundManager:
			version == null -> ""
		// Pre-1.9.6: using old SoundManager sources:
			version < LibGdxVersion(major = 1, minor = 9, revision = 6) -> "soundmanager2-jsmin_old.js"
		// Recent libGDX version - using latest SoundManager:
			version < LibGdxVersion(major = 1, minor = 9, revision = 12) -> "soundmanager2-jsmin.js"
		// after 1.9.11, soundmanager is no longer used
			else -> ""
		}
		if(soundManagerSource.isNotEmpty()) 
			project.files.add(CopiedFile(projectName = id,
				original = path("generator", id, "webapp", soundManagerSource),
				path = path("webapp", "soundmanager2-jsmin.js")))
	}
}

class GWTGradleFile(val project: Project) : GradleFile(GWT.ID) {
	init {
		buildDependencies.add("project(':${Core.ID}')")
		dependencies.add("project(':${Core.ID}')")

		addDependency("com.badlogicgames.gdx:gdx:\$gdxVersion:sources")
		addDependency("com.github.tommyettinger:gdx-backend-gwt:1.912.0")
		addDependency("com.github.tommyettinger:gdx-backend-gwt:1.912.0:sources")
	}

	override fun getContent(): String = """
buildscript {
	repositories {
		jcenter()
	}
	dependencies {
		classpath 'org.gretty:gretty:3.0.2'
	}
}
apply plugin: "gwt"
apply plugin: "war"
apply plugin: "org.gretty"

gwt {
	gwtVersion = "2.9.0" // Should match the version used for building the GWT backend. See gradle.properties.
	maxHeapSize = '1G' // Default 256m is not enough for the GWT compiler. GWT is HUNGRY.
	minHeapSize = '1G'

	src = files(file('src/main/java')) // Needs to be in front of "modules" below.
	modules '${project.basic.rootPackage}.GdxDefinition'
	devModules '${project.basic.rootPackage}.GdxDefinitionSuperdev'
	project.webAppDirName = 'webapp'

	compiler.strict = true
	compiler.disableCastChecking = true
	//// The next line can be useful to uncomment if you want output that hasn't been obfuscated.
//	compiler.style = org.wisepersist.gradle.plugins.gwt.Style.DETAILED
}

dependencies {
${joinDependencies(dependencies)}
}

import org.akhikhl.gretty.AppBeforeIntegrationTestTask
import org.wisepersist.gradle.plugins.gwt.GwtSuperDev

gretty.httpPort = 8080
gretty.resourceBase = project.buildDir.path + "/gwt/draftOut"
gretty.contextPath = "/"
gretty.portPropertiesFileName = "TEMP_PORTS.properties"

task startHttpServer (dependsOn: [draftCompileGwt]) {
	doFirst {
		copy {
			from "webapp"
			into gretty.resourceBase
		}
		copy {
			from "war"
			into gretty.resourceBase
		}
	}
}
task beforeRun(type: AppBeforeIntegrationTestTask, dependsOn: startHttpServer) {
    // The next line allows ports to be reused instead of
    // needing a process to be manually terminated.
	file("build/TEMP_PORTS.properties").delete()
	// Somewhat of a hack; uses Gretty's support for wrapping a task in
	// a start and then stop of a Jetty server that serves files while
	// also running the SuperDev code server.
	integrationTestTask 'superDev'
	
	interactive false
}

task superDev(type: GwtSuperDev) {
	doFirst {
		gwt.modules = gwt.devModules
	}
}
task dist(dependsOn: [clean, compileGwt]) {
    doLast {
		file("build/dist").mkdirs()
		copy {
			from "build/gwt/out"
			into "build/dist"
		}
		copy {
			from "webapp"
			into "build/dist"
			}
		copy {
			from "war"
			into "build/dist"
		}
	}
}

task addSource {
	doLast {
		sourceSets.main.compileClasspath += files(project(':core').sourceSets.main.allJava.srcDirs)
		${if(project.hasPlatform(Shared.ID)) "sourceSets.main.compileClasspath += files(project(':shared').sourceSets.main.allJava.srcDirs)" else ""}
	}
}

task distZip(type: Zip, dependsOn: dist){
	//// The next lines copy the dist but remove the recompile button (circling arrow) from the HTML page.
	from('build/dist/') {
		exclude '**/*.html'
	}
	from('build/dist/') {
		include '**/*.html'
		filter { String line -> line.replaceAll('<a class="superdev" .+', '') }
	}
	//// The next line attempts to name the zip with a unique timestamp, removing spaces and ':' for compatibility.
	archiveName "dist-${'$'}{(new Date().toString()).replace(' ', '-').replace(':', '-')}.zip"
	//// The result will be in html/build/ with a name containing the above probably-unique timestamp.
	destinationDir(file("build"))
}

tasks.compileGwt.dependsOn(addSource)
tasks.draftCompileGwt.dependsOn(addSource)

sourceCompatibility = 8.0
sourceSets.main.java.srcDirs = [ "src/main/java/" ]

eclipse.project.name = appName + "-html"
"""
}
