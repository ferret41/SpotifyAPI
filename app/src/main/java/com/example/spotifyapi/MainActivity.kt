package com.example.spotifyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var token: Token
    lateinit var artists: Artists

    private val clientId: String = "613c438ba75c41c78f9147a0563d9f94"
    private val clientSecret: String =  "cc294bbdadf14934a80bfa1901c0a041"
    private val tokenEndpoint: String = "https://accounts.spotify.com/api/token"
    private val apiEndpoint: String = "https://api.spotify.com/v1/search"
    private val scopes: List<String> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyvlerView.layoutManager = LinearLayoutManager(this)
        recyvlerView.adapter = ListAdapter()

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
        Fuel.get(apiEndpoint, listOf(Pair("q", query), Pair("type", "artist")))
            .header(Pair("Authorization", "${token.token_type} ${token.access_token}"))
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        println("Result.Success !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                        val gson = GsonBuilder().create()
                        artists = gson.fromJson(result.value, Artists::class.java)
                        println("Succes parsed")

                    }
                    is Result.Failure -> {
                        println("ERROR! Result.Failure")
                    }
                }
            }
    }

}

class Token(val access_token: String, val token_type: String)

class Artists(val artists: Items)
class Items(val items: List<Artist>)
class Artist(val name: String, val uri: String, val href: String)