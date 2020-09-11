package com.example.spotifyapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListAdapter: RecyclerView.Adapter<ArtistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val card =  layoutInflater.inflate(R.layout.artist_card, parent, false)
        return ArtistViewHolder(card)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }


}

class ArtistViewHolder(v: View): RecyclerView.ViewHolder(v) {

}