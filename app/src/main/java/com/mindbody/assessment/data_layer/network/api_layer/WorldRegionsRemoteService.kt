package com.mindbody.assessment.data_layer.network.api_layer

import com.mindbody.assessment.models.CountryModel
import com.mindbody.assessment.models.ProvinceModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/*
* Remote API service.
* */

interface WorldRegionsRemoteService {
    @GET("country")
    suspend fun getCountries(): Response<List<CountryModel>>

    @GET("country/{countryId}/province")
    suspend fun getProvinces(
        @Path("countryId") countryId: Int
    ): Response<List<ProvinceModel>>
}
