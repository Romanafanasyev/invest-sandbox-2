package com.example.investsandbox2.activities

import android.content.Intent
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
import com.example.investsandbox2.ui.theme.InvestSandbox2Theme
import androidx.compose.ui.text.input.PasswordVisualTransformation
import android.widget.Toast
import com.example.investsandbox2.network.AuthResponse
import com.example.investsandbox2.network.LoginRequest
import com.example.investsandbox2.network.RegistrationRequest
import com.example.investsandbox2.network.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
 * Activity responsible for user authorization (login/sign-up).
 */
class AuthorizationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set content to AuthorizationScreen composable
        setContent {
            InvestSandbox2Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AuthorizationScreen(
                        isLoginPage = true,
                        onLoginClicked = { username, password ->
                            // Call login API
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    val response = RetrofitClient.apiService.login(LoginRequest(username, password))
                                    // Handle response
                                    response.user_id?.let {
                                        moveToStocksActivity(response.user_id)
                                    } ?: run {
                                        // Handle error
                                        showErrorToast(response.error ?: "Unknown error occurred")
                                    }
                                } catch (e: HttpException) {
                                    // Handling HTTP exception
                                    val responseBody = e.response()?.errorBody()?.string()
                                    val errorResponse = Gson().fromJson(responseBody, AuthResponse::class.java)
                                    errorResponse?.error?.let { showErrorToast(it) } ?: showErrorToast("Unknown error")
                                } catch (e: Exception) {
                                    // Handle other exceptions
                                    showErrorToast("Network error occurred")
                                }
                            }
                        },
                        onSignUpClicked = { username, password ->
                            // Call register API
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    val response = RetrofitClient.apiService.register(
                                        RegistrationRequest(username, password)
                                    )
                                    response.user_id?.let {
                                        moveToStocksActivity(response.user_id)
                                    } ?: run {
                                        showErrorToast(response.error ?: "Unknown error occurred")
                                    }
                                } catch (e: HttpException) {
                                    // Handling HTTP exception
                                    val responseBody = e.response()?.errorBody()?.string()
                                    val errorResponse = Gson().fromJson(responseBody, AuthResponse::class.java)
                                    errorResponse?.error?.let { showErrorToast(it) } ?: showErrorToast("Unknown error")
                                } catch (e: Exception) {
                                    // Handle other exceptions
                                    showErrorToast("Network error occurred")
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    // Display error toast message
    private fun showErrorToast(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    // Move to StockActivity upon successful authentication
    private fun moveToStocksActivity(userId: Int) {
        val intent = Intent(this, StockActivity::class.java).apply {
            putExtra("USER_ID", userId)
        }
        startActivity(intent)
    }
}

/**
 * Composable function representing the authorization screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationScreen(
    isLoginPage: Boolean = true,
    onLoginClicked: (String, String) -> Unit,
    onSignUpClicked: (String, String) -> Unit
) {
    var isLoginPageState by remember { mutableStateOf(isLoginPage) } // State variable

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isLoginPageState) "Login" else "Sign Up",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        var username by remember { mutableStateOf("") }
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        var password by remember { mutableStateOf("") }
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isLoginPageState) {
            Button(onClick = { onLoginClicked(username, password) }) {
                Text(text = "Log in")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { isLoginPageState = false }) {
                Text(text = "Don't have an account yet?")
            }
        } else {
            Button(onClick = { onSignUpClicked(username, password) }) {
                Text(text = "Sign up")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { isLoginPageState = true }) {
                Text(text = "Already have an account?")
            }
        }
    }
}

/**
 * Composable function for previewing the AuthorizationScreen.
 */
@Preview(showBackground = true)
@Composable
fun AuthorizationPreview() {
    InvestSandbox2Theme {
        AuthorizationScreen(
            isLoginPage = true,
            onLoginClicked = { _, _ -> /* Placeholder for click action */ },
            onSignUpClicked = { _, _ -> /* Placeholder for click action */ }
        )
    }
}
