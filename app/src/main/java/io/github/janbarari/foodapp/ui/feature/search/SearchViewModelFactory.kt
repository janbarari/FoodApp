package io.github.janbarari.foodapp.ui.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.janbarari.foodapp.data.repository.DrinkRepository
import io.github.janbarari.foodapp.data.repository.IngredientRepository

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(private val drinkRepository: DrinkRepository,private val ingredientRepository: IngredientRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(drinkRepository, ingredientRepository) as T
    }
}