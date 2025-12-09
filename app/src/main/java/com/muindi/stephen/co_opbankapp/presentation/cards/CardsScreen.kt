package com.muindi.stephen.co_opbankapp.presentation.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muindi.stephen.co_opbankapp.R
import com.muindi.stephen.co_opbankapp.data.dto.responses.Card
import com.muindi.stephen.co_opbankapp.presentation.components.CardItem

@Composable
fun CardsListScreen(
    onCardClick: (String) -> Unit,
    onClickUserProfileIcon: () -> Unit,
    viewModel: CardsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCards()

        uiState.error?.let {
            snackBarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(22.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                                onClick = { onClickUserProfileIcon() }
                            ),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = "Hi Wanjiku",
                        color = Color.White,
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            when {
                uiState.isLoading -> LoadingState()

                uiState.error != null -> {
                    ErrorState(
                        message = uiState.error!!,
                        onRetry = { viewModel.fetchCards() }
                    )
                }

                uiState.cards.isNotEmpty() -> {
                    SuccessState(
                        cards = uiState.cards,
                        onCardClick = onCardClick
                    )
                }

                else -> EmptyState()
            }
        }
    }
}


@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(color = Color(0xFF004D3D))
            Text(
                text = "Loading your cards...",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "An error occurred",
                fontSize = 24.sp
            )
            Text(
                text = message,
                color = Color.Gray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF004D3D)
                )
            ) {
                Text("Retry")
            }
        }
    }
}


@Composable
fun SuccessState(
    cards: List<Card>,
    onCardClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TextButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "View All Cards",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        items(cards, key = { it.id }) { card ->
            CardItem(
                card = card,
                onClick = { onCardClick(card.id.toString()) }
            )
        }
    }
}


@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "No cards available",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

