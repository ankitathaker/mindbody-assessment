package com.mindbody.assessment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindbody.assessment.R
import com.mindbody.assessment.databinding.CardCountryBinding
import com.mindbody.assessment.models.CountryModel
import com.mindbody.assessment.utilities.GlideApp
import com.mindbody.assessment.view_model.CountryViewModel

class CountryAdapter(
    val countriesList: List<CountryModel>,
    var countryViewModel: CountryViewModel
) :
    RecyclerView.Adapter<CountryAdapter.CountryHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_country, parent, false)
        return CountryHolder(CardCountryBinding.bind(view))
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        val itemPojo = countriesList[position]
        holder.bind(itemPojo)
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    inner class CountryHolder(var layoutBinding: CardCountryBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {
        fun bind(model: CountryModel) {
            layoutBinding.apply {
                countryModel = model
                countryViewModel = this@CountryAdapter.countryViewModel

                /*
                * Used Glide library to display image
                * */
                GlideApp.with(root.context)
                    .load(model.flagUrl)
                    .into(ivCountryFlag)

            }
        }
    }
}