package com.upakon.randomquote.quotes.model

data class Quote(
    val quote : String?,
    val author : String?
)

fun List<QuoteModel>.toQuote() : Quote {
    val quoteMod = this[0]
    return Quote(
        quoteMod.q,
        quoteMod.a
    )
}
