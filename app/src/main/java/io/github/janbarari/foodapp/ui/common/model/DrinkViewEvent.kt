package io.github.janbarari.foodapp.ui.common.model

import android.view.View
import io.github.janbarari.foodapp.ui.common.viewmodel.DrinkViewModel

data class DrinkViewEvent(val viewModel: DrinkViewModel, val root: View)