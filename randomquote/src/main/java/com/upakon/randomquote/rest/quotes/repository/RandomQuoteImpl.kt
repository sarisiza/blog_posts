package com.upakon.randomquote.rest.quotes.repository

import android.util.Log
import com.upakon.randomquote.rest.quotes.model.Quote
import com.upakon.randomquote.rest.quotes.model.toQuote
import com.upakon.randomquote.rest.quotes.service.QuoteService
import com.upakon.randomquote.utils.EmptyResponse
import com.upakon.randomquote.utils.NullResponseBody
import com.upakon.randomquote.utils.RequestError
import com.upakon.randomquote.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "RandomQuoteImpl"
class RandomQuoteImpl(
    private val quoteService: QuoteService
) : RandomQuote {
    override fun getRandomQuote(): Flow<UiState<Quote>> = flow {
        Log.d(TAG, "getRandomQuote: starting")
        emit(UiState.LOADING)
        try {
            val response = quoteService.getRandomQuote()
            Log.d(TAG, "getRandomQuote: fetched")
            if(response.isSuccessful){
                response.body()?.let {
                    val quotes = it.toQuote()
                    if(quotes.isNotEmpty()){
                        Log.d(TAG, "getRandomQuote: ${quotes[0]}")
                        emit(UiState.SUCCESS(quotes[0]))
                    } else throw EmptyResponse
                } ?: throw NullResponseBody
            } else throw RequestError
        } catch (e: Exception){
            Log.e(TAG, "getRandomQuote: ${e.localizedMessage}", e)
            emit(UiState.ERROR(e))
        }
    }

}