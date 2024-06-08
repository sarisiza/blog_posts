package com.upakon.datastoreexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upakon.datastoreexample.datastore.viewmodel.DataStoreViewModel
import com.upakon.datastoreexample.datastore.viewmodel.UiState
import com.upakon.datastoreexample.ui.screens.HomeScreen
import com.upakon.datastoreexample.ui.screens.RegisterScreen
import com.upakon.datastoreexample.ui.theme.DataStoreExampleTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

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
                    val navController = rememberNavController()
                    val dataViewModel : DataStoreViewModel by viewModel() //koin injection of viewmodel
                    dataViewModel.getUserSettings()
                    DataStoreGraph(
                        viewModel = dataViewModel,
                        navController = navController,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun DataStoreGraph(
    viewModel: DataStoreViewModel,
    navController: NavHostController,
    modifier: Modifier
){
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = "register"
    ){
        composable("register"){
            when(val settings = viewModel.userSettings.collectAsState().value){
                is UiState.ERROR -> {}
                UiState.LOADING -> {}
                is UiState.SUCCESS -> {
                    if(settings.information.firstTimeUser){
                        RegisterScreen(viewModel = viewModel) {
                            navController.navigate("homepage")
                        }
                    } else {
                        navController.navigate("homepage")
                    }
                }
            }
        }
        composable("homepage"){
            HomeScreen(viewModel = viewModel)
        }
    }
}

