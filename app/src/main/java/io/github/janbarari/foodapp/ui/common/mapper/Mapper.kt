package io.github.janbarari.foodapp.ui.common.mapper

import io.github.janbarari.foodapp.data.database.entity.Drink
import io.github.janbarari.foodapp.data.database.entity.Ingredient
import io.github.janbarari.foodapp.ui.common.viewmodel.DrinkViewModel
import io.github.janbarari.genericrecyclerview.model.GenericViewModel

fun List<Drink>.toDrinkViewModel(): ArrayList<GenericViewModel>{
    val list = arrayListOf<GenericViewModel>()
    this.forEach {
        list.add(
            DrinkViewModel(
                it.strDrink,
                it.strDrinkThumb,
                it.idDrink
            )
        )
    }
    return list
}

fun Drink.toDrinkViewModel(): DrinkViewModel {
    return DrinkViewModel(
        this.strDrink,
        this.strDrinkThumb,
        this.idDrink
    )
}

fun List<Ingredient>.toArray(): Array<String> {
    val result: MutableList<String> = mutableListOf()
    this.forEach {
        result.add(it.strIngredient1)
    }
    return result.toTypedArray()
}