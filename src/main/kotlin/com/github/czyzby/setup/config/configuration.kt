package com.github.czyzby.setup.config

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.github.czyzby.autumn.annotation.Component
import com.github.czyzby.autumn.annotation.Initiate
import com.github.czyzby.autumn.mvc.component.i18n.LocaleService
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService
import com.github.czyzby.autumn.mvc.component.ui.SkinService
import com.github.czyzby.autumn.mvc.config.AutumnActionPriority
import com.github.czyzby.autumn.mvc.stereotype.preference.*
import com.github.czyzby.lml.parser.LmlParser
import com.github.czyzby.lml.parser.tag.LmlAttribute
import com.github.czyzby.lml.parser.tag.LmlTag
import com.github.czyzby.lml.vis.parser.impl.VisLmlSyntax
import com.github.czyzby.lml.vis.parser.impl.nongwt.ExtendedVisLml
import com.github.czyzby.setup.views.widgets.ScrollableTextArea
import com.kotcrab.vis.ui.Locales
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.file.FileChooser

/**
 * Configures Autumn MVC application.
 * @author MJ
 */
@Component
class Configuration {
    companion object {
        const val VERSION = "3.0.4-JITPACK"
        const val WIDTH = 600
        const val HEIGHT = 700
        const val PREFERENCES_PATH = "SquidSetup-prefs"
    }

    @LmlParserSyntax val syntax = VisLmlSyntax()
    @LmlMacro val macro = "templates/macros.lml"

    @I18nBundle val bundle = "i18n/nls"
    @I18nLocale(propertiesPath = PREFERENCES_PATH, defaultLocale = "en") val localePreference = "locale"
    @AvailableLocales val availableLocales = arrayOf("en", "pl")
    @Preference val preferencesPath = PREFERENCES_PATH;

    @Initiate(priority = AutumnActionPriority.TOP_PRIORITY)
    fun initiate(skinService: SkinService, interfaceService: InterfaceService, localeService: LocaleService) {
        VisUI.setSkipGdxVersionCheck(true)
        VisUI.load(Gdx.files.internal("skin/tinted.json"))
        skinService.addSkin("default", VisUI.getSkin())
        FileChooser.setDefaultPrefsName(PREFERENCES_PATH)

        // Adding tags and attributes related to the file chooser:
        ExtendedVisLml.registerFileChooser(syntax)
        ExtendedVisLml.registerFileValidators(syntax)
        // Adding custom ScrollableTextArea widget:
        syntax.addTagProvider(ScrollableTextArea.ScrollableTextAreaLmlTagProvider(), "console")

        // Changing FileChooser locale bundle:
        interfaceService.setActionOnBundlesReload {
            Locales.setFileChooserBundle(localeService.i18nBundle)
        }

        // Adding custom tooltip tag attribute:
        interfaceService.parser.syntax.addAttributeProcessor(object : LmlAttribute<Actor> {
            override fun getHandledType(): Class<Actor> = Actor::class.java
            override fun process(parser: LmlParser, tag: LmlTag, actor: Actor, rawAttributeData: String) {
                val tooltip = Tooltip()
                val label = VisLabel(parser.parseString(rawAttributeData, actor), "small")
                label.setWrap(true)
                tooltip.clear()
                tooltip.add(label).width(200f)
                tooltip.pad(3f)
                tooltip.setTarget(actor)
                tooltip.pack()
            }
        }, "tooltip")
    }
}
