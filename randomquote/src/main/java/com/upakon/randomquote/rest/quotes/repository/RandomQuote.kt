package com.upakon.randomquote.rest.quotes.repository

import com.upakon.randomquote.rest.quotes.model.Quote
import com.upakon.randomquote.utils.UiState
import kotlinx.coroutines.flow.Flow

interface RandomQuote {

    fun getRandomQuote() : Flow<UiState<Quote>>

}