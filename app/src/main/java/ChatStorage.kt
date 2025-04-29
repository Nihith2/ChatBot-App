package com.example.chatbot.ui

import android.content.Context
import android.content.SharedPreferences

object ChatStorage {

    private const val PREFS_NAME = "chatbot_prefs"
    private const val KEY_CHAT_HISTORY = "chat_history"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveChat(context: Context, chat: String) {
        val prefs = getPrefs(context)
        prefs.edit().putString(KEY_CHAT_HISTORY, chat).apply()
    }

    fun getChat(context: Context): String {
        val prefs = getPrefs(context)
        return prefs.getString(KEY_CHAT_HISTORY, "") ?: ""
    }

    fun clearChat(context: Context) {
        val prefs = getPrefs(context)
        prefs.edit().remove(KEY_CHAT_HISTORY).apply()
    }
}
