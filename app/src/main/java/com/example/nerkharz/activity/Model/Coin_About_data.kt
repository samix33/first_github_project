package com.example.nerkharz.activity.Model


import com.google.gson.annotations.SerializedName

class Coin_About_data : ArrayList<Coin_About_data.Coin_About_dataItem>(){
    data class Coin_About_dataItem(
        @SerializedName("currencyName")
        val currencyName: String,
        @SerializedName("info")
        val info: Info
    ) {
        data class Info(
            @SerializedName("desc")
            val desc: String,
            @SerializedName("forum")
            val forum: String,
            @SerializedName("github")
            val github: String,
            @SerializedName("reddit")
            val reddit: String,
            @SerializedName("twt")
            val twt: String,
            @SerializedName("web")
            val web: String
        )
    }
}