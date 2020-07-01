package io.github.janbarari.foodapp.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.github.janbarari.foodapp.data.preference.PreferenceManager
import io.github.janbarari.foodapp.helper.LanguageUtil
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

abstract class BaseFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val preferenceManager: PreferenceManager by instance<PreferenceManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LanguageUtil.setLocale(context!!, preferenceManager.getLanguageCode())
    }
}