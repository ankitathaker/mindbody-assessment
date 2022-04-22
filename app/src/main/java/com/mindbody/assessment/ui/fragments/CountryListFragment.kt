package com.mindbody.assessment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.mindbody.assessment.adapters.CountryAdapter
import com.mindbody.assessment.databinding.FragmentCountryListBinding
import com.mindbody.assessment.models.CountryModel
import com.mindbody.assessment.view_model.CountryViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindbody.assessment.R

class CountryListFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by kodein()

    private val countryViewModel: CountryViewModel by instance()

    private lateinit var fragmentCountryListBinding: FragmentCountryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        /*
        * Checking if fragment binding is initialized or not.
        * When fragment is replaced on back press of next page, the page won't refresh.
        * Below check will return exisitng view without reinitiating binding and API call.
        * */
        if (::fragmentCountryListBinding.isInitialized)
            return fragmentCountryListBinding.root

        fragmentCountryListBinding = FragmentCountryListBinding.inflate(inflater, container, false)

        /*
        * Setting up data binding for XML components.
        * */
        fragmentCountryListBinding.apply {
            lifecycleOwner = this@CountryListFragment
            countryViewModel = this@CountryListFragment.countryViewModel
            noDataFound.viewModel = this@CountryListFragment.countryViewModel
            noInternetConnection.viewModel = this@CountryListFragment.countryViewModel
            somethingWentWrong.viewModel = this@CountryListFragment.countryViewModel
        }
        viewModelOps()

        return fragmentCountryListBinding.root
    }

    /*
    * This method has operations related to view model members.
    * API Call.
    * Setting observers to listen to value changes in live data.
    * */
    private fun viewModelOps() {
        countryViewModel.loadCountries()
        countryViewModel.countriesListLiveData.observe(viewLifecycleOwner) { countriesList ->
            countriesList?.let { safeList ->
                setAdapter(countriesList = safeList)
            }
        }
        countryViewModel.noDataVisibilityMutableLiveData.observe(viewLifecycleOwner){visibility ->
            if(visibility == View.VISIBLE)
                countryViewModel.noDataMessageLiveData.value = getString(R.string.no_countries)
        }
    }

    private fun setAdapter(countriesList: List<CountryModel>) {
        val countryAdapter = CountryAdapter(countriesList = countriesList, countryViewModel)
        fragmentCountryListBinding.rvCountry.adapter = countryAdapter
        fragmentCountryListBinding.rvCountry.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
    }
}