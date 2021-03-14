package com.fukuhara.designsystem

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class CharacterCard(private val view: View) {

    fun setEpisodeCardContent(
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String,
        avatarUrl: String,
        origin: String,
        location: String) {
        val typeTv: TextView = view.findViewById(R.id.ds_character_type)
        val typeLabelTv: TextView = view.findViewById(R.id.ds_character_type_label)
        val avatarIv: ImageView = view.findViewById(R.id.ds_character_avatar)

        view.findViewById<TextView>(R.id.ds_character_name).text = name
        view.findViewById<TextView>(R.id.ds_character_status).text = status
        view.findViewById<TextView>(R.id.ds_character_species).text = species
        view.findViewById<TextView>(R.id.ds_character_gender).text = gender
        view.findViewById<TextView>(R.id.ds_character_origin).text =
            view.resources.getString(R.string.ds_character_card_location_placeholder, origin)
        view.findViewById<TextView>(R.id.ds_character_location).text =
            view.resources.getString(R.string.ds_character_card_location_placeholder, location)

        if (type.isNotEmpty()) {
            typeTv.text = type
        } else {
            typeTv.visibility = View.GONE
            typeLabelTv.visibility = View.GONE
        }

        Glide
            .with(view)
            .load(avatarUrl)
            .centerCrop()
            .placeholder(R.drawable.no_image)
            .error(R.drawable.no_image)
            .into(avatarIv)
    }
}