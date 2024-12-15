package ua.fox.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import fox.core.generated.resources.Res
import fox.core.generated.resources.error
import fox.core.generated.resources.fox_icon
import fox.core.generated.resources.retry
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ua.fox.core.ui.LocalBarsPaddingValues
import ua.fox.data.model.FoxModel
import ua.fox.home.state.HomeNavEvent
import ua.fox.home.state.HomeUiInteract
import ua.fox.home.state.HomeUiState
import ua.fox.navigation.Viewer

private enum class HomeContentState { Loading, Error, Content }

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val navEvent by viewModel.navEvent.collectAsStateWithLifecycle(null)
    LaunchedEffect(navEvent) {
        when (val event = navEvent?.event) {
            is HomeNavEvent.GoViewerNavEvent -> navController.navigate(Viewer(fox = event.fox))
            null -> Unit
        }
    }
    HomeScreenContent(uiState = uiState, event = viewModel::handleInteract)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(uiState: HomeUiState, event: (HomeUiInteract) -> Unit) {
    val refreshState = rememberPullToRefreshState()
    val gridState = rememberLazyStaggeredGridState()
    val topBrush = Brush.verticalGradient(listOf(MaterialTheme.colorScheme.background, Color.Transparent))
    val bottomBrush = Brush.verticalGradient(listOf(Color.Transparent, MaterialTheme.colorScheme.background))

    val contentState: HomeContentState = when {
        uiState.isLoading -> HomeContentState.Loading
        uiState.error != null -> HomeContentState.Error
        else -> HomeContentState.Content
    }

    PullToRefreshBox(
        isRefreshing = uiState.isRefreshing,
        onRefresh = { event(HomeUiInteract.OnPullToRefresh) },
        state = refreshState,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = uiState.isRefreshing,
                state = refreshState,
                threshold = 100.dp
            )
        },
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(visible = gridState.firstVisibleItemScrollOffset == 0) {
                Image(
                    modifier = Modifier.statusBarsPadding().size(width = 236.dp, height = 200.dp),
                    painter = painterResource(Res.drawable.fox_icon),
                    contentDescription = null,
                )
            }

            Crossfade(targetState = contentState, animationSpec = tween(300)) { state ->
                when (state) {
                    HomeContentState.Loading -> LoadingContent()
                    HomeContentState.Error -> ErrorContent(error = uiState.error, onRetry = { event(HomeUiInteract.OnPullToRefresh) })
                    HomeContentState.Content -> GridContent(gridState = gridState, data = uiState.data, onClick = { event(HomeUiInteract.OnFoxClick(it)) })
                }
            }

        }
        Spacer(modifier = Modifier.fillMaxWidth().height(LocalBarsPaddingValues.current.calculateTopPadding()).background(topBrush))
        Spacer(modifier = Modifier.fillMaxWidth().height(LocalBarsPaddingValues.current.calculateBottomPadding()).background(bottomBrush).align(Alignment.BottomCenter))

    }
}

@Composable
private fun GridContent(
    modifier: Modifier = Modifier,
    gridState: LazyStaggeredGridState,
    data: List<FoxModel>,
    onClick: (FoxModel) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize(),
        state = gridState,
        columns = StaggeredGridCells.Adaptive(120.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = LocalBarsPaddingValues.current.calculateBottomPadding(),
            start = 4.dp,
            end = 4.dp
        )
    ) {
        items(data.size) { index ->
            Item(
                modifier = Modifier.animateItem(fadeInSpec = tween(1000)),
                fox = data[index],
                onClick = { onClick(data[index]) }
            )
        }
    }
}

@Composable
private fun Item(
    modifier: Modifier = Modifier,
    fox: FoxModel,
    onClick: () -> Unit
) {
    Card(modifier = modifier, onClick = onClick) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = fox.image,
            contentDescription = fox.link,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize().padding(bottom = LocalBarsPaddingValues.current.calculateBottomPadding()),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
        )
    }
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier,
    error: String?,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.wrapContentSize().padding(bottom = LocalBarsPaddingValues.current.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = error ?: stringResource(Res.string.error),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            Button(onClick = onRetry) {
                Text(stringResource(Res.string.retry))
            }
        }

    }
}
