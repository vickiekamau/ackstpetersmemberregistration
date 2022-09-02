package com.ack.stpeters.network

import androidx.lifecycle.LiveData
import com.ack.stpeters.model.FetchMembers
import com.ack.stpeters.model.Member
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {


    @POST("stpetersapi/v1/member")
    fun saveData(@Body member: Member): Call<Member>

    @POST("stpetersapi/v1/member")
    suspend fun saveMembers(@Body member: Member): Member

    @GET("stpetersapi/v1/member")
    suspend fun getMembers(): List<FetchMembers>


    //We need to prepare a custom OkHttp client because need to use our custom call interceptor.
    // to be able to authenticate our requests.
    companion object {

        operator fun invoke(): ApiInterface {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create();
            //val builder = OkHttpClient.Builder()
            // We add the interceptor to OkHttpClient.
            // It will add authentication headers to every call we make.
            // builder.interceptors().add(BasicInterceptor("MOBILE","Mobile@1"))
            //val client = builder.build()


            val api = Retrofit.Builder() // Create retrofit builder.
                .baseUrl("http://41.139.209.115:8130/") // Base url for the api
                .addConverterFactory(GsonConverterFactory.create(gson)) // Use GSON converter for JSON to POJO object mapping.
                .client(client) // Here we set the custom OkHttp client we just created.
                .build()
                .create(ApiInterface::class.java) // We create an API using the interface we defined.
            return api

        }
    }
}

