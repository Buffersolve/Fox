package ua.fox.navigation.bottom

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ua.fox.navigation.Details
import ua.fox.navigation.Home

sealed class BottomNavItem<T : Any>(val route: T, val icon: ImageVector) {
    data object BottomHome : BottomNavItem<Home>(Home, Icons.Default.Home)
    data object BottomDetails : BottomNavItem<Details>(Details, Icons.Default.Info)
}

@Composable
fun BoxScope.BottomNavigationBar(navController: NavHostController) {
    val items = listOf(BottomNavItem.BottomHome, BottomNavItem.BottomDetails)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 16.dp, start = 64.dp, end = 64.dp)
            .navigationBarsPadding()
            .clip(RoundedCornerShape(50)),
    ) {
        items.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route::class.qualifiedName } == true
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.route::class.simpleName) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route::class.qualifiedName } == true,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
