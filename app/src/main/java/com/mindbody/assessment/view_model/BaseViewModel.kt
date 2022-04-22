package com.mindbody.assessment.view_model

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/*
* Creating an abstract class for implementation of common members needed for common layouts across pages.
* The layout changes depending on response and live data values.
* */

abstract class BaseViewModel : ViewModel() {
    val loaderVisibility = MutableLiveData(View.GONE)
    val contentVisibilityLiveData = MutableLiveData(View.GONE)
    val noInternetVisibilityMutableLiveData = MutableLiveData(View.GONE)
    val noDataVisibilityMutableLiveData = MutableLiveData(View.GONE)
    val errorVisibilityMutableLiveData = MutableLiveData(View.GONE)

    val noDataMessageLiveData = MutableLiveData<String>()

    abstract fun tryAgain(view: View)

    fun toggleLayoutVisibility(
        contentVisibility: Int,
        noInternetVisibility: Int,
        noDataVisibility: Int,
        errorVisibility: Int
    ) {
        contentVisibilityLiveData.postValue(contentVisibility)
        noInternetVisibilityMutableLiveData.postValue(noInternetVisibility)
        noDataVisibilityMutableLiveData.postValue(noDataVisibility)
        errorVisibilityMutableLiveData.postValue(errorVisibility)
    }
}