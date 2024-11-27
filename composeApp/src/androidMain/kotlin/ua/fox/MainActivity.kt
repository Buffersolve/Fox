package ua.fox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import ua.fox.di.initKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()))
        super.onCreate(savedInstanceState)
        initKoin()
        setContent {
            WindowCompat.getInsetsController(window, LocalView.current).isAppearanceLightStatusBars = !isSystemInDarkTheme()
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}