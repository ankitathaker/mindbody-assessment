package com.mindbody.assessment.data_layer.repository

import com.mindbody.assessment.data_layer.exception.ApiException
import com.mindbody.assessment.data_layer.exception.NoDataException
import com.mindbody.assessment.data_layer.exception.NoInternetException
import com.mindbody.assessment.data_layer.network.api_layer.WorldRegionsRemoteService
import com.mindbody.assessment.models.CountryModel
import com.mindbody.assessment.models.ProvinceModel
import retrofit2.Response
import java.net.UnknownHostException

/*
* Data layer:
* Repository to fetch countries and provinces
* */

class WorldRegionsRepository(private val worldRegionsRemoteService: WorldRegionsRemoteService) {

    /*
    * This function is used to handle response from retrofit remote service.
    * This method returns success or failure depending on data parsed or any exception.
    * */
    private suspend fun <T> handleResponse(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall.invoke()
            val responseBody = response.body()
            return if (response.isSuccessful) {

                return if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure<T>(NoDataException())
                }
            } else {
                Result.failure<T>(ApiException())
            }

        } catch (e: UnknownHostException) {
            Result.failure<T>(NoInternetException())
        }
    }

    suspend fun getCountries(): Result<List<CountryModel>> {
        return handleResponse {
            worldRegionsRemoteService.getCountries()
        }
    }

    suspend fun getProvinces(countryId: Int): Result<List<ProvinceModel>> {
        return handleResponse {
            worldRegionsRemoteService.getProvinces(countryId = countryId)
        }
    }
}