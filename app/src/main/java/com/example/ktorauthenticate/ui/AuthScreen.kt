package com.example.ktorauthenticate.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ktorauthenticate.auth.AuthResult
import com.example.ktorauthenticate.ui.destinations.AuthScreenDestination
import com.example.ktorauthenticate.ui.destinations.HomeScreenDestination
import com.example.ktorauthenticate.ui.destinations.SignUpScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(start = true)
fun AuthScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    var showError by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result) {
                is AuthResult.Authorized<*> -> {
                    Toast.makeText(context, "Authorized", Toast.LENGTH_LONG).show()
                    navigator.navigate(HomeScreenDestination) {
                        popUpTo(AuthScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }

                is AuthResult.Unauthorized<*> -> {
                    Toast.makeText(context, "Unauthorized", Toast.LENGTH_LONG).show()
                }

                is AuthResult.UnknownError<*> -> {
                    Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign In",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 256.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.signInUsername,
            onValueChange = { viewModel.onEvent(AuthUiEvent.SignInUsernameChanged(it)) },
            label = { Text("Email or Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFF6F61),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFFFF6F61),
                unfocusedLabelColor = Color.Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        OutlinedTextField(
            value = state.signInPassword,
            onValueChange = { viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFF6F61),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFFFF6F61),
                unfocusedLabelColor = Color.Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        if (showError) {
            Text(
                text = "Invalid username or password",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Button(
            onClick = {
                viewModel.onEvent(AuthUiEvent.SignIn)
                showError = state.signInUsername.isBlank() || state.signInPassword.isBlank()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(8.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Sign In",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "New user? Sign up",
            color = Color(0xFFFF6F61),
            modifier = Modifier.clickable { navigator.navigate(SignUpScreenDestination) }
                .padding(bottom = 16.dp)
        )
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
