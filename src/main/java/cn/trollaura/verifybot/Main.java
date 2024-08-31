package cn.trollaura.verifybot;

import cn.trollaura.verifybot.managers.MessageManager;
import snw.jkook.message.Message;
import snw.jkook.plugin.BasePlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author tRollaURa_
 * @since 2024/5/19
 */
public class Main extends BasePlugin {
    public static List<String> mute = new ArrayList<>();
    public static Map<String,String> namelock = new HashMap<>();


    @Override
    public void onLoad() {

        saveDefaultConfig();

        reloadConfig();
    }

    @Override
    public void onEnable() {
        saveResource( "data/Servers.json",false,false);
        new Loader(this).init();
        MessageManager.INSTANCE.init();

    }

}
