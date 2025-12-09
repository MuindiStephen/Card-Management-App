package com.muindi.stephen.co_opbankapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muindi.stephen.co_opbankapp.presentation.carddetails.CardDetailsScreen
import com.muindi.stephen.co_opbankapp.presentation.cards.CardsListScreen
import com.muindi.stephen.co_opbankapp.presentation.userprofile.UserProfileScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.CardsList
    ) {

        composable(Screen.CardsList) {
            CardsListScreen(
                onCardClick = { cardId ->
                    navController.navigate("${Screen.CardDetails}/$cardId")
                },
                onClickUserProfileIcon = {
                    navController.navigate(Screen.UserProfile)
                }
            )
        }

        composable(
            route = "${Screen.CardDetails}/{cardId}",
            arguments = listOf(navArgument("cardId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val cardId = backStackEntry.arguments?.getString("cardId") ?: ""
            CardDetailsScreen(
                cardId = cardId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.UserProfile) {
            UserProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
