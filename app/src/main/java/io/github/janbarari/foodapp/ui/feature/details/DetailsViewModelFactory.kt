package io.github.janbarari.foodapp.ui.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.janbarari.foodapp.data.repository.DrinkRepository

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(private val drinkRepository: DrinkRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(drinkRepository) as T
    }
}