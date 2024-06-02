package com.example.praktikum06

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.praktikum06.adapter.PlayerAdapterRetrofit
import com.example.praktikum06.api.ApiConfig
import com.example.praktikum06.model.Player
import com.example.praktikum06.model.PlayerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var playerAdapter: PlayerAdapterRetrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_item)
        recyclerView.layoutManager = LinearLayoutManager(this)

        getPlayers()
    }

    private fun getPlayers() {
        val client = ApiConfig.getApiService().getPlayers()
        client.enqueue(object : Callback<PlayerResponse> {
            override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
                if (response.isSuccessful) {
                    val playerList = response.body()?.data ?: emptyList()
                    playerAdapter = PlayerAdapterRetrofit(playerList, object : PlayerAdapterRetrofit.OnItemClickCallback {
                        override fun onItemClicked(data: Player) {
                            Toast.makeText(this@MainActivity, data.name, Toast.LENGTH_SHORT).show()
                        }
                    })
                    recyclerView.adapter = playerAdapter
                }
            }

            override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
