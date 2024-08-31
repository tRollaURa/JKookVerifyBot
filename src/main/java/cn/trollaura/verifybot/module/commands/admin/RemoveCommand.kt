package cn.trollaura.verifybot.module.commands.admin

import cn.trollaura.verifybot.annotations.Command
import cn.trollaura.verifybot.managers.MessageManager
import cn.trollaura.verifybot.module.Module
import cn.trollaura.verifybot.util.DataUtil
import snw.jkook.entity.User
import snw.jkook.message.Message
import snw.jkook.plugin.Plugin

/**
@author tRollaURa_
@since 2024/7/26
 */
@Command
class RemoveCommand(val plugin: Plugin): Module("remove", arrayOf("<卡密>")) {
 init {
  this.description = "删除为X的卡密"
  this.justAdmin = true
 }
 override fun onCommand(p0: User?, p1: Array<out Any>?, p2: Message?) {
  try {
   if (checkIfBanned(getGuildID(plugin, p2!!))) {
    p2.reply(MessageManager.bannedMessage)
    return
   }
   if (!checkIfServerAdmin(getGuildID(plugin, p2!!), p0!!.id) && !plugin.config.getStringList("Administrator")
     .contains(p0!!.id)
   ) {
    p2.reply(MessageManager.nopermMessage)
    return
   }
   if (p1!!.isEmpty()) {
    throwUsage(p2, this)
    return
   }

   val jsonObject = DataUtil.getJSONObjectWithID(getJsonArray(), getGuildID(plugin, p2))

   if(jsonObject == null) {
    p2.reply(MessageManager.needInitMessage)
    return
   }

   val keys = jsonObject!!.getJSONArray("Key")
   if (!DataUtil.containsUserID(keys,p1[0].toString())) {
    p2.reply(MessageManager.noKeyMessage)
    p2.delete()
    return
   }
     DataUtil.replaceJSONObjectInArray(getJsonArray(),jsonObject,jsonObject.put("Key",jsonObject.getJSONArray("Key").filter { it.toString() != p1[0].toString() }))

  }catch (e: Exception) {
   p2!!.reply(MessageManager.caughtErrorMessage(e.message!!))
  }
 }
}