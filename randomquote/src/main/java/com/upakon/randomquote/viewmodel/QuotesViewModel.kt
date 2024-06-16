package com.upakon.randomquote.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upakon.randomquote.rest.images.repository.RandomImage
import com.upakon.randomquote.rest.quotes.model.Quote
import com.upakon.randomquote.rest.quotes.repository.RandomQuote
import com.upakon.randomquote.utils.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

private const val TAG = "QuotesViewModel"
class QuotesViewModel(
    private val randomImage: RandomImage,
    private val randomQuote: RandomQuote,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _quote : MutableLiveData<UiState<Quote>> = MutableLiveData(UiState.LOADING)
    private val _image : MutableLiveData<UiState<Bitmap>> = MutableLiveData(UiState.LOADING)

    val quoteImage : MediatorLiveData<UiState<QuoteImage>> = MediatorLiveData(UiState.LOADING)
    private val _quoteimg = QuoteImage()

    private suspend fun getQuote(){
        randomQuote.getRandomQuote().collect{
            Log.d(TAG, "getQuote: getting quote")
            _quote.postValue(it)
        }
    }

    private suspend fun getImage(category : String){
        randomImage.getRandomImage(category).collect{
            _image.postValue(it)
        }
    }

    /**
     * This method will add an observer for each LiveData
     * Should only be called once
     */
    fun addObservers(){
        quoteImage.addSource(_quote){
            combineQuoteImage(it)
        }
        quoteImage.addSource(_image){
            combineQuoteImage(it)
        }
    }

    //Observing quote and image LiveData to combine them in the MediatorLiveData
    fun getQuoteImage(category : String = "nature"){
        _quoteimg.quote = null
        _quoteimg.image = null
        viewModelScope.launch(dispatcher) {
            //calling both APIs at the same time
            getQuote()
            getImage(category)
        }
    }

    private fun combineQuoteImage(response : UiState<Any>){
        quoteImage.postValue(UiState.LOADING)
        when (response){
            is UiState.ERROR -> {
                quoteImage.postValue(UiState.ERROR(response.error))
            }
            UiState.LOADING -> {
                quoteImage.postValue(UiState.LOADING)
            }
            is UiState.SUCCESS -> {
                if(quoteImage.value !is UiState.ERROR){
                    if(response.response is Quote)
                        _quoteimg.quote = response.response
                    if(response.response is Bitmap)
                        _quoteimg.image = response.response
                    if(_quoteimg.quote != null && _quoteimg.image != null){
                        quoteImage.postValue(UiState.SUCCESS(_quoteimg))
                    }
                }
            }
        }
    }

    //This method avoids memory leaks by removing the observers from both LiveData
    fun removeObservers(owner: LifecycleOwner){
        _quote.removeObservers(owner)
        _image.removeObservers(owner)
    }

}