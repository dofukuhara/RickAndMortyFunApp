package com.fukuhara.rickandmortyfunapp.feature.character.adater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fukuhara.designsystem.CharacterCard
import com.fukuhara.rickandmortyfunapp.R
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterResultModel

class CharacterAdapter(private val dataset: List<CharacterResultModel>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.ds_card_character, parent, false)

        return CharacterViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.onBind(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

    class CharacterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(characterResultModel: CharacterResultModel) {
            val characterCard = CharacterCard(view)

            characterCard.setEpisodeCardContent(
                characterResultModel.name,
                characterResultModel.status,
                characterResultModel.species,
                characterResultModel.type,
                characterResultModel.gender,
                characterResultModel.image,
                characterResultModel.origin.name,
                characterResultModel.location.name
            )
        }
    }
}