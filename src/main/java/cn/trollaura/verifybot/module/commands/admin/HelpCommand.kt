package cn.trollaura.verifybot.module.commands.admin

import cn.trollaura.verifybot.Loader
import cn.trollaura.verifybot.annotations.Command
import cn.trollaura.verifybot.managers.MessageManager
import cn.trollaura.verifybot.managers.MessageManager.modules
import cn.trollaura.verifybot.module.Module
import cn.trollaura.verifybot.util.DataUtil
import org.json.JSONArray
import snw.jkook.entity.User
import snw.jkook.message.Message
import snw.jkook.message.component.card.CardBuilder
import snw.jkook.message.component.card.MultipleCardComponent
import snw.jkook.message.component.card.Size
import snw.jkook.message.component.card.Theme
import snw.jkook.message.component.card.element.BaseElement
import snw.jkook.message.component.card.element.MarkdownElement
import snw.jkook.message.component.card.module.ContextModule
import snw.jkook.message.component.card.module.DividerModule
import snw.jkook.message.component.card.module.SectionModule
import snw.jkook.message.component.card.structure.Paragraph
import snw.jkook.plugin.Plugin

/**
@author tRollaURa_
@since 2024/7/23
 */

@Command
class HelpCommand(val plugin: Plugin)  : Module("help") {
    override fun onCommand(p0: User?, p1: Array<out Any>?, p2: Message?) {
        try {
            p2!!.reply(helpMessage())
        }catch (e: Exception) {
            p2!!.reply(MessageManager.caughtErrorMessage(e.message!!))
        }
    }

    fun helpMessage(): MultipleCardComponent {
        var cmds = mutableListOf<String>()
        var descriptions = mutableListOf<String>()


        Loader(plugin).modules.filter { it::class.java.annotations.contains(Command()) }.forEach {
            cmds.add("`$prefix${it.name} ${it.usage.first()}`")
            descriptions.add(it.description)
        }
        return CardBuilder().setTheme(Theme.INFO).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("**Mziac Verify-Bot Help List: **")))
            .addModule(DividerModule.INSTANCE)
            .addModule(
                SectionModule(
                    Paragraph(
                        2,
                        arrayListOf<BaseElement>(
                            MarkdownElement(cmds.joinToString("\n")),
                            MarkdownElement("**${descriptions.joinToString("\n")}**")
                        ).toCollection(ArrayList())
                    )
                )
            )
            .addModule(ContextModule(arrayListOf<BaseElement>(MarkdownElement("获取详细使用教程请使用${prefix}help <指令>"))))
            .addModule(DividerModule.INSTANCE)
            .addModule(ContextModule(arrayListOf<BaseElement>(MarkdownElement("Written by \uD835\uDD99\uD835\uDD7D\uD835\uDD94\uD835\uDD91\uD835\uDD91\uD835\uDD86\uD835\uDD80\uD835\uDD7D\uD835\uDD86#1337"))))
            .build()
    }
}