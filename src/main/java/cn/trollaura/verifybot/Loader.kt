package cn.trollaura.verifybot

import cn.trollaura.verifybot.annotations.Command
import cn.trollaura.verifybot.annotations.Listener
import cn.trollaura.verifybot.module.Module
import cn.trollaura.verifybot.module.commands.admin.*
import cn.trollaura.verifybot.module.commands.user.InfoCommand
import cn.trollaura.verifybot.module.commands.user.OrderCommand
import cn.trollaura.verifybot.module.commands.user.SponsorCommand
import cn.trollaura.verifybot.module.commands.user.VerificationCommand
import cn.trollaura.verifybot.util.ConfigUtil
import cn.trollaura.verifybot.util.ConfigUtil.get
import snw.jkook.command.JKookCommand
import snw.jkook.command.UserCommandExecutor
import snw.jkook.plugin.Plugin

/**
@author tRollaURa_
@since 2024/7/23
 */
class Loader(val plugin: Plugin) {
    var modules = mutableSetOf<Module>()

    init {
        ConfigUtil.init(plugin)
        modules.add(SetCommand(plugin))
        modules.add(VerificationCommand(plugin))
        modules.add(CreateCommand(plugin))
        modules.add(RemoveCommand(plugin))
        modules.add(InfoCommand(plugin))
        modules.add(SponsorCommand(plugin))
        modules.add(OrderCommand(plugin))
        modules.add(MuteCommand(plugin))
        modules.add(NamelockCommand(plugin))
    }



    fun init() {


        modules.add(HelpCommand(plugin))

        modules.filter { it::class.java.annotations.contains(Command()) }.forEach { cmd ->
            JKookCommand(cmd.name, get<String>("CommandPrefix"))
                .executesUser(cmd as UserCommandExecutor)
                .register(plugin)
        }

        modules.filter { it::class.java.annotations.contains(Listener()) }.forEach { listener ->
            plugin.core.eventManager.registerHandlers(plugin, listener as snw.jkook.event.Listener)

        }
    }
}