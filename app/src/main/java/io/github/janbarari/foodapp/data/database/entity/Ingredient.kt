package io.github.janbarari.foodapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ingredient")
class Ingredient(

    @SerializedName("strIngredient1")
    @PrimaryKey
    var strIngredient1: String
)