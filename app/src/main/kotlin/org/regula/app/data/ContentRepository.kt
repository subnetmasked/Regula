package org.regula.app.data

import android.content.Context
import android.util.Log
import androidx.core.content.edit

class ContentRepository(
    private val context: Context,
    private val database: RegulaDatabase,
) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    suspend fun syncIfNeeded() {
        val content = try {
            ContentLoader.load(context)
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to load content.json", exception)
            return
        }

        val storedVersion = preferences.getInt(KEY_CONTENT_VERSION, -1)
        if (storedVersion == content.contentVersion) {
            return
        }

        try {
            database.replaceContent(content)
            preferences.edit { putInt(KEY_CONTENT_VERSION, content.contentVersion) }
            Log.i(TAG, "Content synced to version ${content.contentVersion}")
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to sync content to database", exception)
        }
    }

    companion object {
        private const val TAG = "ContentRepository"
        private const val PREFS_NAME = "regula_content"
        private const val KEY_CONTENT_VERSION = "content_version"
    }
}
