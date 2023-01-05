package com.example.nerkharz.activity.market

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nerkharz.R
import com.example.nerkharz.activity.ApiManeger.BASE_URL_IMAGE
import com.example.nerkharz.activity.Model.Coins_data
import com.example.nerkharz.databinding.ItemRecyclerMarketBinding

class Adapter_market(
    val data: ArrayList<Coins_data.Data>,
    private val clictitemcoin: Clictitemcoin
) : RecyclerView.Adapter<Adapter_market.viewholder>() {
    lateinit var binding: ItemRecyclerMarketBinding

    inner class viewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        @SuppressLint("SetTextI18n")
        fun Bindview(data: Coins_data.Data) {
            binding.txtCoinName.text = data.coinInfo.fullName
            binding.txtPrice.text = "$" + data.rAW.uSD.pRICE.toString()
            binding.txtMarketCap.text = data.dISPLAY.uSD.mKTCAP
            val taghir = data.rAW.uSD.cHANGEPCT24HOUR
            if (taghir > 0) {
                binding.txtTaghir.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context, R.color.colorGain
                    )
                )
                binding.txtTaghir.text =
                    data.dISPLAY.uSD.cHANGEPCT24HOUR + "%"

            } else if (taghir < 0) {
                binding.txtTaghir.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context, R.color.colorLoss
                    )
                )
                binding.txtTaghir.text =
                    data.dISPLAY.uSD.cHANGEPCT24HOUR

            } else {
                binding.txtTaghir.text = "0%"
            }




            Glide
                .with(itemView)
                .load(BASE_URL_IMAGE + data.coinInfo.imageUrl)
                .into(binding.imgItem)

            itemView.setOnClickListener {
                clictitemcoin.Clictitem(data)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRecyclerMarketBinding.inflate(inflater, parent, false)
        return viewholder(binding.root)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.Bindview(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface Clictitemcoin {
        fun Clictitem(data: Coins_data.Data)
    }
}