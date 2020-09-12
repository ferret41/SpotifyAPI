package com.example.spotifyapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.artist_card_old.view.*

class ArtistAdapter(var artists: Artists): RecyclerView.Adapter<ArtistViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val card =  layoutInflater.inflate(R.layout.artist_card, parent, false)
        return ArtistViewHolder(card)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {

        holder.itemView.tag = position
        holder.view.artistTextView.text = artists.items.get(position).name

        val childLayoutManager = LinearLayoutManager(holder.itemView.albumRecyvlerView.context, LinearLayout.VERTICAL, false)

        holder.itemView.albumRecyvlerView.apply {
            layoutManager = childLayoutManager
            adapter = AlbumAdapter(artists.items.get(position).albums)
            setRecycledViewPool(viewPool)
        }

        holder.view.artistTextView.text = artists.items.get(position).name
    }

    override fun getItemCount(): Int {
        return artists.items.size
    }
}

class ArtistViewHolder(val view: View): RecyclerView.ViewHolder(view) {}