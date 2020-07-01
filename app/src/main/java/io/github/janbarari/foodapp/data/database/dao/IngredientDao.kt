package io.github.janbarari.foodapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.janbarari.foodapp.data.database.entity.Ingredient

@Dao
interface IngredientDao {

    @Query("SELECT * FROM ingredient")
    fun getIngredients(): List<Ingredient>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllIngredients(drinks: List<Ingredient>)

}