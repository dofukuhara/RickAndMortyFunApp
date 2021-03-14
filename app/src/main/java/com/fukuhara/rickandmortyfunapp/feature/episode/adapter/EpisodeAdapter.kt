package com.fukuhara.rickandmortyfunapp.feature.episode.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fukuhara.common.ext.getSeasonAndEpisode
import com.fukuhara.rickandmortyfunapp.R
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeResultModel

open class EpisodeAdapter(
    private val dataset: List<EpisodeResultModel>
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val viewRoot = LayoutInflater.from(parent.context).inflate(R.layout.ds_card_episode, parent, false)

        return EpisodeViewHolder(viewRoot)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.onBind(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

    class EpisodeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val episodeName: TextView = view.findViewById(R.id.ds_episode_name)
        val episodeValue: TextView = view.findViewById(R.id.ds_episode_value)
        val episodeAirDate: TextView = view.findViewById(R.id.ds_episode_air_date)

        fun onBind(episodeResultModel: EpisodeResultModel) {
            val seasonEpisodePair = episodeResultModel.episode.getSeasonAndEpisode()
            val episodeValueString = seasonEpisodePair?.run {
                view.resources.getString(R.string.episode_season_and_epi_in_season_label, this.first, this.second)
            } ?: episodeResultModel.episode

            val episodeAirDateString = view.resources.getString(R.string.episode_air_date_label, episodeResultModel.air_date)

            episodeName.text = episodeResultModel.name
            episodeValue.text = episodeValueString
            episodeAirDate.text = episodeAirDateString
        }
    }
}