package com.example.gracetechnologiestest.simpleclasses


import com.example.gracetechnologiestest.datamodel.VideoDM
import org.json.JSONObject

object DataParser {

    fun videoModelParse(resp:JSONObject):VideoDM
    {
        val DM:VideoDM=VideoDM()
        DM.ex_id="${resp.optString("ex_id")}"
        DM.ex_title="${resp.optString("ex_title")}"
        DM.ex_identity="${resp.optString("ex_identity")}"
        DM.ex_description="${resp.optString("ex_description")}"
        DM.ex_duration=resp.optDouble("ex_duration",0.0)
        DM.ex_url="${resp.optString("ex_url")}"
        DM.ex_cat_id="${resp.optString("ex_cat_id")}"
        DM.ex_cat_name="${resp.optString("ex_cat_name")}"
        DM.ex_level_id="${resp.optString("ex_level_id")}"
        DM.ex_level_name="${resp.optString("ex_level_name")}"
        return DM
    }

}