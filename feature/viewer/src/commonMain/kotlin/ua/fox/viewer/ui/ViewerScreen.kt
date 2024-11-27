package ua.fox.viewer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import ua.fox.core.ui.LocalBarsPaddingValues
import ua.fox.core.ui.MobileTheme
import ua.fox.viewer.state.ViewerNavEvent
import ua.fox.viewer.state.ViewerUiInteract
import ua.fox.viewer.state.ViewerUiState

@Composable
fun ViewerScreen(navController: NavController) {
    val viewModel = koinViewModel<ViewerViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navEvent by viewModel.navEvent.collectAsStateWithLifecycle(null)
    LaunchedEffect(navEvent) {
        when (navEvent?.event) {
            ViewerNavEvent.GoBackNavEvent -> navController.navigateUp()
            null -> Unit
        }
    }
    ViewerScreenContent(state, viewModel::handleInteract)
}

@Composable
private fun ViewerScreenContent(state: ViewerUiState, interact: (ViewerUiInteract) -> Unit) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var scale by remember { mutableStateOf(1f) }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = state.fox.image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale *= zoom
                        offset += pan
                    }
                }
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
        )
        IconButton(
            onClick = { interact(ViewerUiInteract.OnBack) },
            modifier = Modifier.align(Alignment.TopStart).padding(top = LocalBarsPaddingValues.current.calculateTopPadding())
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
private fun ViewerScreenPreview() {
    MobileTheme {
//        ViewerScreenContent()
    }
}