package com.fukuhara.designsystem

import android.view.View
import android.widget.TextView

class EpisodeCard(private val view: View) {

    fun setEpisodeCardContent(episodeName: String, episodeValue: String, episodeAirData: String) {
        view.findViewById<TextView>(R.id.ds_episode_name).text = episodeName
        view.findViewById<TextView>(R.id.ds_episode_value).text = episodeValue
        view.findViewById<TextView>(R.id.ds_episode_air_date).text = episodeAirData
    }
}