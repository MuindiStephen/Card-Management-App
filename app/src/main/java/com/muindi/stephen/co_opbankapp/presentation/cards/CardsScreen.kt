package com.muindi.stephen.co_opbankapp.presentation.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muindi.stephen.co_opbankapp.data.dto.responses.Card
import com.muindi.stephen.co_opbankapp.presentation.components.CardItem

@Composable
fun CardsListScreen(
    onCardClick: (String) -> Unit,
    viewModel: CardsViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.fetchCards()

        if (uiState.error != null) {
            snackbarHostState.showSnackbar(uiState.error)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {

            when {
                uiState.isLoading -> {
                    LoadingState()
                }

                uiState.error != null -> {
                    ErrorState(
                        message = uiState.error,
                        onRetry = { viewModel.fetchCards() }
                    )
                }

                uiState.cards.isNotEmpty() -> {
                    SuccessState(
                        cards = uiState.cards,
                        onCardClick = onCardClick
                    )
                }

                else -> {
                    EmptyState()
                }
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

