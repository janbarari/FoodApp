package io.github.janbarari.foodapp.data.network.response

import com.google.gson.annotations.SerializedName
import io.github.janbarari.foodapp.data.database.entity.Drink

class SearchByIngredientResponse {

    @SerializedName("drinks")
    lateinit var drinks: List<Drink>
}