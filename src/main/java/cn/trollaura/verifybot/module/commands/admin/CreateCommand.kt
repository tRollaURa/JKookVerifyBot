package cn.trollaura.verifybot.module.commands.admin

import cn.trollaura.api.随机字符串
import cn.trollaura.verifybot.annotations.Command
import cn.trollaura.verifybot.managers.MessageManager
import cn.trollaura.verifybot.module.Module
import cn.trollaura.verifybot.util.DataUtil
import cn.trollaura.verifybot.util.VIPUtil
import snw.jkook.entity.User
import snw.jkook.message.Message
import snw.jkook.message.component.MarkdownComponent
import snw.jkook.message.component.card.element.MarkdownElement
import snw.jkook.plugin.Plugin

/**
@author tRollaURa_
@since 2024/7/26
 */
@Command
class CreateCommand(val plugin: Plugin): Module("create",arrayOf("<数量> <前缀>")) {
 init {
     this.description = "添加X个以Y为前缀的卡密"
  this.justAdmin = true
 }
 override fun onCommand(p0: User?, p1: Array<out Any>?, p2: Message?) {
  try {
   if(checkIfBanned(getGuildID(plugin,p2!!))) {
    p2.reply(MessageManager.bannedMessage)
    return
   }
   if (p1!!.size < 2) {
    p2!!.reply(MessageManager.usageMessage(this))
    return
   }
   if (!checkIfServerAdmin(getGuildID(plugin, p2!!), p0!!.id) && !plugin.config.getStringList("Administrator")
     .contains(p0!!.id)
   ) {
    p2.reply(MessageManager.nopermMessage)
    return
   }

   if (p1[0].toString().toInt() > VIPUtil.getLevelKeysCount(getGuildID(plugin, p2))) {
    p2.reply(MessageManager.spawnMaxMessage(getGuildID(plugin, p2)))
    return
   }
   val jsonArray = getJsonArray()
   val json = DataUtil.getJSONObjectWithID(jsonArray, getGuildID(plugin, p2))
   if(json == null) {
    p2.reply(MessageManager.needInitMessage)
    return
   }
   if (json!!.getJSONArray("Key").length() + p1[0].toString().toInt() > VIPUtil.getLevelKeysCount(
     getGuildID(
      plugin,
      p2
     )
    )
   ) {
    p2.reply(MessageManager.maxKeysMessage)
    return
   }
   try {
    val keys = mutableListOf<String>()
    for (i in 0 until p1[0].toString().toInt()) {
     val name = 随机字符串(20)
     keys.add("${p1[1]}-$name")

     DataUtil.replaceJSONObjectInArray(jsonArray, json, json.put("Key", json.getJSONArray("Key").put("${p1[1]}-$name")))
    }
    p0.sendPrivateMessage(keys.joinToString("\n"))
    p0.sendPrivateMessage(MarkdownComponent("**以上是您的卡密,悠着点**"))
    p2.reply(MessageManager.spawnKeysMessage(p1[0].toString().toInt(), getGuildID(plugin, p2)))

   } catch (e: NumberFormatException) {
    p2.reply(MessageManager.usageMessage(this))
    return
   }
  }catch (e: Exception) {
   p2!!.reply(MessageManager.caughtErrorMessage(e.message!!))
  }

 }
}