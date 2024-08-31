package cn.trollaura.verifybot.util

import org.json.JSONArray

object VIPUtil {
    fun getLevelKeysCount(guildid: String): Int {
        val jsonArray = JSONArray(DataUtil.file.readText())
        val json = DataUtil.getJSONObjectWithID(jsonArray,guildid)
       return if(json!!.getInt("Level") == 0) { 30 } else { 999 }
    }

    fun getMaxSpawn(guildid: String): Int {
        val jsonArray = JSONArray(DataUtil.file.readText())
        val json = DataUtil.getJSONObjectWithID(jsonArray,guildid)
        return if(json!!.getInt("Level") == 0) { 5 } else { 20 }
    }

    fun getLevelName(guildid: String) : String
    {
        val jsonArray = JSONArray(DataUtil.file.readText())
        val json = DataUtil.getJSONObjectWithID(jsonArray,guildid)
        if(json!!.getInt("Level") < 0) {
            return "未授权"
        }else if(json.getInt("Level") == 0 ){
            return "普通用户"
        }else if(json.getInt("Level") > 0) {
            return "高级用户"
        }
        return "未授权"
    }

}