package br.com.alura.panucci

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import br.com.alura.panucci.navigation.checkoutRoute
import br.com.alura.panucci.navigation.drinksRoute
import br.com.alura.panucci.navigation.highlightsListRoute
import br.com.alura.panucci.navigation.menuRoute
import br.com.alura.panucci.navigation.navigateToCheckout
import br.com.alura.panucci.navigation.navigateToDrinks
import br.com.alura.panucci.navigation.navigateToHighlightsList
import br.com.alura.panucci.navigation.navigateToMenu
import br.com.alura.panucci.navigation.navigateToProductDetails
import br.com.alura.panucci.navigation.productDetailsRoute
import br.com.alura.panucci.navigation.productIdArgument
import br.com.alura.panucci.sampledata.sampleProducts
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
    fun shouldDisplayHighlightsListScreen() {
        composeTestRule.onNodeWithText("Destaques")
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(currentRoute, highlightsListRoute)
    }

    @Test
    fun shouldDisplayDrinksScreen() {
        composeTestRule.onNodeWithText("Bebidas")
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(currentRoute, drinksRoute)
    }

    @Test
    fun shouldDisplayProductDetailsFromHighlightsListScreen() {
        composeTestRule.onNodeWithText("Destaques")
            .performClick()
        composeTestRule.onAllNodesWithContentDescription("highlight product card")
            .onFirst()
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertThat(currentRoute, equalTo("$productDetailsRoute/{$productIdArgument}"))
    }

    @Test
    fun shouldDisplayProductDetailsFromMenuScreen() {
        composeTestRule.onNodeWithText("Menu")
            .performClick()
        composeTestRule.onAllNodesWithContentDescription("menu product card")
            .onFirst()
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertThat(currentRoute, equalTo("$productDetailsRoute/{$productIdArgument}"))
    }

    @Test
    fun shouldDisplayProductDetailsFromDrinksScreen() {
        composeTestRule.onNodeWithText("Bebidas")
            .performClick()
        composeTestRule.onAllNodesWithContentDescription("drinks product card")
            .onFirst()
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertThat(currentRoute, equalTo("$productDetailsRoute/{$productIdArgument}"))
    }

    @Test
    fun shouldDisplayCheckoutScreenFromMenuScreen() {
        composeTestRule.onNodeWithText("Menu")
            .performClick()
        composeTestRule.onNodeWithContentDescription("Floating Action Button for order action")
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertThat(currentRoute, equalTo(checkoutRoute))
    }

    @Test
    fun shouldDisplayCheckoutScreenFromDrinksScreen() {
        composeTestRule.onNodeWithText("Bebidas")
            .performClick()
        composeTestRule.onNodeWithContentDescription("Floating Action Button for order action")
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertThat(currentRoute, equalTo(checkoutRoute))
    }

    @Test
    fun shouldDisplayCheckoutScreenFromProductDetailsScreen() {
        composeTestRule.onNodeWithText("Menu")
            .performClick()
        composeTestRule.onAllNodesWithContentDescription("menu product card")
            .onFirst()
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule
                .onAllNodesWithText("Pedir")
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule
            .onNodeWithText("Pedir")
            .performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertThat(currentRoute, equalTo(checkoutRoute))
    }

    @Test
    fun shouldDisplaySnackBarWhenAnOrderIsDone() {
        composeTestRule.runOnUiThread {
            navController.navigateToCheckout()
        }
        composeTestRule.onNodeWithText("Pedir")
            .performClick()

        composeTestRule.onNodeWithContentDescription("message by snackbar")
            .assertIsDisplayed()
    }

    @Test
    fun shouldDisplayFABOnlyMenuOrDrinksScreen() {
        val fabContentDescription = "Floating Action Button for order action"

        composeTestRule.runOnUiThread {
            navController.navigateToMenu()
        }
        composeTestRule
            .onNodeWithContentDescription(fabContentDescription)
            .assertIsDisplayed()

        composeTestRule.runOnUiThread {
            navController.navigateToDrinks()
        }
        composeTestRule
            .onNodeWithContentDescription(fabContentDescription)
            .assertIsDisplayed()

        composeTestRule.runOnUiThread {
            navController.navigateToHighlightsList()
        }
        composeTestRule
            .onNodeWithContentDescription(fabContentDescription)
            .assertDoesNotExist()

        composeTestRule.runOnUiThread {
            navController.navigateToProductDetails(sampleProducts.first().id)
        }
        composeTestRule
            .onNodeWithContentDescription(fabContentDescription)
            .assertDoesNotExist()

        composeTestRule.runOnUiThread {
            navController.navigateToCheckout()
        }
        composeTestRule
            .onNodeWithContentDescription(fabContentDescription)
            .assertDoesNotExist()
    }

    @Test
    fun shouldDisplayTopAppOnlyMenuOrDrinksScreen() {
        val topAppBarContentDescription = "Top App Bar with Ristorante Panucci"

        composeTestRule.runOnUiThread {
            navController.navigateToMenu()
        }
        composeTestRule
            .onNodeWithContentDescription(topAppBarContentDescription)
            .assertIsDisplayed()

        composeTestRule.runOnUiThread {
            navController.navigateToDrinks()
        }
        composeTestRule
            .onNodeWithContentDescription(topAppBarContentDescription)
            .assertIsDisplayed()

        composeTestRule.runOnUiThread {
            navController.navigateToHighlightsList()
        }
        composeTestRule
            .onNodeWithContentDescription(topAppBarContentDescription)
            .assertIsDisplayed()

        composeTestRule.runOnUiThread {
            navController.navigateToProductDetails(sampleProducts.first().id)
        }
        composeTestRule
            .onNodeWithContentDescription(topAppBarContentDescription)
            .assertDoesNotExist()

        composeTestRule.runOnUiThread {
            navController.navigateToCheckout()
        }
        composeTestRule
            .onNodeWithContentDescription(topAppBarContentDescription)
            .assertDoesNotExist()
    }


}