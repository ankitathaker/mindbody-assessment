package com.mindbody.assessment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindbody.assessment.R
import com.mindbody.assessment.databinding.CardProvinceBinding
import com.mindbody.assessment.models.ProvinceModel
import com.mindbody.assessment.view_model.ProvinceViewModel

class ProvinceAdapter(
    val provinceList: List<ProvinceModel>,
) : RecyclerView.Adapter<ProvinceAdapter.ProvinceHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProvinceHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_province, parent, false)
        return ProvinceHolder(CardProvinceBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ProvinceHolder, position: Int) {
        val itemPojo = provinceList[position]
        holder.bind(itemPojo)
    }

    override fun getItemCount(): Int {
        return provinceList.size
    }

    inner class ProvinceHolder(var layoutBinding: CardProvinceBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {
        fun bind(model: ProvinceModel) {
            layoutBinding.apply {
                provinceModel = model
            }
        }
    }
}