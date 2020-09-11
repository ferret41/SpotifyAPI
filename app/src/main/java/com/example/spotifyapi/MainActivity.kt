package com.example.spotifyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_main.*

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
                    }
                    is Result.Failure -> { }
                }
            }
    }
}

class Token(val access_token: String, val token_type: String)