package com.github.czyzby.setup.views

import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.github.czyzby.kiwi.util.common.Strings
import com.github.czyzby.lml.annotation.LmlActor
import com.kotcrab.vis.ui.widget.VisTextField
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel
import com.kotcrab.vis.ui.widget.spinner.Spinner

/**
 * Stores data from "advanced" tab.
 * @author MJ
 */
class AdvancedData {
    @LmlActor("version") private lateinit var versionField: VisTextField
    @LmlActor("javaVersion") private lateinit var javaVersionField: Spinner
    @LmlActor("sdkVersion") private lateinit var sdkVersionField: Spinner
    @LmlActor("androidPluginVersion") private lateinit var androidPluginVersionField: VisTextField
    @LmlActor("robovmVersion") private lateinit var robovmVersionField: VisTextField
    @LmlActor("gwtPlugin") private lateinit var gwtPluginVersionField: VisTextField
    @LmlActor("serverJavaVersion") private lateinit var serverJavaVersionField: Spinner
    @LmlActor("desktopJavaVersion") private lateinit var desktopJavaVersionField: Spinner
    @LmlActor("generateSkin") private lateinit var generateSkinButton: Button
    @LmlActor("generateReadme") private lateinit var generateReadmeButton: Button
    @LmlActor("gradleTasks") private lateinit var gradleTasksField: VisTextField

    val version: String
        get() = versionField.text

    val gdxVersion: String
        get() = "1.10.0"

    val javaVersion: String
        get() = if(javaVersionField.model.text.length == 1)
            "1." + javaVersionField.model.text else javaVersionField.model.text

    var androidSdkVersion: String
        get() = sdkVersionField.model.text
        set(value) {
            val model = sdkVersionField.model as IntSpinnerModel
            model.value = value.toInt()
            sdkVersionField.notifyValueChanged(false)
        }

    val androidPluginVersion: String
        get() = androidPluginVersionField.text

    val robovmVersion: String
        get() = robovmVersionField.text

    val gwtVersion: String
        get() = "2.9.0"

    val gwtPluginVersion: String
        get() = gwtPluginVersionField.text

    val serverJavaVersion: String
        get() = if(serverJavaVersionField.model.text.length == 1)
            "1." + serverJavaVersionField.model.text else serverJavaVersionField.model.text

    val desktopJavaVersion: String
        get() = if(desktopJavaVersionField.model.text.length == 1)
            "1." + desktopJavaVersionField.model.text else desktopJavaVersionField.model.text

    val generateSkin: Boolean
        get() = generateSkinButton.isChecked

    val generateReadme: Boolean
        get() = generateReadmeButton.isChecked

    val addGradleWrapper: Boolean
        get() = true

    val gradleTasks: List<String>
        get() = if (gradleTasksField.isEmpty) listOf()
        else gradleTasksField.text.split(Regex(Strings.WHITESPACE_SPLITTER_REGEX)).filter { it.isNotBlank() }

    fun forceSkinGeneration() {
        generateSkinButton.isChecked = true
    }
}
