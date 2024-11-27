package ua.fox

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import ua.fox.core.ui.MobileTheme
import ua.fox.navigation.bottom.BottomNavItem
import ua.fox.navigation.bottom.BottomNavigationBar
import ua.fox.navigation.detailsGraph
import ua.fox.navigation.homeGraph

@Composable
fun RootScreen(parentNavController: NavHostController) {
    val rootNavController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = rootNavController,
            startDestination = BottomNavItem.BottomHome.route,
        ) {
            homeGraph(parentNavController)
            detailsGraph()
        }
        BottomNavigationBar(rootNavController)
    }
}


@Preview
@Composable
fun RootScreenPreview() {
    MobileTheme {
        RootScreen(rememberNavController())
    }
}