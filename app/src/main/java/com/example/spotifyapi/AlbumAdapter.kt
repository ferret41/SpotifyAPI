package com.example.spotifyapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.album_card.view.albumTitle
import kotlinx.android.synthetic.main.album_card.view.tracksRecyclerView

class AlbumAdapter(val albums: List<Album>) : RecyclerView.Adapter<AlbumViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val card =  layoutInflater.inflate(R.layout.album_card, parent, false)
        return AlbumViewHolder(card)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {

        holder.view.albumTitle.text = albums.get(position).name

        val childLayoutManager = LinearLayoutManager(holder.itemView.tracksRecyclerView.context, RecyclerView.VERTICAL, false)

        holder.itemView.tracksRecyclerView.apply {
            layoutManager = childLayoutManager
            adapter = TrackAdapter(albums.get(position).tracks)
            setRecycledViewPool(viewPool)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }
}

class AlbumViewHolder(val view: View): RecyclerView.ViewHolder(view) {}