package com.example.spotifyapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.artist_card.view.*

class ListAdapter(val artists: Artists): RecyclerView.Adapter<ArtistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val card =  layoutInflater.inflate(R.layout.artist_card, parent, false)
        return ArtistViewHolder(card)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.view.artistTextView.text = artists.artists.items.get(position).name
    }

    override fun getItemCount(): Int {
        return artists.artists.items.size
    }


}

class ArtistViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}