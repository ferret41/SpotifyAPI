package com.example.spotifyapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.artist_card.view.*

class ArtistAdapter(var artists: Artists): RecyclerView.Adapter<ArtistViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val card =  layoutInflater.inflate(R.layout.artist_card, parent, false)
        return ArtistViewHolder(card)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {

        holder.view.artistTextView.text = artists.items.get(position).name

        val artisImageView = holder.view.artistImageView
        if (artists.items.get(position).images.size > 0) Picasso.get().load(artists.items.get(position).images.get(0).url).into(artisImageView)

        val childLayoutManager = LinearLayoutManager(holder.itemView.albumsRecyclerView.context, RecyclerView.VERTICAL, false)

        holder.itemView.albumsRecyclerView.apply {
            layoutManager = childLayoutManager
            adapter = AlbumAdapter(artists.items.get(position).albums)
            setRecycledViewPool(viewPool)
        }

    }

    override fun getItemCount(): Int {
        return artists.items.size
    }
}

class ArtistViewHolder(val view: View): RecyclerView.ViewHolder(view) {}