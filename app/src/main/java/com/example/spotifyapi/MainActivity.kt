package com.example.spotifyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.artist_card_old.*

class MainActivity : AppCompatActivity() {

    lateinit var token: Token

    private val clientId: String = "613c438ba75c41c78f9147a0563d9f94"
    private val clientSecret: String =  "cc294bbdadf14934a80bfa1901c0a041"
    private val tokenEndpoint: String = "https://accounts.spotify.com/api/token"
    private val apiEndpoint: String = "https://api.spotify.com/v1/search"
    private val scopes: List<String> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        artistsRecyclerView.layoutManager = LinearLayoutManager(this)

        getClientCredential()


    }

    fun getClientCredential() {
        tokenEndpoint.httpPost(
            listOf(
                "grant_type" to "client_credentials",
                "client_id" to clientId,
                "client_secret" to clientSecret,
                "scope" to scopes.joinToString(" ")
            )
        )
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val gson = GsonBuilder().create()
                        token = gson.fromJson(result.value, Token::class.java)
                        println(token.access_token)

                        getArtists("amaia")
                    }
                    is Result.Failure -> { }
                }
            }
    }

    fun getArtists(query: String) {
        Fuel.get(apiEndpoint, listOf(Pair("q", query), Pair("type", "artist"), Pair("limit", 5)))
            .header(Pair("Authorization", "${token.token_type} ${token.access_token}"))
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        println("Result.Success !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                        val gson = GsonBuilder().create()
                        val json = gson.fromJson(result.value, JSON::class.java)

                        for (a in json.artists.items) {
                            a.albums = getAlbums(a.id)
                        }

                        println("Succes parsed")

                        runOnUiThread {
                            artistsRecyclerView.adapter = ArtistAdapter(json.artists)
                        }

                    }
                    is Result.Failure -> {
                        println("ERROR! Result.Failure")
                    }
                }
            }
    }

    fun getAlbums(artistID: String) : List<Album> {
        val albumApiEndpoint = "https://api.spotify.com/v1/artists/$artistID/albums"

        val (request, response, result) = Fuel.get(albumApiEndpoint, listOf("limit" to 3))
            .header(Pair("Authorization", "${token.token_type} ${token.access_token}"))
            .responseString()
        when (result) {
            is Result.Success -> {
                val gson = GsonBuilder().create()
                val albums = gson.fromJson(result.value, Albums::class.java)
                for (a in albums.items) {
                    a.tracks = getTracks(a.id)
                }
                return albums.items
            }
            is Result.Failure -> {}
        }
        return emptyList<Album>()
    }

    fun getTracks(albumID: String) : List<Track> {
        val trackApiEndpoint = "https://api.spotify.com/v1/albums/$albumID/tracks"

        val (request, response, result) = Fuel.get(trackApiEndpoint, listOf("limit" to 10))
            .header(Pair("Authorization", "${token.token_type} ${token.access_token}"))
            .responseString()
        when (result) {
            is Result.Success -> {
                val gson = GsonBuilder().create()
                val tracks = gson.fromJson(result.value, Tracks::class.java)
                return tracks.items
            }
            is Result.Failure -> {}
        }
        return emptyList<Track>()
    }

}


class Token(val access_token: String, val token_type: String)

class Albums(val items: List<Album>)
class Tracks(val items: List<Track>)
class Track(val name: String)

class JSON(val artists: Artists)

class Artists(val items: List<Artist>)
class Artist(val name: String, val id: String, val href: String, var albums: List<Album>)
class Album(val name: String, val id: String, var tracks: List<Track>)
