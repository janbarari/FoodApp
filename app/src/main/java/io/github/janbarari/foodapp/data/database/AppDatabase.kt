package io.github.janbarari.foodapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.janbarari.foodapp.APP_DATABASE_NAME
import io.github.janbarari.foodapp.APP_DATABASE_VERSION
import io.github.janbarari.foodapp.data.database.dao.DrinkDao
import io.github.janbarari.foodapp.data.database.dao.IngredientDao
import io.github.janbarari.foodapp.data.database.entity.Drink
import io.github.janbarari.foodapp.data.database.entity.Ingredient

@Database(entities = [Drink::class, Ingredient::class], version = APP_DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDrinkDao(): DrinkDao

    abstract fun getIngredientDao(): IngredientDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            APP_DATABASE_NAME
        ).build()
    }

}