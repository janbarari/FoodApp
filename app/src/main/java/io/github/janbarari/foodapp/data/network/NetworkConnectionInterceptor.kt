package io.github.janbarari.foodapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import io.github.janbarari.R
import io.github.janbarari.foodapp.helper.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Check is device internet is available, to prevent crash the OkHttp
 */
class NetworkConnectionInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) {
            throw NoInternetException(applicationContext.getString(R.string.no_internet_connection_message))
        }
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            } else {
                return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
            }
        }
        return result
    }

}