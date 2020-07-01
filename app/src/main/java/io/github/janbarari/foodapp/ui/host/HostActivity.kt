package io.github.janbarari.foodapp.ui.host

import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import io.github.janbarari.R
import io.github.janbarari.databinding.ActivityHostBinding
import io.github.janbarari.foodapp.ui.base.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HostActivity : BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()
    private val factory: HostViewModelFactory by instance<HostViewModelFactory>()

    private lateinit var binding: ActivityHostBinding
    private lateinit var viewModel: HostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWindowFlag(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            false
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_host)
        viewModel = ViewModelProviders.of(this, factory).get(HostViewModel::class.java)
        binding.lifecycleOwner = this
    }
}