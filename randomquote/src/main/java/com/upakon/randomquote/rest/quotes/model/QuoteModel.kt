package com.upakon.randomquote.rest.quotes.model


import com.google.gson.annotations.SerializedName

data class QuoteModel(
    @SerializedName("a")
    val a: String?,
    @SerializedName("h")
    val h: String?,
    @SerializedName("q")
    val q: String?
)