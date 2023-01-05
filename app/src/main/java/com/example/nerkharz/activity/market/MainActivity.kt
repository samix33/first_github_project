package com.example.nerkharz.activity.market

import CoinAboutItem
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nerkharz.activity.ApiManeger.ApiManger
import com.example.nerkharz.activity.Coin.MainActivity2
import com.example.nerkharz.activity.Model.Coin_About_data
import com.example.nerkharz.activity.Model.Coins_data
import com.example.nerkharz.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), Adapter_market.Clictitemcoin {
    lateinit var binding: ActivityMainBinding

    lateinit var datanewes: ArrayList<Pair<String, String>>
    lateinit var aboutdataMAp: MutableMap<String, CoinAboutItem>
    val ApiManger = ApiManger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.swipeRefreshMain.setOnRefreshListener {
            lodingdata()
            binding.majolgeymat.btnMore.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cryptocompare.com"))
                startActivity(intent)
            }
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshMain.isRefreshing = false
            }, 1500)
        }

    }

    private fun lodingdata() {
        lodingAbut()
        lodingnewes()
        lodingCoins()
    }

    private fun lodingnewes() {
        ApiManger.getnewez(object : ApiManger.ApiCallback<ArrayList<Pair<String, String>>> {
            override fun onSccess(data: ArrayList<Pair<String, String>>) {
                datanewes = data
                refreshnewes()

            }

            override fun onEror(errormessage: String) {
                Toast.makeText(this@MainActivity, errormessage, Toast.LENGTH_SHORT).show()

            }
        })

    }

    private fun refreshnewes() {

        val randomnewes = (0..49).random()
        binding.majolakhbar.txtnewez.text = datanewes[randomnewes].first
        binding.majolakhbar.ImgNew.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(datanewes[randomnewes].second))
            startActivity(intent)
        }
        binding.majolakhbar.txtnewez.setOnClickListener {
            refreshnewes()
        }


    }

    private fun lodingCoins() {

        ApiManger.GetCounslist(object : ApiManger.ApiCallback<List<Coins_data.Data>> {
            override fun onSccess(data: List<Coins_data.Data>) {
                ShowDatarecakler(data)
                binding.progressBar.visibility = View.INVISIBLE

            }

            override fun onEror(errormessage: String) {
                Toast.makeText(
                    this@MainActivity,
                    "لطفا اینترنت خود را جک کنید ",
                    Toast.LENGTH_SHORT
                ).show()

            }

        })

    }

    fun ShowDatarecakler(data: List<Coins_data.Data>) {
        val mayadapter = Adapter_market(ArrayList(data), this)
        binding.majolgeymat.recyclerViewmain.adapter = mayadapter
        binding.majolgeymat.recyclerViewmain.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

    override fun Clictitem(data: Coins_data.Data) {
        val intent = Intent(this, MainActivity2::class.java)
        val bundel = Bundle()
        bundel.putParcelable("bundel1", data)
        bundel.putParcelable("bundel2", aboutdataMAp[data.coinInfo.name])
        intent.putExtra("bundel", bundel)



        startActivity(intent)

    }

    override fun onResume() {
        super.onResume()
        lodingdata()
    }


    private fun lodingAbut() {

        val fileString = applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use {
                it.readText(
                )
            }
        aboutdataMAp = mutableMapOf()
        val Gson = Gson()
        val dataaboutall = Gson.fromJson(fileString, Coin_About_data::class.java)
        dataaboutall.forEach {
            aboutdataMAp[it.currencyName] = CoinAboutItem(
                it.info.web,
                it.info.github,
                it.info.twt,
                it.info.desc,
                it.info.reddit
            )
        }
    }


}