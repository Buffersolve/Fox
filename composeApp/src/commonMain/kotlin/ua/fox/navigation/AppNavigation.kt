package ua.fox.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val parentNavController = rememberNavController()
    NavHost(
        navController = parentNavController,
        startDestination = Root,
    ) {
        rootGraph(parentNavController)
        homeGraph(parentNavController)
        detailsGraph()
    }
}
