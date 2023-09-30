package com.example.mealsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mealsapp.presenation.NavGraphs
import com.example.mealsapp.ui.theme.MealsAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
             DestinationsNavHost(navGraph = NavGraphs.root)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MealsAppTheme {

    }
}