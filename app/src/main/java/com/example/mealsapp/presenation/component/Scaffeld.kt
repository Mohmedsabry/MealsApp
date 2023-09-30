package com.example.mealsapp.presenation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealsapp.R
import com.example.mealsapp.presenation.NavGraphs
import com.example.mealsapp.presenation.destinations.FavouriteScreenDestination
import com.example.mealsapp.presenation.destinations.HomeScreenDestination
import com.example.mealsapp.presenation.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.utils.findDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBar(
    navigator: DestinationsNavigator,
    title: String = stringResource(id = R.string.home),
    mainContent: @Composable (padding: PaddingValues) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        color = Color(0xFFE41B5F),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    IconButton(onClick = {
                        println("search")
                        navigator.navigate(
                            SearchScreenDestination()
                        )
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                        )
                    }
                }
            },
        )
    }, bottomBar = {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
        ) {
            NavigationBarItem(
                selected = false,
                onClick = {
                    println("home")
                    navigator.navigate(HomeScreenDestination()) {
                        popUpTo(NavGraphs.root.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        tint = if (title == stringResource(id = R.string.home)) Color(0x8DC41D55) else MaterialTheme.colorScheme.onBackground
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.home),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (title == stringResource(id = R.string.home)) Color(0x8DC41D55) else MaterialTheme.colorScheme.onBackground
                    )
                },
                alwaysShowLabel = true,
            )
            NavigationBarItem(selected = false, onClick = {
                println("fav")
                navigator.navigate(FavouriteScreenDestination()) {
                    popUpTo(NavGraphs.root.findDestination("fav")?.route?:"home")
                }
            }, icon = {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (title == stringResource(id = R.string.fav)) Color(0x8DC41D55) else MaterialTheme.colorScheme.onBackground
                )
            }, label = {
                Text(
                    text = stringResource(id = R.string.fav),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (title == stringResource(id = R.string.fav)) Color(0x8DC41D55) else MaterialTheme.colorScheme.onBackground
                )
            }, alwaysShowLabel = true
            )
        }
    }) { paddingValues ->
        mainContent(paddingValues)
    }
}

@Preview
@Composable
fun Prev() {

}