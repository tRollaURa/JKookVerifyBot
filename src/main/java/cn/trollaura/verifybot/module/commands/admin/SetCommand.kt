package cn.trollaura.verifybot.module.commands.admin

import cn.trollaura.verifybot.annotations.Command
import cn.trollaura.verifybot.managers.MessageManager
import cn.trollaura.verifybot.module.Module
import cn.trollaura.verifybot.util.ConfigUtil.get
import cn.trollaura.verifybot.util.DataUtil
import org.json.JSONArray
import snw.jkook.entity.User
import snw.jkook.message.Message
import snw.jkook.plugin.Plugin

/**
@author tRollaURa_
@since 2024/7/26
 */
@Command
class SetCommand(val plugin: Plugin) : Module("set", arrayOf("<等级>","<role> <身份组名>","<admin> <KookID>","<backside> <后台频道ID>")) {
 init {
  this.description = "设置某些值"
  this.justAdmin = true
 }
 override fun onCommand(p0: User?, p1: Array<out Any>?, p2: Message?) {
  try {


  if(p1!!.isEmpty()) {
   p2!!.reply(MessageManager.usageMessage(this))
  }
  if(p1.size == 1) {
   if(!plugin.config.getStringList("Administrator").contains(p0!!.id)) {
    p2!!.reply(MessageManager.nopermMessage)
    return
   }
   try {
    if (p1[0].toString().toInt() is Int) {
     val jsonArray = getJsonArray()
     DataUtil.setLevelJson(
      getGuildID(plugin,p2!!),
      p1!![0].toString().toInt(),
      jsonArray
     )
     p2.reply(MessageManager.alreadySetMessage(p1[0].toString().toInt()))
    } else {
     p2!!.reply(MessageManager.usageMessage(this))
    }
   }catch (e: NumberFormatException) {
    p2!!.reply(MessageManager.usageMessage(this))
   }

  }

  if(p1.size == 2) {
   if(!checkIfServerAdmin(getGuildID(plugin,p2!!),p0!!.id) && !plugin.config.getStringList("Administrator").contains(p0!!.id)) {
    p2.reply(MessageManager.nopermMessage)
    return
   }
   when(p1[0].toString().lowercase()) {
    "role" -> {
     val jsonArray = getJsonArray()
     val jsonObject = DataUtil.getJSONObjectWithID(jsonArray,getGuildID(plugin,p2))
     DataUtil.replaceJSONObjectInArray(jsonArray,jsonObject!!,jsonObject.put("Role",p1[1].toString().replace("(rol)","")))


     p2.reply(MessageManager.alreadySetRoleMessage(p1[1].toString()))
    }
    "admin" -> {
     if(!plugin.config.getStringList("Administrator").contains(p0!!.id)) {
      p2!!.reply(MessageManager.nopermMessage)
      return
     }
     val jsonArray = getJsonArray()
     val jsonObject = DataUtil.getJSONObjectWithID(jsonArray,getGuildID(plugin,p2))
     if(jsonObject!!.getJSONArray("Administrators").contains(p1[1].toString())) {
      DataUtil.replaceJSONObjectInArray(jsonArray,jsonObject,jsonObject.put("Administrators",jsonObject.getJSONArray("Administrators").filter { it.toString() != p1[1].toString().replace("(met)","") }))
      p2.reply(MessageManager.alreadyunSetAdminMessage(p1[1].toString()))
      return
     }
     DataUtil.replaceJSONObjectInArray(jsonArray,jsonObject,jsonObject.put("Administrators",jsonObject.getJSONArray("Administrators").put(p1[1].toString().replace("(met)",""))))
     p2.reply(MessageManager.alreadySetAdminMessage(p1[1].toString()))
    }
    "backside" -> {
     val jsonArray = getJsonArray()
     val jsonObject = DataUtil.getJSONObjectWithID(jsonArray,getGuildID(plugin,p2))
     DataUtil.replaceJSONObjectInArray(jsonArray,jsonObject!!,jsonObject.put("VerifyBackSide",p1[1].toString()))
     p2.reply(MessageManager.alreadySetBackSideMessage(p1[1].toString()))
    }
    else ->  p2.reply(MessageManager.usageMessage(this))
   }
  }
   }catch (e: Exception) {
   p2!!.reply(MessageManager.caughtErrorMessage(e.message!!))
   }

 }

}