package com.example.investsandbox2.activities

import android.os.Bundle
import android.widget.Toast
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
import androidx.lifecycle.lifecycleScope
import com.example.investsandbox2.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StockActivity : ComponentActivity() {
    private var buyMode by mutableStateOf(false)
    private var userId: Int = 0
    private var username by mutableStateOf("")
    private var balance by mutableStateOf(0.0)
    private var stocks by mutableStateOf(emptyList<Stock>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userId = intent.getIntExtra("USER_ID", 0)

        fetchUserInfo()

        setContent {
            InvestSandbox2Theme {
                StockScreen(
                    username = username,
                    balance = balance,
                    stocks = stocks,
                    buyMode = buyMode,
                    onToggleMode = {
                        buyMode = !buyMode
                        if (buyMode) {
                            fetchAllStocks()
                        } else {
                            fetchUserStocks()
                        }
                    }
                )
            }
        }
    }

    private fun fetchUserInfo() {
        lifecycleScope.launch {
            try {
                val userInfoResponse = RetrofitClient.apiService.getUserInfo(userId)
                userInfoResponse.error?.let {
                    return@launch
                }
                username = userInfoResponse.username ?: ""
                balance = userInfoResponse.balance ?: 0.0
                if (buyMode) {
                    fetchAllStocks()
                } else {
                    fetchUserStocks()
                }
            } catch (e: Exception) {
                showErrorToast("Error during fetching user info")
            }
        }
    }

    private fun fetchUserStocks() {
        lifecycleScope.launch {
            try {
                val userStocksResponse = RetrofitClient.apiService.getUserStocks(userId)
                userStocksResponse.forEach {
                    it.error?.let {
                        return@forEach
                    }
                }
                stocks = userStocksResponse.map { stockResponse ->
                    Stock(
                        id = stockResponse.stockId ?: 0,
                        name = stockResponse.name ?: "",
                        price = stockResponse.price ?: 0.0,
                        quantity = stockResponse.boughtQuantity ?: 0
                    )
                }
            } catch (e: Exception) {
                showErrorToast("Error during fetching stocks")
            }
        }
    }

    private fun fetchAllStocks() {
        lifecycleScope.launch {
            try {
                val allStocksResponse = RetrofitClient.apiService.getAllStocks()
                allStocksResponse.forEach {
                    it.error?.let {
                        return@forEach
                    }
                }
                stocks = allStocksResponse.map { stockResponse ->
                    Stock(
                        id = stockResponse.id ?: 0,
                        name = stockResponse.name ?: "",
                        price = stockResponse.price ?: 0.0,
                        quantity = stockResponse.quantity ?: 0
                    )
                }
            } catch (e: Exception) {
                showErrorToast("Error during fetching stocks")
            }
        }
    }

    private fun showErrorToast(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(
    username: String,
    balance: Double,
    stocks: List<Stock>,
    buyMode: Boolean,
    onToggleMode: () -> Unit
) {
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
            Button(
                onClick = onToggleMode,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text(text = if (buyMode) "My Stocks" else "Browse Stocks")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = if (buyMode) "Stock Market"
                else "Owned Stocks",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Stocks
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (stocks.isEmpty()) {
                item {
                    Text(
                        text = if (buyMode) "No stocks available for buying"
                        else "No stocks owned yet? Buy some on stock exchange",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(stocks) { stock ->
                    StockItem(stock = stock, buyMode = buyMode)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockItem(stock: Stock, buyMode: Boolean) {
    var showDialog by remember { mutableStateOf(false) }
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
                onClick = { showDialog = true },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .widthIn(min = ButtonDefaults.MinWidth)
            ) {
                Text(text = if (buyMode) "Buy" else "Sell")
            }

        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = if (buyMode) "Buy Stocks" else "Sell Stocks")
            },
            text = {
                Column {
                    Text(
                        text = if (buyMode) "Number of stocks to buy:" else "Number of stocks to sell:",
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
                        text = if (buyMode) "Total Price: ${numberOfStocks * stock.price}$"
                        else "Total Price: ${numberOfStocks * stock.price}$",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Placeholder for API request
                        if (buyMode) {
                            println("Buying $numberOfStocks stocks of ${stock.name}")
                        } else {
                            println("Selling $numberOfStocks stocks of ${stock.name}")
                        }
                        showDialog = false
                    }
                ) {
                    Text(text = if (buyMode) "Buy Stock" else "Sell Stock")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StockPreview() {
    InvestSandbox2Theme {
        val username = "JohnDoe"
        val balance = 1000.0
        val stocks = listOf(
            Stock(1, "Company A", 50.0, 10),
            Stock(2, "Company B", 75.0, 5),
            Stock(3, "Company C", 100.0, 8)
        )
        var buyMode = false
        StockScreen(username = username, balance = balance, stocks = stocks, buyMode = buyMode){
            buyMode = !buyMode
        }
    }
}
