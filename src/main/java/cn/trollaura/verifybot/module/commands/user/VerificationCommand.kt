package cn.trollaura.verifybot.module.commands.user

import cn.trollaura.api.Json解析器
import cn.trollaura.verifybot.annotations.Command
import cn.trollaura.verifybot.managers.MessageManager
import cn.trollaura.verifybot.module.Module
import cn.trollaura.verifybot.util.DataUtil
import org.json.JSONArray
import org.json.JSONObject
import snw.jkook.entity.User
import snw.jkook.entity.channel.TextChannel
import snw.jkook.message.Message
import snw.jkook.message.component.card.CardBuilder
import snw.jkook.message.component.card.Size
import snw.jkook.message.component.card.Theme
import snw.jkook.message.component.card.element.MarkdownElement
import snw.jkook.message.component.card.module.SectionModule
import snw.jkook.plugin.Plugin
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
@author tRollaURa_
@since 2024/7/24
 */
@Command
class VerificationCommand(val plugin: Plugin) : Module("verify", arrayOf("<卡密>")) {

    init {
        this.description = "验证卡密"
    }
    override fun onCommand(p0: User, p1: Array<out Any>, p2: Message?) {
        try {
        val guild = getGuild(plugin,p2!!)
        if(checkIfBanned(getGuildID(plugin,p2))) {
            p2.reply(MessageManager.bannedMessage)
            return
        }
        if(p1.isEmpty()) {
            throwUsage(p2,this)
            return
        }

        val jsonObject = DataUtil.getJSONObjectWithID(getJsonArray(),getGuildID(plugin,p2))

            if(jsonObject == null) {
                p2.reply(MessageManager.needInitMessage)
                return
            }

        if(!jsonObject!!.getJSONArray("Key").toMutableList() .contains(p1[0].toString())) {
            p2.reply(MessageManager.noKeyMessage)
            p2.delete()
            return
        }




        Thread {
            val jsonArray = getJsonArray()
            val jsonObjects = DataUtil.getJSONObjectWithID(jsonArray,getGuildID(plugin,p2))
            val roles = guild.roles
            while (roles.hasNext()) {
                val roleSet = roles.next()
                if (jsonObject.getString("Role").isNullOrEmpty() || jsonObject.getString("VerifyBackSide").isNullOrEmpty()) {
                    p2.reply(MessageManager.noFullSetMessage)
                    p2.delete()
                    return@Thread
                }

                for (role in roleSet) {
                    if (role.id.toString() == jsonObject.getString("Role")) {
                        DataUtil.replaceJSONObjectInArray(jsonArray,jsonObjects!!,jsonObjects.put("Key",jsonObject.getJSONArray("Key").filter { it.toString() != p1[0].toString().replace("(met)","") }))
                        (plugin.core.httpAPI.getChannel(jsonObject.getString("VerifyBackSide")) as TextChannel).sendComponent(
                            CardBuilder()
                                .setSize(Size.LG)
                                .setTheme(Theme.INFO)
                                .addModule(SectionModule(
                                    MarkdownElement(
                                        "**${p0.name}** 验证成功！\n" +
                                                "**卡密**：${p1[0].toString().replace("(met)","")}\n" +
                                                "**验证时间**：${SimpleDateFormat("yyyy/MM/dd HH:mm:ss") .format(Date(System.currentTimeMillis()))}\n" +
                                                "**验证人**：${p0.name}\n" +
                                                "**验证人ID**：${p0.id}\n"
                                    )
                                )).build()
                        )
                        p0.grantRole(role)
                        p2.reply(MessageManager.verifySucceedMessage)

                        p2.delete()

                        return@Thread
                    }
                }
            }
        }.start()




    }catch (e: Exception) {
            p2!!.reply(MessageManager.caughtErrorMessage(e.message!!))
            e.printStackTrace()
    }
        }
}