package data.local.preferences
import data.local.SharedPreferencesKeys.PREFERENCES_FILE_NAME
import data.local.SharedPreferencesKeys.PREFERENCES_SAVE_COMMENTS
import java.io.File
import java.util.*

object Preferences {
    private val file = File(PREFERENCES_FILE_NAME)
    private val properties = Properties().apply {
        if (file.exists()) {
            load(file.inputStream())
        }
    }

    fun set(key: String, value: String) {
        properties.setProperty(key, value)
        save()
    }

    fun set(key: String, value: Int) = set(key, value.toString())
    fun set(key: String, value: Long) = set(key, value.toString())
    fun set(key: String, value: Float) = set(key, value.toString())
    fun set(key: String, value: Double) = set(key, value.toString())
    fun set(key: String, value: Boolean) = set(key, value.toString())

    fun getString(key: String, default: String = ""): String =
        properties.getProperty(key, default)

    fun getInt(key: String, default: Int = 0): Int =
        properties.getProperty(key)?.toIntOrNull() ?: default

    fun getLong(key: String, default: Long = 0L): Long =
        properties.getProperty(key)?.toLongOrNull() ?: default

    fun getFloat(key: String, default: Float = 0f): Float =
        properties.getProperty(key)?.toFloatOrNull() ?: default

    fun getDouble(key: String, default: Double = 0.0): Double =
        properties.getProperty(key)?.toDoubleOrNull() ?: default

    fun getBoolean(key: String, default: Boolean = false): Boolean =
        properties.getProperty(key)?.toBooleanStrictOrNull() ?: default

    fun remove(key: String) {
        properties.remove(key)
        save()
    }

    fun clear() {
        properties.clear()
        save()
    }

    private fun save() {
        file.outputStream().use {
            properties.store(it, PREFERENCES_SAVE_COMMENTS)
        }
    }
}