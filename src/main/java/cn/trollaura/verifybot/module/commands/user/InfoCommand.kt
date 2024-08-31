package cn.trollaura.verifybot.module.commands.user

import cn.trollaura.verifybot.annotations.Command
import cn.trollaura.verifybot.managers.MessageManager
import cn.trollaura.verifybot.module.Module
import snw.jkook.entity.User
import snw.jkook.message.Message
import snw.jkook.plugin.Plugin

/**
@author tRollaURa_
@since 2024/7/26
 */
@Command
class InfoCommand(val plugin: Plugin): Module("info") {

 init {
  this.description = "获取服务器信息"
 }
 override fun onCommand(p0: User?, p1: Array<out Any>?, p2: Message?) {
  try {
   p2!!.reply(MessageManager.serverInfoMessage(getGuildID(plugin,p2)))
  }catch (e: Exception) {
   p2!!.reply(MessageManager.caughtErrorMessage(e.message!!))
   e.printStackTrace()
  }
 }

}