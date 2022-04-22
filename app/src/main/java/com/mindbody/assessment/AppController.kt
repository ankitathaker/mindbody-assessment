package com.mindbody.assessment

import android.app.Application
import com.google.gson.GsonBuilder
import com.mindbody.assessment.data_layer.network.api_layer.RetrofitInitializer
import com.mindbody.assessment.data_layer.network.api_layer.WorldRegionsRemoteService
import com.mindbody.assessment.data_layer.repository.WorldRegionsRepository
import com.mindbody.assessment.view_model.CountryViewModel
import com.mindbody.assessment.view_model.ProvinceViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.converter.gson.GsonConverterFactory

class AppController : Application(), KodeinAware {

    /*
    * Initialized Kodein to achieve dependency injection.
    * */

    override val kodein: Kodein = Kodein.lazy(true) {

        import(androidXModule(this@AppController))

        bind() from singleton {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }

        bind() from singleton { GsonConverterFactory.create(GsonBuilder().create()) }

        bind() from singleton {
            RetrofitInitializer.invoke(
                okHttpClient = instance(),
                remoteServiceClass = WorldRegionsRemoteService::class.java,
                baseUrl = "https://connect.mindbodyonline.com/rest/worldregions/"
            )
        }

        bind() from singleton { WorldRegionsRepository(worldRegionsRemoteService = instance()) }

        bind() from provider { CountryViewModel(worldRegionsRepository = instance()) }
        bind() from provider { ProvinceViewModel(worldRegionsRepository = instance()) }

    }

}