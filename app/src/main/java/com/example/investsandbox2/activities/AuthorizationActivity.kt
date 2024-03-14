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

class AuthorizationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvestSandbox2Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AuthorizationScreen(
                        isLoginPage = true,
                        onLoginClicked = { username, password ->
                            // Placeholder for API interaction
                            println("Login clicked with username: $username and password: $password")
                            moveToProfile(username)
                        },
                        onSignUpClicked = { username, password ->
                            // Placeholder for API interaction
                            println("Sign up clicked with username: $username and password: $password")
                            moveToProfile(username)
                        }
                    )
                }
            }
        }
    }

    private fun moveToProfile(username: String) {
        val intent = Intent(this, StockActivity::class.java).apply {
            putExtra("USERNAME", username)
        }
        startActivity(intent)
    }
}

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
