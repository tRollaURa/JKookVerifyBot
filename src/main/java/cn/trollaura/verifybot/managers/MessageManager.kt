package cn.trollaura.verifybot.managers

import cn.trollaura.verifybot.Loader
import cn.trollaura.verifybot.annotations.Command
import cn.trollaura.verifybot.module.Module
import cn.trollaura.verifybot.util.ConfigUtil
import cn.trollaura.verifybot.util.ConfigUtil.get
import cn.trollaura.verifybot.util.DataUtil
import cn.trollaura.verifybot.util.VIPUtil
import org.json.JSONArray
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

object MessageManager {

    var modules = mutableSetOf<Module>()
    fun init() {
        modules = Loader(ConfigUtil.plugin).modules
    }

    val prefix = get<String>("CommandPrefix")
    val bannedMessage = CardBuilder().setTheme(Theme.DANGER).setSize(Size.LG)
        .addModule(SectionModule(MarkdownElement("🥵**抱歉,您的服务器暂未获得授权或已得到禁令!**"))).build()

    val nopermMessage =
        CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("\uD83D\uDE11**您似乎没有拥有使用此指令的权限**"))).build()

    val OrdersuccessMessage =
        CardBuilder().setTheme(Theme.SUCCESS).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("😎**支付成功,已经升级到高级用户组!**"))).build()

    val OrderFailedMessage =
        CardBuilder().setTheme(Theme.SUCCESS).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("😲**支付失败,请联系开发者查看是否掉单?**"))).build()


    val noOrderidMessage =
        CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("\uD83D\uDE11**请输入订单号!**"))).build()
    val noKeyMessage =
        CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("😭**此卡密不存在!**"))).build()

    val noFullSetMessage =
        CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("😭**您的服务器未配置完毕!**")))
            .addModule(SectionModule(MarkdownElement("**配置指令: `/set` **"))).build()

    val waitForTimeMessage =
        CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("😫**您需要等待 5 分钟才能再次生成!**")))
            .build()
    val verifySucceedMessage =
        CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("👍**您已经验证通过**"))).build()

    val maxKeysMessage =
        CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("\uD83D\uDE2D**您的卡密生成已超过限制!**"))).build()

    val shouldChannelMessage =
        CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("\uD83D\uDE2D**您应该在服务器使用此指令!**"))).build()

    val needInitMessage =  CardBuilder().setTheme(Theme.DANGER).setSize(Size.LG)
        .addModule(SectionModule(MarkdownElement("\uD83D\uDE2D**本服务器未初始化,请联系机器人简介上的开发者!**"))).build()

    fun spawnMaxMessage(guildID: String): MultipleCardComponent {
        return CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(
                SectionModule(
                    MarkdownElement(
                        "\uD83D\uDE2D**您的一次性卡密生成最多为 `${
                            VIPUtil.getMaxSpawn(
                                guildID
                            )
                        }个`**"
                    )
                )
            ).build()
    }

    fun addMuteMessage(man: String): MultipleCardComponent? {
        return CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("😶**${man}无法继续狗叫了!**")))
            .build()
    }
    fun removeMuteMessage(man: String): MultipleCardComponent {
        return CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("😀**${man}又可以继续狗叫了!**")))
            .build()
    }
    fun caughtErrorMessage(error: String): MultipleCardComponent {
        return CardBuilder().setTheme(Theme.DANGER).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("🥵🥵🥵机器人发生致命错误,请联系开发者解决!!!!")))
            .addModule(
                SectionModule(
                    MarkdownElement(
                        """ ```java
                $error
                ```
                """.trimIndent()
                    )
                )
            )
            .addModule(DividerModule.INSTANCE)
            .addModule(ContextModule(arrayListOf<BaseElement>(MarkdownElement("Written by \uD835\uDD99\uD835\uDD7D\uD835\uDD94\uD835\uDD91\uD835\uDD91\uD835\uDD86\uD835\uDD80\uD835\uDD7D\uD835\uDD86#1337"))))
            .build()
    }

    fun alreadySetMessage(level: Int): MultipleCardComponent {
        return CardBuilder().setTheme(Theme.SUCCESS).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("\uD83D\uDE0E**已经修改当前服务器权限为: `$level` **")))
            .addModule(SectionModule(MarkdownElement("**如本服务器未完成配置,请添加管理员进行配置!**")))
            .addModule(SectionModule(MarkdownElement("**配置指令: `/set` **")))
            .addModule(DividerModule.INSTANCE)
            .addModule(ContextModule(arrayListOf<BaseElement>(MarkdownElement("Written by \uD835\uDD99\uD835\uDD7D\uD835\uDD94\uD835\uDD91\uD835\uDD91\uD835\uDD86\uD835\uDD80\uD835\uDD7D\uD835\uDD86#1337")))).build()
    }



    fun serverInfoMessage(guildID: String): MultipleCardComponent {
        val guild = ConfigUtil.plugin.core.httpAPI.getGuild(guildID)
        val json = DataUtil.getJSONObjectWithID(JSONArray(DataUtil.file.readText()), guildID)
        val adminname = mutableListOf<String>()
        if(json == null) {
            return needInitMessage
        }
        json!!.getJSONArray("Administrators").toMutableList().forEach {
            adminname.add("${ConfigUtil.plugin.core.httpAPI.getUser(it.toString()).name}($it)")
        }

        return CardBuilder().setTheme(Theme.INFO).setSize(Size.LG)
            .addModule(SectionModule(Paragraph(2, arrayListOf<BaseElement>(MarkdownElement("**服务器名**\n**授权等级**\n**验证用户组**\n**管理员**"),MarkdownElement("`${guild.name}(${guild.id})`\n`${VIPUtil.getLevelName(guildID)}`\n`${if(json.getString("Role").isNullOrEmpty()) "None" else json.getString("Role")}`\n ${adminname.joinToString("\n") }")).toCollection(ArrayList()))))
            .addModule(DividerModule.INSTANCE)
            .addModule(ContextModule(arrayListOf<BaseElement>(MarkdownElement("Written by \uD835\uDD99\uD835\uDD7D\uD835\uDD94\uD835\uDD91\uD835\uDD91\uD835\uDD86\uD835\uDD80\uD835\uDD7D\uD835\uDD86#1337")))).build()
    }


    fun spawnKeysMessage(counts: Int, guildID: String): MultipleCardComponent? {
        val json = DataUtil.getJSONObjectWithID(JSONArray(DataUtil.file.readText()), guildID)
        return CardBuilder().setTheme(Theme.SUCCESS).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("**你的∫β卡密已经添加: **")))
            .addModule(
                ContextModule(
                    mutableListOf(
                        MarkdownElement(
                            "已经添加卡密: `${counts}个`,目前拥有: `${
                                json!!.getJSONArray(
                                    "Key"
                                ).length()
                            }个`,最多生成: `${VIPUtil.getLevelKeysCount(guildID)}个`"
                        )
                    ).toList()
                )
            )
            .addModule(DividerModule.INSTANCE)
            .addModule(ContextModule(arrayListOf<BaseElement>(MarkdownElement("Written by \uD835\uDD99\uD835\uDD7D\uD835\uDD94\uD835\uDD91\uD835\uDD91\uD835\uDD86\uD835\uDD80\uD835\uDD7D\uD835\uDD86#1337"))))
            .build()
    }

    fun alreadySetRoleMessage(role: String): MultipleCardComponent {
        return CardBuilder().setTheme(Theme.SUCCESS).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("\uD83D\uDE0E**已经修改当前服务器验证身份组为: `$role` **")))
            .addModule(DividerModule.INSTANCE)
            .addModule(ContextModule(arrayListOf<BaseElement>(MarkdownElement("Written by \uD835\uDD99\uD835\uDD7D\uD835\uDD94\uD835\uDD91\uD835\uDD91\uD835\uDD86\uD835\uDD80\uD835\uDD7D\uD835\uDD86#1337"))))
            .build()
    }

    fun alreadySetBackSideMessage(back: String): MultipleCardComponent {
        return CardBuilder().setTheme(Theme.SUCCESS).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("\uD83D\uDE0E**已经修改当前服务器机器人后台为: `$back` **")))
            .addModule(DividerModule.INSTANCE)
            .addModule(ContextModule(arrayListOf<BaseElement>(MarkdownElement("Written by \uD835\uDD99\uD835\uDD7D\uD835\uDD94\uD835\uDD91\uD835\uDD91\uD835\uDD86\uD835\uDD80\uD835\uDD7D\uD835\uDD86#1337"))))
            .build()
    }

    fun alreadySetAdminMessage(admin: String): MultipleCardComponent {
        return CardBuilder().setTheme(Theme.SUCCESS).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("\uD83D\uDE0E**已经添加新的管理员: `$admin` **")))
            .addModule(DividerModule.INSTANCE)
            .addModule(ContextModule(arrayListOf<BaseElement>(MarkdownElement("Written by \uD835\uDD99\uD835\uDD7D\uD835\uDD94\uD835\uDD91\uD835\uDD91\uD835\uDD86\uD835\uDD80\uD835\uDD7D\uD835\uDD86#1337")))).build()
    }


    fun alreadyunSetAdminMessage(admin: String): MultipleCardComponent {
        return CardBuilder().setTheme(Theme.SUCCESS).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("😎**已经删除管理员: `$admin` **")))
            .addModule(DividerModule.INSTANCE)
            .addModule(ContextModule(arrayListOf<BaseElement>(MarkdownElement("Written by \uD835\uDD99\uD835\uDD7D\uD835\uDD94\uD835\uDD91\uD835\uDD91\uD835\uDD86\uD835\uDD80\uD835\uDD7D\uD835\uDD86#1337")))).build()
    }


    fun usageMessage(module: Module): MultipleCardComponent {
        val card = CardBuilder().setTheme(Theme.WARNING).setSize(Size.LG)
            .addModule(SectionModule(MarkdownElement("**❓使用方法: **")))
        for (i in 0 until module.usage.size) {
            card.addModule(SectionModule(MarkdownElement("`${module.prefix}${module.name} ${module.usage[i]}`")))
        }
            card.addModule(DividerModule.INSTANCE)
            card.addModule(ContextModule(arrayListOf<BaseElement>(MarkdownElement("Written by \uD835\uDD99\uD835\uDD7D\uD835\uDD94\uD835\uDD91\uD835\uDD91\uD835\uDD86\uD835\uDD80\uD835\uDD7D\uD835\uDD86#1337"))))
        return card.build()
    }
}