package ua.fox.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun MobileTheme(content: @Composable () -> Unit) {
    val density = LocalDensity.current
    val paddingValues = PaddingValues(
        top = with(density) { WindowInsets.statusBars.getTop(this).toDp() },
        bottom = with(density) { WindowInsets.navigationBars.getBottom(this).toDp() + 120.dp }
    )
    CompositionLocalProvider(LocalBarsPaddingValues provides paddingValues) {
        MaterialTheme(
            colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
            content = content
        )
    }
}