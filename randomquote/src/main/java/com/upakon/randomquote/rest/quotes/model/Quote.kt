package com.upakon.randomquote.rest.quotes.model

data class Quote(
    val quote : String?,
    val author : String?
)

fun List<QuoteModel>.toQuote() : List<Quote> =
    map {
        Quote(
            it.q,
            it.a
        )
    }