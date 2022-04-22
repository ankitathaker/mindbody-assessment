package com.mindbody.assessment.data_layer.network.api_layer

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* Initializes retrofit remote service.
* */
class RetrofitInitializer {
    companion object {
        var gson = GsonBuilder().setLenient().create()
        operator fun <T> invoke(
            okHttpClient: OkHttpClient,
            remoteServiceClass: Class<T>,
            baseUrl: String
        ): T {
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(remoteServiceClass)
        }
    }
}