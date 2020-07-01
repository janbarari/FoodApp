package io.github.janbarari.foodapp.data.repository

import androidx.lifecycle.MutableLiveData
import io.github.janbarari.foodapp.data.database.AppDatabase
import io.github.janbarari.foodapp.data.database.entity.Ingredient
import io.github.janbarari.foodapp.data.network.Api
import io.github.janbarari.foodapp.data.network.SafeApiRequest
import io.github.janbarari.foodapp.data.preference.PreferenceManager
import io.github.janbarari.foodapp.helper.coroutines.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * one hour by millisecond
 */
private const val MINIMUM_INTERVAL = 3_600_000

class IngredientRepository(
    private val api: Api,
    private val db: AppDatabase,
    private val preferenceManager: PreferenceManager
) : SafeApiRequest() {

    private val ingredients = MutableLiveData<List<Ingredient>>()

    init {
        ingredients.observeForever {
            saveIngredients(it)
        }
    }

    /**
     * Fetch ingredients from api
     */
    private suspend fun fetchIngredients() {
        val response = apiRequest {
            api.fetchIngredients()
        }
        ingredients.postValue(response.drinks)
    }

    suspend fun getIngredients(): List<Ingredient> {
        return withContext(Dispatchers.IO) {
            if (isFetchNeeded(preferenceManager.getIngredientLastSavedTime())) {
                fetchIngredients()
            }
            db.getIngredientDao().getIngredients()
        }
    }

    /**
     * returns true when last saved ingredients time is more than one hour
     */
    private fun isFetchNeeded(savedAt: Long): Boolean {
        return System.currentTimeMillis() - savedAt > MINIMUM_INTERVAL
    }

    /**
     * Save new ingredients to database, update last saved time
     */
    private fun saveIngredients(ingredient: List<Ingredient>) {
        Coroutines.io {
            preferenceManager.saveIngredientLastSavedAt(System.currentTimeMillis())
            db.getIngredientDao().saveAllIngredients(ingredient)
        }
    }
}