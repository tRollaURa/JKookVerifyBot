package cn.trollaura.verifybot.module

import cn.trollaura.verifybot.annotations.Command
import cn.trollaura.verifybot.managers.MessageManager
import cn.trollaura.verifybot.util.ConfigUtil
import cn.trollaura.verifybot.util.ConfigUtil.get
import cn.trollaura.verifybot.util.DataUtil
import org.json.JSONArray
import org.json.JSONObject
import snw.jkook.command.UserCommandExecutor
import snw.jkook.entity.Guild
import snw.jkook.event.Listener
import snw.jkook.message.Message
import snw.jkook.plugin.Plugin

/**
@author tRollaURa_
@since 2024/7/23
 */
 abstract class Module(val name: String) : Listener,UserCommandExecutor {
     var justAdmin = false
     var description: String = "无建议"
   val prefix = ConfigUtil.get<String>("CommandPrefix")
    var usage: Array<String> = arrayOf("")


    constructor(name: String,usage: Array<String>) : this(name) {
        this.usage = usage
    }

    fun throwUsage(message: Message,module: Module) {
        message.reply(MessageManager.usageMessage(this))
    }

    fun getGuild(plugin: Plugin,message: Message): Guild {
        return plugin.core.httpAPI.getGuild(getGuildID(plugin,message))
    }

    fun getJsonArray(): JSONArray {
        return JSONArray(DataUtil.file.readText())
    }
    fun getGuildID(plugin: Plugin,message: Message): String {
        return plugin.core.httpAPI.getTextChannelMessage(message.id).channel.guild.id
    }


    fun checkIfServerAdmin(guildid: String,userID: String): Boolean {
        val jsonArray = getJsonArray()
        val json = DataUtil.getJSONObjectWithID(jsonArray,guildid)
        if(json==null) {
            return false
        }
        if(DataUtil.containsUserID(json!!.getJSONArray("Administrators"),userID) || ConfigUtil.plugin.core.httpAPI.getGuild(guildid).master == ConfigUtil.plugin.core.httpAPI.getUser(userID)) {
            return true
        }
        return false
    }

    fun checkIfBanned(guildid: String): Boolean {
        val jsonArray = JSONArray(DataUtil.file.readText())
        try {
            val json = DataUtil.getJSONObjectWithID(jsonArray,guildid)
            return json!!.getInt("Level") <= -1
        }catch (e: NullPointerException) {
            return false
        }


    }

 }
