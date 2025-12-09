package com.muindi.stephen.co_opbankapp.presentation.carddetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muindi.stephen.co_opbankapp.data.dto.responses.Card
import com.muindi.stephen.co_opbankapp.data.dto.responses.Transaction
import com.muindi.stephen.co_opbankapp.presentation.components.CardItem
import java.text.NumberFormat
import java.util.*

@Composable
fun CardDetailsScreen(
    cardId: String,
    onBackClick: () -> Unit,
    viewModel: CardDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(cardId) {
        viewModel.loadCardDetails(cardId)
        viewModel.loadTransactions(cardId)
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> LoadingScreen()

                uiState.error != null -> ErrorScreen(
                    message = uiState.error.orEmpty(),
                    onRetry = {
                        viewModel.loadCardDetails(cardId)
                        viewModel.loadTransactions(cardId)
                    }
                )

                uiState.card != null -> DetailsContent(
                    card = uiState.card!!,
                    transactions = uiState.transactions,
                    isBalanceVisible = uiState.isBalanceVisible,
                    onToggleVisibility = { viewModel.toggleBalanceVisibility() },
                    userName = uiState.userName,
                    onBackClick = onBackClick
                )
            }
        }
    }
}


@Composable
private fun DetailsContent(
    card: Card,
    transactions: List<Transaction>,
    isBalanceVisible: Boolean,
    onToggleVisibility: () -> Unit,
    userName: String,
    onBackClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {

        item {
            HeaderSection(
                userName = userName,
                onBackClick = onBackClick
            )
        }

        item {
            CardBalanceSection(
                balance = card.balance ?: 0.0,
                currency = card.currency ?: "KES",
                isVisible = isBalanceVisible,
                onToggleVisibility = onToggleVisibility
            )
        }

        item { Spacer(Modifier.height(12.dp)) }

        item {
            CardItem(
                card = card,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

        item { Spacer(Modifier.height(24.dp)) }

        item {
            Text(
                text = "Recent Transactions",
                color = Color(0xFF006B54),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

        items(transactions) { transaction ->
            TransactionItem(transaction)
        }
    }
}

@Composable
private fun HeaderSection(
    userName: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF006B54))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Hi Wanjiku",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }
}


@Composable
private fun CardBalanceSection(
    balance: Double,
    currency: String,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Card Balance",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val formatted = NumberFormat.getNumberInstance(Locale.US).format(balance)

            Text(
                text = if (isVisible) "$currency $formatted" else "$currency ••••••",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = onToggleVisibility) {
                Icon(
                    imageVector = if (isVisible) Icons.Default.Visibility else Icons.Outlined.VisibilityOff,
                    contentDescription = "Toggle balance"
                )
            }
        }
    }
}


@Composable
private fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF006B54)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = transaction.merchant.take(1).uppercase(),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column {
                Text(transaction.merchant, fontSize = 16.sp)
                Text(transaction.date, fontSize = 12.sp, color = Color.Gray)
            }
        }

        Text(
            text = NumberFormat.getCurrencyInstance().format(transaction.amount),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Something went wrong", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(message, color = Color.Gray)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) { Text("Retry") }
    }
}



