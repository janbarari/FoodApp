package io.github.janbarari.foodapp.data.database.dao

import androidx.room.*
import io.github.janbarari.foodapp.data.database.entity.Drink

@Dao
interface DrinkDao {

    @Query("SELECT * FROM drink")
    fun getDrinks(): List<Drink>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllDrinks(drinks: List<Drink>)

    @Query("DELETE FROM drink")
    fun deleteDrinks()

}