package com.upakon.randomquote.viewmodel

import android.graphics.Bitmap
import com.upakon.randomquote.rest.quotes.model.Quote

data class QuoteImage(
    var quote : Quote? = null,
    var image : Bitmap? = null
)
