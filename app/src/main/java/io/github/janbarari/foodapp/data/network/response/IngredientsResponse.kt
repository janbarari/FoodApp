package io.github.janbarari.foodapp.data.network.response

import com.google.gson.annotations.SerializedName
import io.github.janbarari.foodapp.data.database.entity.Ingredient

class IngredientsResponse {

    @SerializedName("drinks")
    lateinit var drinks: List<Ingredient>

}