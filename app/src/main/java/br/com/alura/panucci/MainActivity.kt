package br.com.alura.panucci

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.alura.panucci.navigation.*
import br.com.alura.panucci.ui.components.BottomAppBarItem
import br.com.alura.panucci.ui.components.PanucciBottomAppBar
import br.com.alura.panucci.ui.components.bottomAppBarItems
import br.com.alura.panucci.ui.screens.*
import br.com.alura.panucci.ui.theme.PanucciTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PanucciTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PanucciApp()
                }
            }
        }
    }

}

@Composable
fun PanucciApp(navController: NavHostController = rememberNavController()) {
    LaunchedEffect(Unit) {
        navController.addOnDestinationChangedListener { _, _, _ ->
            val routes = navController.backQueue.map {
                it.destination.route
            }
            Log.i("MainActivity", "onCreate: back stack - $routes")
        }
    }
    val backStackEntryState by navController.currentBackStackEntryAsState()
    val orderDoneMessage = backStackEntryState
        ?.savedStateHandle
        ?.getStateFlow<String?>("order_done", null)
        ?.collectAsState()
    Log.i("MainActivity", "onCreate: order done msg ${orderDoneMessage?.value}")
    val currentDestination = backStackEntryState?.destination
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    orderDoneMessage?.value?.let { message ->
        scope.launch {
            snackbarHostState.showSnackbar(message = message)
        }
    }
    val currentRoute = currentDestination?.route
    val selectedItem by remember(currentDestination) {
        val item = when (currentRoute) {
            highlightsListRoute -> BottomAppBarItem.HighlightsList
            menuRoute -> BottomAppBarItem.Menu
            drinksRoute -> BottomAppBarItem.Drinks
            else -> BottomAppBarItem.HighlightsList
        }
        mutableStateOf(item)
    }
    val containsInBottomAppBarItems = when (currentRoute) {
        highlightsListRoute, menuRoute, drinksRoute -> true
        else -> false
    }
    val isShowFab = when (currentDestination?.route) {
        menuRoute,
        drinksRoute -> true

        else -> false
    }
    PanucciApp(
        snackbarHostState = snackbarHostState,
        bottomAppBarItemSelected = selectedItem,
        onBottomAppBarItemSelectedChange = { item ->
            navController.navigateSingleTopWithPopUpTo(item)
        },
        onFabClick = {
            navController.navigateToCheckout()
        },
        isShowTopBar = containsInBottomAppBarItems,
        isShowBottomBar = containsInBottomAppBarItems,
        isShowFab = isShowFab
    ) {
        PanucciNavHost(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanucciApp(
    bottomAppBarItemSelected: BottomAppBarItem = bottomAppBarItems.first(),
    onBottomAppBarItemSelectedChange: (BottomAppBarItem) -> Unit = {},
    onFabClick: () -> Unit = {},
    isShowTopBar: Boolean = false,
    isShowBottomBar: Boolean = false,
    isShowFab: Boolean = false,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    content: @Composable () -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(Modifier.padding(8.dp)
                    .semantics {
                        testTag = "PanucciSnackbar"
                    }) {
                    Text(text = data.visuals.message)
                }
            }
        },
        topBar = {
            if (isShowTopBar) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Ristorante Panucci")
                    },
                    Modifier.semantics {
                        testTag = "PanucciTopAppBar"
                    },
                )
            }
        },
        bottomBar = {
            if (isShowBottomBar) {
                PanucciBottomAppBar(
                    item = bottomAppBarItemSelected,
                    items = bottomAppBarItems,
                    onItemChange = onBottomAppBarItemSelectedChange,
                    modifier = Modifier.semantics {
                        testTag = "PanucciBottomAppBar"
                    }
                )
            }
        },
        floatingActionButton = {
            if (isShowFab) {
                FloatingActionButton(
                    onClick = onFabClick,
                    Modifier.semantics { contentDescription = "Floating Action Button for order" }
                ) {
                    Icon(
                        Icons.Filled.PointOfSale,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun PanucciAppPreview() {
    PanucciTheme {
        Surface {
            PanucciApp(content = {})
        }
    }
}