package com.upakon.randomquote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import coil.Coil
import coil.compose.rememberAsyncImagePainter
import com.upakon.randomquote.rest.quotes.repository.RandomQuote
import com.upakon.randomquote.ui.theme.DataStoreExampleTheme
import com.upakon.randomquote.utils.UiState
import com.upakon.randomquote.viewmodel.QuotesViewModel
import org.koin.androidx.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel : QuotesViewModel by viewModel()
                    viewModel.addObservers()
                    RandomQuote(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun RandomQuote(
    viewModel: QuotesViewModel
) {
    Column {
        val quoteState = viewModel.quoteImage.observeAsState().value
        var clicked by remember {
            mutableStateOf(false)
        }
        Button(
            onClick = {
                clicked = true
                viewModel.getQuoteImage()
            }
        ) {
            Text(text = "Create Quote")
        }
        when(quoteState){
            is UiState.ERROR -> {
                TODO()
            }
            UiState.LOADING -> {
                if (clicked){
                    CircularProgressIndicator()
                }
            }
            is UiState.SUCCESS -> {
                quoteState.response.image?.let {
                    Image(
                        painter= rememberAsyncImagePainter(it),
                        contentDescription = "random image"
                    )
                }
                quoteState.response.quote?.let {
                    Text(text = it.quote ?: "Unkown quote")
                    Text(text = "by ${it.author}")
                }
            }
            null -> TODO()
        }
    }
}

