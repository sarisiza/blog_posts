package com.upakon.randomquote.quotes.model


import com.google.gson.annotations.SerializedName

data class QuoteModel(
    @SerializedName("a")
    val a: String?,
    @SerializedName("h")
    val h: String?,
    @SerializedName("q")
    val q: String?
)