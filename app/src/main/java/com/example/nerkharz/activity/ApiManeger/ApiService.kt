package com.example.nerkharz.activity.ApiManeger
import com.example.nerkharz.activity.Model.ChartData
import com.example.nerkharz.activity.Model.Coins_data
import com.example.nerkharz.activity.Model.news_data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers(API_KEY)
    @GET("v2/news/")
    fun getNews(
        @Query("SortOrder") SortOrder: String
    ): Call<news_data>

    @Headers(API_KEY)
    @GET("top/totalvolfull")
    fun getTopCoin(
        @Query("tsym") to_symbol: String = "USD",
        @Query("limit") limit_data: Int = 10
    ): Call<Coins_data>


    @Headers(API_KEY)
    @GET("{period}")
    fun getChartdata(
        @Path("period") period :String ,
        @Query("fsym") fromSymbol :String ,
        @Query("limit") limit :Int ,
        @Query("aggregate")  aggregate:Int ,
        @Query("tsym") toSymbol :String = "USD"
    ):Call<ChartData>
}