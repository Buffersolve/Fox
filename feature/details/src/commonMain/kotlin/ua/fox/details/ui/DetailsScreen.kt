package ua.fox.details.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fox.core.generated.resources.Res
import fox.core.generated.resources.dev_by
import fox.core.generated.resources.fox_app
import fox.core.generated.resources.fox_icon
import fox.core.generated.resources.theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ua.fox.core.ui.MobileTheme

@Composable
fun DetailsScreen() {
    DetailsScreenContent()
}

@Composable
private fun DetailsScreenContent() {
    Scaffold(containerColor = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = it.calculateTopPadding()),
                style = MaterialTheme.typography.headlineLarge,
                text = stringResource(Res.string.fox_app)
            )
            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(Res.drawable.fox_icon),
                contentDescription = null
            )
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(Res.string.theme),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.align(Alignment.End),
                text = stringResource(Res.string.dev_by)
            )
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    MobileTheme {
        DetailsScreenContent()
    }
}