package br.com.alura.panucci

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import br.com.alura.panucci.navigation.PanucciNavHost
import br.com.alura.panucci.navigation.drinksRoute
import br.com.alura.panucci.navigation.highlightsListRoute
import br.com.alura.panucci.navigation.menuRoute
import br.com.alura.panucci.navigation.productDetailsRoute
import br.com.alura.panucci.navigation.productIdArgument
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
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
            PanucciApp(navController = navController)
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

    @Test
    fun shouldDisplayHighlightListScreen(){
        composeTestRule.onNodeWithText("Destaques")
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(currentRoute, highlightsListRoute)
    }

    @Test
    fun shouldDisplayDrinksScreen(){
        composeTestRule.onNodeWithText("Bebidas")
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(currentRoute, drinksRoute)
    }

    @Test
    fun shouldDisplayProductDetailsFromHighlightsListScreen(){
        composeTestRule.onNodeWithText("Destaques")
            .performClick()
        composeTestRule.onAllNodesWithContentDescription("highlight product card")
            .onFirst()
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertThat(currentRoute, equalTo("$productDetailsRoute/{$productIdArgument}"))
    }

    @Test
    fun shouldDisplayProductDetailsFromMenuScreen(){
        composeTestRule.onNodeWithText("Menu")
            .performClick()
        composeTestRule.onAllNodesWithContentDescription("menu product card")
            .onFirst()
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertThat(currentRoute, equalTo("$productDetailsRoute/{$productIdArgument}"))
    }

    @Test
    fun shouldDisplayProductDetailsFromDrinksScreen(){
        composeTestRule.onNodeWithText("Bebidas")
            .performClick()
        composeTestRule.onAllNodesWithContentDescription("drinks product card")
            .onFirst()
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertThat(currentRoute, equalTo("$productDetailsRoute/{$productIdArgument}"))
    }

}