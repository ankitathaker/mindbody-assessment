package com.mindbody.assessment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindbody.assessment.R
import com.mindbody.assessment.adapters.ProvinceAdapter
import com.mindbody.assessment.databinding.FragmentProvinceListBinding
import com.mindbody.assessment.models.CountryModel
import com.mindbody.assessment.models.ProvinceModel
import com.mindbody.assessment.utilities.GlideApp
import com.mindbody.assessment.view_model.ProvinceViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProvinceListFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by kodein()

    private val provinceViewModel: ProvinceViewModel by instance()

    private val args: ProvinceListFragmentArgs by navArgs()

    private lateinit var countryModel: CountryModel

    private lateinit var fragmentProvinceListBinding: FragmentProvinceListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*
       * Similar to CountryListFragment
       * */
        if (::fragmentProvinceListBinding.isInitialized)
            return fragmentProvinceListBinding.root

        fragmentProvinceListBinding =
            FragmentProvinceListBinding.inflate(inflater, container, false)

        countryModel = args.countryModel

        /*
        * Similar to CountryListFragment
        * */
        fragmentProvinceListBinding.apply {
            lifecycleOwner = this@ProvinceListFragment
            provinceViewModel = this@ProvinceListFragment.provinceViewModel
            countryModel = this@ProvinceListFragment.countryModel
            noDataFound.viewModel = this@ProvinceListFragment.provinceViewModel
            noInternetConnection.viewModel = this@ProvinceListFragment.provinceViewModel
            somethingWentWrong.viewModel = this@ProvinceListFragment.provinceViewModel

            GlideApp.with(requireContext())
                .load(this@ProvinceListFragment.countryModel.flagUrl)
                .into(ivCountryFlag)
        }
        viewModelOps()

        return fragmentProvinceListBinding.root
    }

    /*
    * Similar to CountryListFragment
    * */
    private fun viewModelOps() {
        provinceViewModel.countryId = countryModel.id
        provinceViewModel.loadProvinces()
        provinceViewModel.provinceListLiveData.observe(viewLifecycleOwner) { provinceList ->
            provinceList?.let { safeList ->
                setAdapter(provinceList = safeList)
            }
        }
        provinceViewModel.noDataVisibilityMutableLiveData.observe(viewLifecycleOwner) { visibility ->

            if (visibility == View.VISIBLE)
                provinceViewModel.noDataMessageLiveData.value = getString(R.string.no_province)
        }
    }

    private fun setAdapter(provinceList: List<ProvinceModel>) {

        val provinceAdapter = ProvinceAdapter(provinceList = provinceList)
        fragmentProvinceListBinding.rvProvince.adapter = provinceAdapter
        fragmentProvinceListBinding.rvProvince.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

    }

}