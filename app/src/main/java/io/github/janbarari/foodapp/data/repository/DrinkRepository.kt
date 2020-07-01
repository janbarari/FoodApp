package io.github.janbarari.foodapp.data.repository

import io.github.janbarari.foodapp.data.database.entity.Drink
import io.github.janbarari.foodapp.data.network.Api
import io.github.janbarari.foodapp.data.network.SafeApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DrinkRepository(private val api: Api) : SafeApiRequest() {

    /**
     * Search ingredients by name, returns list of drinks, No cache
     */
    suspend fun searchIngredient(query: String): List<Drink> {
        return withContext(Dispatchers.IO) {
            val response = apiRequest {
                api.searchIngredient(query)
            }
            response.drinks
        }
    }

    /**
     * Fetch ingredient details with id, No cache
     */
    suspend fun fetchDrink(id: String): Drink {
        return withContext(Dispatchers.IO) {
            val response = apiRequest {
                api.fetchIngredientById(id)
            }
            response.ingredients[0]
        }
    }
}