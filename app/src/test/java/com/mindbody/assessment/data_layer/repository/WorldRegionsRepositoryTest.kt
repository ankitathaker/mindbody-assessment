package com.mindbody.assessment.data_layer.repository

import com.mindbody.assessment.data_layer.exception.NoDataException
import com.mindbody.assessment.data_layer.network.api_layer.WorldRegionsRemoteService
import com.mindbody.assessment.models.CountryModel
import com.mindbody.assessment.models.ProvinceModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class WorldRegionsRepositoryTest {

    @MockK
    private lateinit var worldRegionsRemoteService: WorldRegionsRemoteService

    @MockK
    private lateinit var worldRegionsRepository: WorldRegionsRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should return countries list on success`(): Unit = runBlocking {

        val resultResponse = listOf(
            mockkClass(CountryModel::class),
            mockkClass(CountryModel::class),
            mockkClass(CountryModel::class)
        )

        coEvery { worldRegionsRemoteService.getCountries() } returns Response.success(resultResponse)
        coEvery { worldRegionsRepository.getCountries() } returns Result.success(resultResponse)

        val actualResponse = worldRegionsRepository.getCountries()

        assertTrue {
            actualResponse.getOrNull()?.isNotEmpty() == true
        }
    }

    @Test
    fun `should return failure NoDataException on fetch countries empty response`() = runBlocking {
        val resultResponse = listOf<CountryModel>()

        coEvery { worldRegionsRemoteService.getCountries() } returns Response.success(resultResponse)
        coEvery { worldRegionsRepository.getCountries() } returns Result.failure(NoDataException())

        val actualResponse = worldRegionsRepository.getCountries()

        assertTrue {
            actualResponse.exceptionOrNull() is NoDataException
        }
    }

    @Test
    fun `should return provinces list on success`(): Unit = runBlocking {

        val countryId = 12

        val resultResponse = listOf(
            mockkClass(ProvinceModel::class),
            mockkClass(ProvinceModel::class),
            mockkClass(ProvinceModel::class)
        )

        coEvery { worldRegionsRemoteService.getProvinces(countryId) } returns Response.success(
            resultResponse
        )
        coEvery { worldRegionsRepository.getProvinces(countryId) } returns Result.success(
            resultResponse
        )

        val actualResponse = worldRegionsRepository.getProvinces(countryId)

        assertTrue {
            actualResponse.getOrNull()?.isNotEmpty() == true
        }
    }

    @Test
    fun `should return failure NoDataException on fetch provinces empty response`() = runBlocking {
        val countryId = 10

        val resultResponse = listOf<ProvinceModel>()

        coEvery { worldRegionsRemoteService.getProvinces(countryId) } returns Response.success(
            resultResponse
        )
        coEvery { worldRegionsRepository.getProvinces(countryId) } returns Result.failure(
            NoDataException()
        )

        val actualResponse = worldRegionsRepository.getProvinces(countryId)

        assertTrue {
            actualResponse.exceptionOrNull() is NoDataException
        }
    }

}