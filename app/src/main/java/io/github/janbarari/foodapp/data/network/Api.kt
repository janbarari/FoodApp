package io.github.janbarari.foodapp.data.network

import io.github.janbarari.foodapp.API_BASE_URL
import io.github.janbarari.foodapp.API_KEY
import io.github.janbarari.foodapp.X_RAPID_API_Host
import io.github.janbarari.foodapp.data.network.response.IngredientResponse
import io.github.janbarari.foodapp.data.network.response.IngredientsResponse
import io.github.janbarari.foodapp.data.network.response.SearchByIngredientResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit
import retrofit2.http.Query


const val NETWORK_PROCESS_TIMEOUT = 15L

interface Api {

    @GET("filter.php")
    suspend fun searchIngredient(@Query("i") query: String): Response<SearchByIngredientResponse>

    @GET("list.php?i=list")
    suspend fun fetchIngredients(): Response<IngredientsResponse>

    @GET("lookup.php")
    suspend fun fetchIngredientById(@Query("i") id: String): Response<IngredientResponse>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor) : Api {

            val apiKeyInterceptor = Interceptor { chain ->
                val headers = chain
                    .request()
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .header("charset", "UTF-8")
                    .addHeader("X-RapidAPI-Host",
                        X_RAPID_API_Host
                    )
                    .addHeader("X-RapidAPI-Key",
                        API_KEY
                    )
                    .build()

                return@Interceptor chain.proceed(headers)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addNetworkInterceptor(apiKeyInterceptor)
                .connectTimeout(NETWORK_PROCESS_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NETWORK_PROCESS_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_PROCESS_TIMEOUT, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }

}