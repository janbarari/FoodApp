package io.github.janbarari.foodapp.helper

import android.content.Context
import android.content.res.Configuration
import java.util.*

const val DEFAULT_LANGUAGE_CODE = "en"
object LanguageUtil {

    fun setLocale(context: Context, languageCode: String?) {
        languageCode?.let { code ->
            val locale = Locale(code)
            Locale.setDefault(locale)
            val configuration: Configuration = context.resources.configuration
            configuration.setLocale(locale)
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        }
    }

}