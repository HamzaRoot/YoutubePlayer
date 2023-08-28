package com.example.gracetechnologiestest.simpleclasses

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.gracetechnologiestest.Constants
import java.util.*
import java.util.regex.Pattern


@SuppressLint("StaticFieldLeak")
object Helper {

    fun printLogs(msg: String) {
        Log.d("${Constants.tag}", "${msg}")
    }

    fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    fun extractYouTubeVideoId(url: String): String {
        val pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(url)
        return if (matcher.find()) {
            matcher.group()
        } else {
            ""
        }
    }


}