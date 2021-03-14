package com.fukuhara.rickandmortyfunapp.feature.location.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fukuhara.designsystem.LocationCard
import com.fukuhara.rickandmortyfunapp.R
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationResultModel

class LocationAdapter(private val dataset: List<LocationResultModel>) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.ds_card_location, parent, false)

        return LocationViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.onBind(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

    class LocationViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(locationResultModel: LocationResultModel) {
            val locationCard = LocationCard(view)
            locationCard.setLocationCardContent(
                locationResultModel.name,
                locationResultModel.type,
                locationResultModel.dimension
            )
        }
    }
}