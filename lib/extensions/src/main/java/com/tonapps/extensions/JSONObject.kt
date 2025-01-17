package com.tonapps.extensions

import org.json.JSONObject

fun String.asJSON(): JSONObject {
    return try {
        JSONObject(this)
    } catch (e: Exception) {
        JSONObject(fixJson())
    }
}

fun JSONObject.optStringCompat(vararg keys: String): String? {
    for (key in keys) {
        val value = optString(key)
        if (!value.isNullOrBlank()) {
            return value
        }
    }
    return null
}

fun JSONObject.optStringCompatJS(vararg keys: String): String? {
    val value = optStringCompat(*keys)
    if (value == "null" || value == "undefined") { // Oh man... JavaScript compatibility
        return null
    }
    return value
}

fun JSONObject.getLongCompat(key: String): Long {
    return when (val value = opt(key)) {
        is Long -> value
        is Number -> value.toLong()
        is String -> value.toLong()
        else -> throw IllegalArgumentException("Value for key $key is not a Long")
    }
}