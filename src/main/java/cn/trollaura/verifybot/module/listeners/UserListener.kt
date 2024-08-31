package cn.trollaura.verifybot.module.listeners

import cn.trollaura.verifybot.Main
import cn.trollaura.verifybot.annotations.Listener
import cn.trollaura.verifybot.module.Module
import snw.jkook.entity.User
import snw.jkook.event.EventHandler
import snw.jkook.event.channel.ChannelMessageEvent
import snw.jkook.event.user.UserInfoUpdateEvent
import snw.jkook.message.Message
import snw.jkook.plugin.Plugin

/**
@author tRollaURa_
@since 2024/7/26
 */
@Listener
class UserListener(val plugin: Plugin): Module("userchat") {
  override fun onCommand(p0: User?, p1: Array<out Any>?, p2: Message?) {}

 @EventHandler
 fun onUserChat(event: ChannelMessageEvent) {
  if(Main.mute.contains(event.message.sender.id)) {
   event.message.delete()
  }
 }

 @EventHandler
 fun onUserRename(event: UserInfoUpdateEvent) {}
 }