package com.upakon.randomquote.rest.quotes.service

import com.upakon.randomquote.rest.RANDOM_QUOTE
import com.upakon.randomquote.rest.quotes.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

interface QuoteService {

    @GET(RANDOM_QUOTE)
    suspend fun getRandomQuote() : Response<ArrayList<QuoteModel>>

}