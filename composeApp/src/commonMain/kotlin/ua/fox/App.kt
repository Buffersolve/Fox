package ua.fox

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import ua.fox.core.ui.MobileTheme
import ua.fox.navigation.AppNavigation

@Composable
@Preview
fun App() {
    MobileTheme {
        AppNavigation()
    }
}