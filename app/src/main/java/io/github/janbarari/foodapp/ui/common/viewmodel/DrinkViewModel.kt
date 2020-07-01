package io.github.janbarari.foodapp.ui.common.viewmodel

import io.github.janbarari.genericrecyclerview.model.GenericViewModel

const val DRINK_ID = "idDrink"
const val DRINK_THUMBNAIL_URL = "strDrinkThumb"
const val DRINK_TITLE = "strDrink"

data class DrinkViewModel(
    var strDrink: String? = null,
    var strDrinkThumb: String? = null,
    var idDrink: String? = null

): GenericViewModel()