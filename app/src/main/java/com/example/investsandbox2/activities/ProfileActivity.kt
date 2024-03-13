package com.example.investsandbox2.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.investsandbox2.models.Stock
import com.example.investsandbox2.ui.theme.InvestSandbox2Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvestSandbox2Theme {
                // Placeholder data
                val username = "John Doe"
                val balance = 1000.0
                val stocks = listOf(
                    Stock("Company A", 50.0, 10),
                    Stock("Company B", 75.0, 5),
                    Stock("Company B", 75.0, 5),
                    Stock("Company B", 75.0, 5),
                    Stock("Company B", 75.0, 5),
                    Stock("Company B", 75.0, 5),
                    Stock("Company B", 75.0, 5),
                    Stock("Company B", 75.0, 5),
                    Stock("Company B", 75.0, 5),
                    Stock("Company C", 100.0, 8)
                )
                ProfileScreen(username, balance, stocks)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(username: String, balance: Double, stocks: List<Stock>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(
                    text = username,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Balance: $balance$",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Stocks
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (stocks.isEmpty()) {
                item {
                    Text(
                        text = "No stocks yet? Buy some on stock exchange",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(stocks) { stock ->
                    StockItem(stock = stock)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockItem(stock: Stock) {
    var showSellDialog by remember { mutableStateOf(false) }
    var numberOfStocks by remember { mutableStateOf(0) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.8f)
            ) {
                Text(
                    text = stock.name,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Price: ${stock.price}$",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Quantity: ${stock.quantity}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Button(
                onClick = { showSellDialog = true },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .widthIn(min = ButtonDefaults.MinWidth)
            ) {
                Text(text = "Sell")
            }

        }
    }

    if (showSellDialog) {
        AlertDialog(
            onDismissRequest = { showSellDialog = false },
            title = {
                Text(text = "Sell Stocks")
            },
            text = {
                Column {
                    Text(
                        text = "Number of stocks to sell:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    TextField(
                        value = numberOfStocks.toString(),
                        onValueChange = {
                            numberOfStocks = it.toIntOrNull() ?: 0
                        },
                        label = { Text(text = "Number of stocks") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Total Price: ${numberOfStocks * stock.price}$",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Placeholder for API request
                        println("Selling ${numberOfStocks} stocks of ${stock.name}")
                        showSellDialog = false
                    }
                ) {
                    Text(text = "Sell Stock")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showSellDialog = false }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    InvestSandbox2Theme {
        val username = "JohnDoe"
        val balance = 1000.0
        val stocks = listOf(
            Stock("Company A", 50.0, 10),
            Stock("Company B", 75.0, 5),
            Stock("Company C", 100.0, 8)
        )
        ProfileScreen(username = username, balance = balance, stocks = stocks)
    }
}

