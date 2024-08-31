package cn.trollaura.verifybot.module.commands.admin

import cn.trollaura.verifybot.Main
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
class MuteCommand(val plugin: Plugin): Module("mute", arrayOf("<KookID>")) {
 override fun onCommand(p0: User?, p1: Array<out Any>?, p2: Message?) {
  try {
      if(!checkIfServerAdmin(getGuildID(plugin,p2!!),p0!!.id) && !plugin.config.getStringList("Administrator").contains(p0!!.id)) {
       p2!!.reply(MessageManager.nopermMessage)
       return
      }

   if(p1!!.size != 1) {
    p2!!.reply(MessageManager.usageMessage(this))
   }
   if(!checkIfServerAdmin(getGuildID(plugin,p2!!),p1[0].toString().replace("(met)","")) && !plugin.config.getStringList("Administrator").contains(p1[0].toString().replace("(met)",""))) {

    if (Main.mute.contains(p1[0].toString().replace("(met)", ""))) {
     Main.mute.remove(p1[0].toString().replace("(met)", ""))
     p2.reply(MessageManager.removeMuteMessage(p1[0].toString()))
     return
    }
    Main.mute.add(p1[0].toString().replace("(met)", ""))
    p2.reply(MessageManager.addMuteMessage(p1[0].toString()))

   }


  }catch (e: Exception) {
   p2!!.reply(MessageManager.caughtErrorMessage(e.message!!))
  }
 }

}

@Command
class NamelockCommand(val plugin: Plugin): Module("lock_name", arrayOf("<KookID>")) {
 override fun onCommand(p0: User?, p1: Array<out Any>?, p2: Message?) {
  try {
   if(!checkIfServerAdmin(getGuildID(plugin,p2!!),p0!!.id) && !plugin.config.getStringList("Administrator").contains(p0!!.id)) {
    p2!!.reply(MessageManager.nopermMessage)
    return
   }

   if(p1!!.size != 1) {
    p2!!.reply(MessageManager.usageMessage(this))
   }

   if(!checkIfServerAdmin(getGuildID(plugin,p2!!),p1[0].toString().replace("(met)","")) && !plugin.config.getStringList("Administrator").contains(p1[0].toString().replace("(met)",""))) {

    if (Main.namelock.contains(p1[0].toString().replace("(met)", ""))) {
     Main.namelock.remove(p1[0].toString().replace("(met)", ""))
     p2.reply(MessageManager.removeMuteMessage(p1[0].toString()))
     return
    }
    Main.namelock[p1[0].toString().replace("(met)", "")] =
     plugin.core.httpAPI.getUser(p1[0].toString().replace("(met)", "")).name
    p2.reply(MessageManager.addMuteMessage(p1[0].toString()))
   }



  }catch (e: Exception) {
   p2!!.reply(MessageManager.caughtErrorMessage(e.message!!))
   e.printStackTrace()
  }
 }

}