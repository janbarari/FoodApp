package io.github.janbarari.foodapp.ui.feature.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.janbarari.foodapp.data.database.entity.Drink
import io.github.janbarari.foodapp.data.repository.DrinkRepository
import io.github.janbarari.foodapp.helper.NoInternetException
import io.github.janbarari.foodapp.helper.coroutines.Coroutines
import java.lang.Exception

class DetailsViewModel(private val drinkRepository: DrinkRepository) : ViewModel() {

    lateinit var listener: IDetailsFragment
    var progressState: MutableLiveData<Boolean> = MutableLiveData(true)
    var drink: MutableLiveData<Drink> = MutableLiveData()

    fun fetchIngredient(id: String) {
        Coroutines.main {
            try {
                drink.postValue(drinkRepository.fetchDrink(id))
            } catch (e: NoInternetException) {
                listener.onError(e)
            } catch (e: Exception) {
                listener.onError(e)
            }
        }
    }

}