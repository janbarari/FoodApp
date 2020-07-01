package io.github.janbarari.foodapp.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import io.github.janbarari.foodapp.helper.DEFAULT_LANGUAGE_CODE

const val KEY_INGREDIENT_SAVED_AT = "ingredient_saved_at"
const val LANGUAGE_CODE = "language_code"
class PreferenceManager(context: Context) {

    private var sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun getIngredientLastSavedTime(): Long {
        return sharedPreferences.getLong(KEY_INGREDIENT_SAVED_AT, 0L)
    }

    fun saveIngredientLastSavedAt(savedAt: Long) {
        sharedPreferences.edit().putLong(KEY_INGREDIENT_SAVED_AT, savedAt).apply()
    }

    fun getLanguageCode(): String {
        return sharedPreferences.getString(LANGUAGE_CODE, DEFAULT_LANGUAGE_CODE)!!
    }

    fun saveLanguageCode(languageCode: String) {
        sharedPreferences.edit().putString(LANGUAGE_CODE, languageCode).apply()
    }

}