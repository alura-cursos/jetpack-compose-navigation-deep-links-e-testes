package br.com.alura.panucci

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import br.com.alura.panucci.navigation.PanucciNavHost
import br.com.alura.panucci.navigation.drinksRoute
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
            PanucciApp(navController = navController)
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule.onRoot().printToLog("panucci app")
        composeTestRule
            .onNodeWithText("Destaques do dia")
            .assertIsDisplayed()
    }

    @Test
    fun appNavHost_verifyIfMenuScreenIsDisplayed(){
        composeTestRule.onRoot().printToLog("panucci app")
        composeTestRule.onNodeWithText("Menu")
            .performClick()

        composeTestRule.onAllNodesWithText("Menu")
            .assertCountEquals(2)

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, menuRoute)
    }

}