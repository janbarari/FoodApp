package io.github.janbarari.foodapp.ui.feature.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import io.github.janbarari.R
import io.github.janbarari.foodapp.data.preference.PreferenceManager
import io.github.janbarari.foodapp.helper.LanguageUtil
import io.github.janbarari.foodapp.helper.selfRestart
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SettingsFragment : PreferenceFragmentCompat(), KodeinAware {

    override val kodein by kodein()
    private val preferenceManager: PreferenceManager by instance<PreferenceManager>()

    private var languageSettings: Preference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        LanguageUtil.setLocale(context!!, preferenceManager.getLanguageCode())

        addPreferencesFromResource(R.xml.app_preference)

        languageSettings = findPreference("language_preference_key")
        configLanguageSettings()
        languageSettings?.setOnPreferenceClickListener {
            activity?.let {
                var initialSelection = 0
                val savedLanguageCode: String = preferenceManager.getLanguageCode()
                val languageCodes = resources.getStringArray(R.array.language_codes)
                for ((index, languageCode) in languageCodes.withIndex()) {
                    if (languageCode == savedLanguageCode) {
                        initialSelection = index
                    }
                }
                MaterialDialog(it)
                    .cornerRadius(4f)
                    .title(R.string.language)
                    .show {
                        listItemsSingleChoice(
                            R.array.languages,
                            initialSelection = initialSelection
                        ) { _, index, _ ->
                            val selectedLanguageCode = resources.getStringArray(R.array.language_codes)[index]
                            preferenceManager.saveLanguageCode(selectedLanguageCode)
                            configLanguageSettings()
                            activity?.let { activity ->
                                LanguageUtil.setLocale(activity, selectedLanguageCode)
                                activity.selfRestart()
                            }
                        }
                    }
            }
            true
        }

    }

    private fun configLanguageSettings() {
        val savedLanguageCode = preferenceManager.getLanguageCode()
        val languageCodes = resources.getStringArray(R.array.language_codes)
        for ((index, languageCode) in languageCodes.withIndex()) {
            if (languageCode == savedLanguageCode) {
                val summery = resources.getStringArray(R.array.languages)[index]
                languageSettings?.summary = summery
            }
        }
    }

}