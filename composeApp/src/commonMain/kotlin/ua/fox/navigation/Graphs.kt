package ua.fox.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ua.fox.RootScreen
import ua.fox.details.ui.DetailsScreen
import ua.fox.home.ui.HomeScreen
import ua.fox.viewer.ui.ViewerScreen

fun NavGraphBuilder.rootGraph(
    parentNavController: NavHostController
) {
    composable<Root> {
        RootScreen(parentNavController)
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    composable<Home> {
        HomeScreen(navController)
    }
    composable<Viewer>(
        typeMap = Viewer.typeMap
    ) {
        ViewerScreen(navController)
    }
}

fun NavGraphBuilder.detailsGraph() {
    composable<Details> { DetailsScreen() }
}