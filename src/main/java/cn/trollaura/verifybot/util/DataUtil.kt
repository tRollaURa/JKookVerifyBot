package cn.trollaura.verifybot.util

import org.json.JSONArray
import org.json.JSONObject
import snw.jkook.plugin.Plugin
import java.io.File

object DataUtil {
    val filePath = "${ConfigUtil.plugin.file.parent}/${ConfigUtil.plugin.description.name}/data/Servers.json"
    val file = File(filePath)

    fun hasID(jsonArray: JSONArray, key: String): Boolean {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if(jsonObject.getString("ID") == key) {
                return true
            }
        }
        return false
    }


    fun containsUserID(administratorsArray: JSONArray, userID: String): Boolean {
        for (i in 0 until administratorsArray.length()) {
            if (administratorsArray.getString(i) == userID) {
                return true
            }
        }
        return false
    }
    fun setLevelJson(guildId: String,level: Int,jsonArray: JSONArray) {
        if(hasID(jsonArray,guildId)) {
            val json = getJSONObjectWithID(jsonArray,guildId)
            replaceJSONObjectInArray(jsonArray,json!!,json.put("Level",level))
            return
        }
        jsonArray.put(
            JsonObject(guildId, level,
            arrayListOf<String>().toCollection(ArrayList()),"","",arrayListOf<String>().toCollection(ArrayList())))
        file.writeText(jsonArray.toString(4))
    }

    fun replaceJSONObjectInArray(jsonArray: JSONArray, key: JSONObject, newJSONObject: JSONObject): Boolean {
        val index = getIndexWithJSONObject(jsonArray, key)
        return if (index != -1) {
            jsonArray.put(index, newJSONObject)
            file.writeText(jsonArray.toString(4))
            true
        } else {
            false
        }
    }

    fun getIndexWithJSONObject(jsonArray: JSONArray, key: JSONObject): Int {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (jsonObject.similar(key)) {
                return i
            }
        }
        return -1
    }

    fun getJSONObjectWithID(jsonArray: JSONArray,guildID: String): JSONObject? {
        return jsonArray.find { (it as JSONObject).getString("ID") == guildID } as JSONObject?

    }

    fun JsonObject(id: String,level: Int,administrators: Collection<String>,role: String,VerifyBackSide: String,keys: Collection<String>): JSONObject {
        return JSONObject()
            .put("ID",id)
            .put("Level",level)
            .put("Administrators",administrators)
            .put("Role",role)
            .put("VerifyBackSide",VerifyBackSide)
            .put("Key",keys)
    }
}