package com.example.nerkharz.activity.Coin

import CoinAboutItem
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.nerkharz.R
import com.example.nerkharz.activity.ApiManeger.*
import com.example.nerkharz.activity.Model.ChartData
import com.example.nerkharz.activity.Model.Coins_data
import com.example.nerkharz.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    lateinit var data: Coins_data.Data
    lateinit var dataThiscoinAbut: CoinAboutItem
    var ApiManger = ApiManger()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val fromIntent = intent.getBundleExtra("bundel")!!
        data = fromIntent.getParcelable("bundel1")!!


        if (fromIntent.getParcelable<CoinAboutItem>("bundel2")!= null){
            Log.v("testmolaii","nul")
            dataThiscoinAbut = fromIntent.getParcelable<CoinAboutItem>("bundel2")!!
        }else{

            dataThiscoinAbut = CoinAboutItem()
        }

        binding.tolbarMain2.tolbar.title = data.coinInfo.fullName
        initUi()
    }

    private fun initUi() {
        setdatacart()
        setdataStatistics()
        setdataAbout()
    }


    @SuppressLint("SetTextI18n")
    private fun setdataStatistics() {
        binding.layotStatistics.tvOpenAmount.text = data.dISPLAY.uSD.oPEN24HOUR
        binding.layotStatistics.tvTodaysHighAmount.text = data.dISPLAY.uSD.hIGH24HOUR
        binding.layotStatistics.tvTodayLowAmount.text = data.dISPLAY.uSD.lOW24HOUR
        binding.layotStatistics.tvChangeTodayAmount.text = data.dISPLAY.uSD.cHANGE24HOUR
        binding.layotStatistics.tvAlgorithm.text = data.coinInfo.algorithm
        binding.layotStatistics.tvTotalVolume.text = data.dISPLAY.uSD.tOTALVOLUME24H
        binding.layotStatistics.tvAvgMarketCapAmount.text = data.dISPLAY.uSD.mKTCAP
        binding.layotStatistics.tvSupplyNumber.text = data.dISPLAY.uSD.sUPPLY


    }

    private fun setdataAbout() {
        binding.Abut.txtWebsite.text = dataThiscoinAbut.coinWebsite
        binding.Abut.txtGithub.text = dataThiscoinAbut.coinGithub
        binding.Abut.txtReddit.text = dataThiscoinAbut.coinReddit
        binding.Abut.txtTwitter.text = "@" + dataThiscoinAbut.coinTwitter
        binding.Abut.txtAboutCoin.text = dataThiscoinAbut.coinDesc
        binding.Abut.txtWebsite.setOnClickListener {
            openWebsiteDataCoin(dataThiscoinAbut.coinWebsite!!)
        }
        binding.Abut.txtGithub.setOnClickListener {
            openWebsiteDataCoin(dataThiscoinAbut.coinGithub!!)
        }
        binding.Abut.txtReddit.setOnClickListener {
            openWebsiteDataCoin(dataThiscoinAbut.coinReddit!!)
        }
        binding.Abut.txtTwitter.setOnClickListener {
            openWebsiteDataCoin(BASE_URL_TWITTER + dataThiscoinAbut.coinWebsite!!)
        }
    }


    private fun openWebsiteDataCoin(url: String) {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)

    }


    @SuppressLint("SetTextI18n")
    private fun setdatacart() {
        var period: String = HOUR
        requestAndShowChart(period)
        binding.layotChart.radioGroupMain.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_12h -> {
                    period = HOUR
                }
                R.id.radio_1d -> {
                    period = HOURS24
                }
                R.id.radio_1w -> {
                    period = WEEK
                }
                R.id.radio_1m -> {
                    period = MONTH
                }
                R.id.radio_3m -> {
                    period = MONTH3
                }
                R.id.radio_1y -> {
                    period = YEAR
                }
                R.id.radio_all -> {
                    period = ALL
                }
            }
            requestAndShowChart(period)
        }

        binding.layotChart.txtChartPrice.text = data.dISPLAY.uSD.pRICE
        if (data.rAW.uSD.cHANGEPCT24HOUR.toString() == "0.0"){
            binding.layotChart.txtChartChange2.text = data.rAW.uSD.cHANGEPCT24HOUR.toString()
        }else{
            binding.layotChart.txtChartChange2.text = data.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 5) + "%"
        }

        binding.layotChart.txtChartChange1.text = " " +  data.dISPLAY.uSD.cHANGE24HOUR


        val taghir = data.rAW.uSD.cHANGEPCT24HOUR
        if (taghir > 0) {
            binding.layotChart.txtChartChange2.setTextColor(
                ContextCompat.getColor(
                    binding.root.context, R.color.colorGain
                )
            )
            binding.layotChart.txtChartUpdown.setTextColor(
                ContextCompat.getColor(
                    binding.root.context, R.color.colorGain
                )
            )
            binding.layotChart.txtChartUpdown.text = "▲"

            binding.layotChart.sparkviewMain.lineColor = ContextCompat.getColor(
                binding.root.context,
                R.color.colorGain
            )

        } else if (taghir < 0)
        {
            binding.layotChart.txtChartChange2.setTextColor(
                ContextCompat.getColor(
                    binding.root.context, R.color.colorLoss
                )
            )
            binding.layotChart.txtChartUpdown.setTextColor(
                ContextCompat.getColor(
                    binding.root.context, R.color.colorLoss
                )
            )
            binding.layotChart.txtChartUpdown.text = "▼"

            binding.layotChart.sparkviewMain.lineColor = ContextCompat.getColor(
                binding.root.context,
                R.color.colorLoss
            )

        } else {

        }
        binding.layotChart.sparkviewMain.setScrubListener {

            // show price kamel
            if ( it == null ) {
                binding.layotChart.txtChartPrice.text = data.dISPLAY.uSD.pRICE
            } else {
                // show price this dot
                binding.layotChart.txtChartPrice.text = "$ " + (it as ChartData.Data).close.toString()
            }

        }
    }

    private fun requestAndShowChart(period: String) {
        ApiManger.GetChartdata(
            data.coinInfo.name,
            period,
            object : ApiManger.ApiCallback<Pair<List<ChartData.Data>, ChartData.Data?>> {
                override fun onSccess(data: Pair<List<ChartData.Data>, ChartData.Data?>) {
                    Log.v("Test", data.first.toString())
                    val chartAdapter = ChartAdapter(data.first, data.second?.open.toString())
                    binding.layotChart.sparkviewMain.adapter = chartAdapter
                }

                override fun onEror(errormessage: String) {
                    Log.v("texteror", errormessage)
                }

            })
    }

}