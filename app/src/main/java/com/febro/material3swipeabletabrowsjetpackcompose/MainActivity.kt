package com.febro.material3swipeabletabrowsjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.febro.material3swipeabletabrowsjetpackcompose.ui.theme.Material3SwipeableTabRowsJetpackComposeTheme


// ref: https://www.youtube.com/watch?v=9r4st6dmyNE
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tabItems = listOf(
            TabItem(
                title = "Home",
                unselectedIcon = Icons.Outlined.Home,
                selectedIcon = Icons.Filled.Home,
            ),
            TabItem(
                title = "Browse",
                unselectedIcon = Icons.Outlined.ShoppingCart,
                selectedIcon = Icons.Filled.ShoppingCart,
            ),
            TabItem(
                title = "Account",
                unselectedIcon = Icons.Outlined.AccountCircle,
                selectedIcon = Icons.Filled.AccountCircle,
            )
        )

        setContent {
            Material3SwipeableTabRowsJetpackComposeTheme(
                darkTheme = false
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {

                    // mutableIntStateOf not found error fix: https://stackoverflow.com/a/64951786/10010404
                    var selectedTabIndex by remember {
                        mutableIntStateOf(0)
                    }
                    
                    val pagerState = rememberPagerState {
                        tabItems.size
                    }

                    // runs everytime selectedTabIndex changes
                    LaunchedEffect(selectedTabIndex) {
                        pagerState.animateScrollToPage(selectedTabIndex)
                    }

                    // runs everytime pager changes
                    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                        // condition fix issue when animation plays from left to right, curr page is the middle: https://youtu.be/9r4st6dmyNE?t=770s
                        if(!pagerState.isScrollInProgress) selectedTabIndex = pagerState.currentPage
                    }

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        // If many tabs (+3), use ScrallableRow
                        TabRow(selectedTabIndex = selectedTabIndex) {
                            tabItems.forEachIndexed { index, tabItem ->
                                Tab(
                                    selected = index == selectedTabIndex,
                                    onClick = {
                                        selectedTabIndex = index
                                    },
                                    text = {
                                        Text(text = tabItem.title)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedTabIndex)
                                                tabItem.selectedIcon
                                            else tabItem.unselectedIcon,
                                            contentDescription = tabItem.title
                                        )
                                    }
                                )
                            }
                        }
                        
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) { index ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    // BUTTONS ACCODING TO MATERIAL 3 GUIDELINES
                                    Text(text = tabItems[index].title)
                                    Spacer(modifier = Modifier.padding(15.dp))
                                    Button(onClick = { /*TODO*/ }) {
                                        Text(text = "Create new Account")
                                    }

                                    // a second option to the main cta
                                    OutlinedButton(onClick = { /*TODO*/ }) {
                                        Text(text = "I have an existing account")
                                    }

                                    // e.g cta for a picture that requires button to be highlighted
                                    ElevatedButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Add,
                                            contentDescription = "Add to cart",
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = "Add to cart")
                                    }
                                    
                                    FilledTonalButton(onClick = { /*TODO*/ }) {
                                        Text(text = "Open in Browser")
                                    }
                                    
                                    OutlinedButton(onClick = { /*TODO*/ }) {
                                        Text(text = "Back")
                                    }
                                    
                                    // lest prominent
                                    TextButton(onClick = { /*TODO*/ }) {
                                        Text(text = "Learn more")
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        // super.onBackPressed()
    }
}

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)