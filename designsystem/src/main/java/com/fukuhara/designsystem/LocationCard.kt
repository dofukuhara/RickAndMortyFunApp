package com.fukuhara.designsystem

import android.view.View
import android.widget.TextView

class LocationCard(private val view: View) {

    fun setLocationCardContent(locationName: String, locationType: String, dimension: String) {
        view.findViewById<TextView>(R.id.ds_location_name).text = locationName
        view.findViewById<TextView>(R.id.ds_location_type).text = locationType
        view.findViewById<TextView>(R.id.ds_location_dimension).text = dimension
    }
}