package com.example.nerkharz.activity.ApiManeger
import com.example.nerkharz.activity.Model.ChartData
import com.example.nerkharz.activity.Model.Coins_data
import com.example.nerkharz.activity.Model.news_data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManger {
    private val apiservice: ApiService

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiservice = retrofit.create(ApiService::class.java)
    }

    fun getnewez(apiCallback: ApiCallback<ArrayList<Pair<String, String>>>) {
        apiservice.getNews("popular").enqueue(object : Callback<news_data> {
            override fun onResponse(call: Call<news_data>, response: Response<news_data>) {
                val data = response.body()!!
                val datatosend: ArrayList<Pair<String, String>> = arrayListOf()
                data.data.forEach {
                    datatosend.add(Pair(it.title, it.url))
                }
                apiCallback.onSccess(datatosend)
            }

            override fun onFailure(call: Call<news_data>, t: Throwable) {
                apiCallback.onEror(t.message!!)
            }
        })

    }

    fun GetCounslist(apicoincalbak: ApiCallback<List<Coins_data.Data>>) {
        apiservice.getTopCoin().enqueue(object : Callback<Coins_data> {
            override fun onResponse(call: Call<Coins_data>, response: Response<Coins_data>) {
                val dataCoin = response.body()!!
                apicoincalbak.onSccess(dataCoin.data)

            }

            override fun onFailure(call: Call<Coins_data>, t: Throwable) {
                apicoincalbak.onEror(t.message!!)
            }
        })
    }

    fun GetChartdata(
        symbol: String,
        period: String,
        apicallbak: ApiCallback<Pair<List<ChartData.Data>, ChartData.Data?>>
    ) {

        var histoPeriod = ""
        var limit = 30
        var aggregate = 1

        when (period) {

            HOUR -> {
                histoPeriod = HISTO_MINUTE
                limit = 60
                aggregate = 12
            }

            HOURS24 -> {
                histoPeriod = HISTO_HOUR
                limit = 24
            }

            MONTH -> {
                histoPeriod = HISTO_DAY
                limit = 30
            }

            MONTH3 -> {
                histoPeriod = HISTO_DAY
                limit = 90
            }

            WEEK -> {
                histoPeriod = HISTO_HOUR
                aggregate = 6
            }

            YEAR -> {
                histoPeriod = HISTO_DAY
                aggregate = 13
            }

            ALL -> {
                histoPeriod = HISTO_DAY
                aggregate = 30
                limit = 2000
            }

        }


        apiservice.getChartdata(histoPeriod, symbol, limit, aggregate)
            .enqueue(object : Callback<ChartData> {
                override fun onResponse(call: Call<ChartData>, response: Response<ChartData>) {
                    val Datafull = response.body()!!
                    val data1 = Datafull.data
                    val data2 = Datafull.data.maxByOrNull { it.close.toFloat() }
                    val retunData = Pair(data1, data2)
                    apicallbak.onSccess(retunData)


                }

                override fun onFailure(call: Call<ChartData>, t: Throwable) {
                    apicallbak.onEror(t.message!!)

                }

            })


    }
    interface ApiCallback<T> {
        fun onSccess(data: T)
        fun onEror(errormessage: String)
    }
}




