package ua.fox.details.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import ua.fox.core.ui.LocalBarsPaddingValues
import ua.fox.core.ui.MobileTheme

@Composable
fun DetailsScreen() {
    Text(text = "Details Screen", modifier = Modifier.padding(LocalBarsPaddingValues.current))
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    MobileTheme {
        DetailsScreen()
    }
}