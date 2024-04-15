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
//                                            vi
//                                coroutineScope.launch {
//                                    searchChannel.cancel()
//                                    delay(300)
//                                    searchChannel.send(it)
//                                }
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


//sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
//    data object First : Screen("first", "First", Icons.Default.Home)
//    data object Second : Screen("second", "Second", Icons.Default.Search)
//    data object Third : Screen("third", "Third", Icons.Default.AccountCircle)
//}
//
//@Composable
//fun BottomNavigationApp() {
//    val navController = rememberNavController()
//    val searchText = remember { mutableStateOf("") }
//    val coroutineScope = rememberCoroutineScope()
//    val searchChannel = remember { Channel<String>() }
//    val isSearchExpanded = remember { mutableStateOf(false) }
//
//    LaunchedEffect(searchText.value) {
//        // Send the search query to the Channel after a delay
//        searchChannel.send(searchText.value)
//    }
//
//    LaunchedEffect(searchChannel) {
//        for (searchQuery in searchChannel) {
//            Log.d("searchQuery", searchQuery)
//            // Call the search method of the ViewModel with the debounced/throttled search query
////            viewModel.search(searchQuery)
//        }
//    }

//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    if (!isSearchExpanded.value) {
//                        Text(text = "App Title")
//                    }
//                },
//                actions = {
//                    if (isSearchExpanded.value) {
//                        TextField(
//                            value = searchText.value,
//                            onValueChange = {
//                                searchText.value = it
//                                coroutineScope.launch {
//                                    searchChannel.cancel()
//                                    delay(300)
//                                    searchChannel.send(it)
//                                }
//                            },
//                            placeholder = { Text("Search") },
//                            textStyle = TextStyle(color = Color.Black),
//                            modifier = Modifier
//                                .padding(top = 3.dp, bottom = 3.dp, end = 16.dp)
//                                .background(Color.White, shape = RoundedCornerShape(8.dp))
//                                .clip(RoundedCornerShape(8.dp))
//                        )
//
//                        IconButton(onClick = {
//                            isSearchExpanded.value = false
//                            searchText.value = ""  // Clear search text when closing
//                        }) {
//                            Icon(Icons.Filled.Close, contentDescription = "Close")
//                        }
//                    } else {
//                        IconButton(onClick = { isSearchExpanded.value = true }) {
//                            Icon(Icons.Filled.Search, contentDescription = "Search")
//                        }
//                    }
//                }
//            )
//        },
//        bottomBar = {
//            BottomNavigation {
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentRoute = navBackStackEntry?.destination?.route
//
//                listOf(Screen.First, Screen.Second, Screen.Third).forEach { screen ->
//                    BottomNavigationItem(
//                        icon = { Icon(screen.icon, contentDescription = null) },
//                        label = { Text(screen.title) },
//                        selected = currentRoute == screen.route,
//                        onClick = {
//                            navController.navigate(screen.route) {
//                                popUpTo(navController.graph.startDestinationId)
//                                launchSingleTop = true
//                            }
//                        }
//                    )
//                }
//            }
//        }
//    ) { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = Screen.First.route,
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            composable(Screen.First.route) { FirstScreen() }
//            composable(Screen.Second.route) { SecondScreen() }
//            composable(Screen.Third.route) { ThirdScreen() }
//        }
//    }
//}
//
//
//@Composable
//fun FirstScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = "First Screen", style = MaterialTheme.typography.h4)
//    }
//}
//
//@Composable
//fun SecondScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = "Second Screen", style = MaterialTheme.typography.h4)
//    }
//}
//
//@Composable
//fun ThirdScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = "Third Screen", style = MaterialTheme.typography.h4)
//    }
//}
//
//
////@Composable
////fun ItemRow(article: Article) {
////    // Your UI layout for each item
////    // For simplicity, just displaying the item name here
////    Text(text = article.title)
////}
////
////@Composable
////fun ArticlesList(viewModel: HomeViewModel) {
////    val itemsState = viewModel.articlesListLiveData.observeAsState(listOf())
////    val items = itemsState.value
////
////    LazyColumn (contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)){
////        items(items = items) { item ->
////            ItemRow(article = item)
////        }
////    }
////}
