package br.com.alura.panucci

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import br.com.alura.panucci.navigation.PanucciNavHost
import br.com.alura.panucci.navigation.highlightsListRoute
import br.com.alura.panucci.navigation.menuRoute
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            PanucciNavHost(navController = navController)
        }
    }

    @Test
    fun shouldDisplayTheHighlightsListScreenInStartDestination() {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(currentRoute, highlightsListRoute)
    }

    @Test
    fun shouldDisplayMenuScreen() {
        composeTestRule.onNodeWithText("Menu")
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(currentRoute, menuRoute)
    }

}