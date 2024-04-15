package com.minaroid.newsapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.minaroid.newsapp.presentation.navigation.Navigation
import com.minaroid.newsapp.presentation.navigation.NavigationItem
import com.minaroid.newsapp.presentation.viewmodel.NewsViewModel
import com.minaroid.newsapp.ui.theme.NewsAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                val viewModel: NewsViewModel = hiltViewModel()

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route ?: ""

                val searchText = remember { mutableStateOf("") }
                val isSearchExpanded = remember { mutableStateOf(false) }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                if (!isSearchExpanded.value) {
                                    Text(
                                        text = getDestinationLabel(navBackStackEntry), maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            },
                            navigationIcon = {
                                if (currentDestination != NavigationItem.HomeScreenNavigationItem.route) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                                    }
                                }
                            },
                            actions = {
                                if (isSearchExpanded.value) {
                                    TextField(
                                        value = searchText.value,
                                        onValueChange = {
                                            searchText.value = it
                                            viewModel.fetchArticles(it)
                                        },
                                        placeholder = { Text("Search") },
                                        textStyle = TextStyle(color = Color.Black),
                                        colors = TextFieldDefaults.textFieldColors(
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent
                                        ),
                                        modifier = Modifier
                                            .padding(top = 6.dp, bottom = 6.dp, end = 16.dp)
                                            .background(
                                                Color.White,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .clip(RoundedCornerShape(8.dp))
                                    )

                                    IconButton(onClick = {
                                        isSearchExpanded.value = false
                                        searchText.value = ""  // Clear search text when closing
                                    }) {
                                        Icon(Icons.Filled.Close, contentDescription = "Close")
                                    }
                                } else {
                                    IconButton(onClick = { isSearchExpanded.value = true }) {
                                        Icon(Icons.Filled.Search, contentDescription = "Search")
                                    }
                                }
                            }
                        )
                    },
                    content = { padding ->
                        Surface(
                            modifier = Modifier.padding(padding),
                            color = androidx.compose.material3.MaterialTheme.colorScheme.background
                        ) {
                            MyApp {
                                Navigation(navController)
                            }

                        }

                    },
                )

            }
        }
    }

    @Composable
    fun MyApp(content: @Composable () -> Unit) {
        content()
    }

    private fun getDestinationLabel(navBackStackEntry: NavBackStackEntry?): String {
        val currentDestination = navBackStackEntry?.destination?.route ?: ""
        return when (currentDestination) {
            NavigationItem.HomeScreenNavigationItem.route -> NavigationItem.HomeScreenNavigationItem.label
            NavigationItem.DetailsScreenNavigationItem.route -> navBackStackEntry?.arguments?.getString(
                "title"
            ) ?: NavigationItem.DetailsScreenNavigationItem.label

            else -> ""
        }
    }
}

