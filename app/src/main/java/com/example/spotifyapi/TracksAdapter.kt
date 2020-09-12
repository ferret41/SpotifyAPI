package com.example.spotifyapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.track_card.view.trackTitle

class TrackAdapter(val tracks: List<Track>) : RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val card =  layoutInflater.inflate(R.layout.track_card, parent, false)
        return TrackViewHolder(card)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.view.trackTitle.text = tracks.get(position).name
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

}

class TrackViewHolder(val view: View): RecyclerView.ViewHolder(view) {}