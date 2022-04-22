package com.mindbody.assessment.view_model

import android.view.HapticFeedbackConstants
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mindbody.assessment.data_layer.exception.ApiException
import com.mindbody.assessment.data_layer.exception.NoDataException
import com.mindbody.assessment.data_layer.exception.NoInternetException
import com.mindbody.assessment.data_layer.repository.WorldRegionsRepository
import com.mindbody.assessment.models.ProvinceModel
import kotlinx.coroutines.launch

class ProvinceViewModel(private val worldRegionsRepository: WorldRegionsRepository) :
    BaseViewModel() {

    override fun tryAgain(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
        toggleLayoutVisibility(
            contentVisibility = View.GONE,
            noInternetVisibility = View.GONE,
            noDataVisibility = View.GONE,
            errorVisibility = View.GONE
        )
        loadProvinces()
    }

    private val _provinceListLiveData = MutableLiveData<List<ProvinceModel>?>()
    val provinceListLiveData: LiveData<List<ProvinceModel>?> = _provinceListLiveData

    var countryId = 0

    /*
    * Below function is used to fetch list of provinces.
    * */
    fun loadProvinces() {
        loaderVisibility.postValue(View.VISIBLE)

        viewModelScope.launch {
            val provincesResponse = worldRegionsRepository.getProvinces(countryId = countryId)
            if (provincesResponse.isSuccess) {
                val dataList = provincesResponse.getOrNull()
                if (!dataList.isNullOrEmpty()) {
                    _provinceListLiveData.postValue(dataList)
                    toggleLayoutVisibility(
                        contentVisibility = View.VISIBLE,
                        noInternetVisibility = View.GONE,
                        noDataVisibility = View.GONE,
                        errorVisibility = View.GONE
                    )
                } else {
                    toggleLayoutVisibility(
                        contentVisibility = View.GONE,
                        noInternetVisibility = View.GONE,
                        noDataVisibility = View.VISIBLE,
                        errorVisibility = View.GONE
                    )
                }
                loaderVisibility.postValue(View.GONE)
            } else {
                when (provincesResponse.exceptionOrNull()) {
                    is NoDataException -> {
                        toggleLayoutVisibility(
                            contentVisibility = View.GONE,
                            noInternetVisibility = View.GONE,
                            noDataVisibility = View.VISIBLE,
                            errorVisibility = View.GONE
                        )
                        loaderVisibility.postValue(View.GONE)
                    }
                    is NoInternetException -> {
                        toggleLayoutVisibility(
                            contentVisibility = View.GONE,
                            noInternetVisibility = View.VISIBLE,
                            noDataVisibility = View.GONE,
                            errorVisibility = View.GONE
                        )
                        loaderVisibility.postValue(View.GONE)
                    }
                    is ApiException -> {
                        toggleLayoutVisibility(
                            contentVisibility = View.GONE,
                            noInternetVisibility = View.GONE,
                            noDataVisibility = View.GONE,
                            errorVisibility = View.VISIBLE
                        )
                        loaderVisibility.postValue(View.GONE)
                    }
                }
            }
        }
    }
}