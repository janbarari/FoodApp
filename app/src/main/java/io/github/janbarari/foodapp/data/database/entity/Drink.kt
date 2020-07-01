package io.github.janbarari.foodapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "drink")
data class Drink(

    @SerializedName("idDrink")
    @PrimaryKey
    var idDrink: String,

    @SerializedName("strDrink")
    var strDrink: String? = null,

    @SerializedName("strDrinkThumb")
    var strDrinkThumb: String? = null,

    @SerializedName("strInstructions")
    var strInstructions: String? = null
)