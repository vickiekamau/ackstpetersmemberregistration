package com.ack.stpeters.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

class NeworkInceptionInterceptor (
    context: Context
    ) : Interceptor {
        private val applicationContext = context.applicationContext
        @RequiresApi(Build.VERSION_CODES.M)
        override fun intercept(chain: Interceptor.Chain): Response {
            return if (!isConnectionOn()) {
                throw IOException("Connection Issues, Check your connection")
            } else if (!isInternetAvailable()) {
                throw IOException("No Internet, Check your connection")
            } else {
                val response = chain.proceed(chain.request())
                Timber.i("RESPONSE CODE: ${response.code}")
                if (!response.isSuccessful) {
                    val error = response.message
                    Timber.e(error)
                    throw  IOException(error)
                } else {
                    response
                }
            }
        }

        /**
         * Checks if connection is on
         */
        @RequiresApi(Build.VERSION_CODES.M)
        private fun isConnectionOn(): Boolean {
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as
                        ConnectivityManager

            return postAndroidMInternetCheck(connectivityManager)
        }


        /**
         * Check connection for post M devices
         */
        @RequiresApi(Build.VERSION_CODES.M)
        private fun postAndroidMInternetCheck(
            connectivityManager: ConnectivityManager
        ): Boolean {
            val network = connectivityManager.activeNetwork
            val connection =
                connectivityManager.getNetworkCapabilities(network)


            return connection != null && connection.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_TRUSTED
            )
        }


        /**
         * Checks if Internet is available
         */
        private fun isInternetAvailable(): Boolean {
            return true
        }
    }
