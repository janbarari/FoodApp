package io.github.janbarari.foodapp.ui.feature.search

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.janbarari.foodapp.data.database.entity.Drink
import io.github.janbarari.foodapp.data.repository.DrinkRepository
import io.github.janbarari.foodapp.data.repository.IngredientRepository
import io.github.janbarari.foodapp.helper.NoInternetException
import io.github.janbarari.foodapp.helper.coroutines.Coroutines
import io.github.janbarari.foodapp.ui.common.mapper.toArray

class SearchViewModel(private val drinkRepository: DrinkRepository, private val ingredientRepository: IngredientRepository) : ViewModel() {

    var recyclerViewState: Parcelable? = null
    var listener: ISearchFragment? = null
    var progressState: MutableLiveData<Boolean> = MutableLiveData(true)
    val drinks: MutableLiveData<List<Drink>> = MutableLiveData()
    val ingredients: MutableLiveData<Array<String>> = MutableLiveData()

    init {
        Coroutines.main {
            ingredients.postValue(ingredientRepository.getIngredients().toArray())
        }
    }

    fun searchFor(query: String) {
        Coroutines.main {
            try {
                drinks.postValue(drinkRepository.searchIngredient(query))
            } catch (e: NoInternetException) {
                listener?.onError(e)
            } catch (e: Exception) {
                listener?.onError(e)
            }
        }
    }
}