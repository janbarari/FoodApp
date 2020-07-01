package io.github.janbarari.foodapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.janbarari.R
import io.github.janbarari.foodapp.data.preference.PreferenceManager
import io.github.janbarari.foodapp.helper.LanguageUtil
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val preferenceManager: PreferenceManager by instance<PreferenceManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LanguageUtil.setLocale(this, preferenceManager.getLanguageCode())
        setTheme(R.style.AppTheme)
    }

    fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
}