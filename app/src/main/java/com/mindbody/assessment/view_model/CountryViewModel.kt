package com.mindbody.assessment.view_model

import android.view.HapticFeedbackConstants
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.mindbody.assessment.data_layer.exception.ApiException
import com.mindbody.assessment.data_layer.exception.NoDataException
import com.mindbody.assessment.data_layer.exception.NoInternetException
import com.mindbody.assessment.data_layer.repository.WorldRegionsRepository
import com.mindbody.assessment.models.CountryModel
import com.mindbody.assessment.ui.fragments.CountryListFragmentDirections
import kotlinx.coroutines.launch

class CountryViewModel(private val worldRegionsRepository: WorldRegionsRepository) :
    BaseViewModel() {

    override fun tryAgain(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
        toggleLayoutVisibility(
            contentVisibility = View.GONE,
            noInternetVisibility = View.GONE,
            noDataVisibility = View.GONE,
            errorVisibility = View.GONE
        )
        loadCountries()
    }

    private val _countriesListLiveData = MutableLiveData<List<CountryModel>?>()
    val countriesListLiveData: LiveData<List<CountryModel>?> = _countriesListLiveData

    /*
    * Below function is used to fetch list of Countries
    * */
    fun loadCountries() {

        loaderVisibility.postValue(View.VISIBLE)

        viewModelScope.launch {
            val countriesResponse = worldRegionsRepository.getCountries()
            if (countriesResponse.isSuccess) {
                val dataList = countriesResponse.getOrNull()
                if (!dataList.isNullOrEmpty()) {
                    _countriesListLiveData.postValue(dataList)
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
                when (countriesResponse.exceptionOrNull()) {
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

    /*
    * Navigate user from country page on selection of country to show province list for that particular country
    * */
    fun onCountrySelection(view: View, countryModel: CountryModel) {
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
        view.findNavController().navigate(
            CountryListFragmentDirections.actionCountryListFragmentToProvinceListFragment(
                countryModel = countryModel
            )
        )
    }

}