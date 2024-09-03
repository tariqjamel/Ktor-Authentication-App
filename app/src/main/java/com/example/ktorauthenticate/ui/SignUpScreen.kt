package com.example.ktorauthenticate.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ktorauthenticate.R
import com.example.ktorauthenticate.auth.AuthResult
import com.example.ktorauthenticate.ui.destinations.AuthScreenDestination
import com.example.ktorauthenticate.ui.destinations.HomeScreenDestination
import com.example.ktorauthenticate.ui.destinations.SignUpScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun SignUpScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var profilePicture by remember { mutableStateOf<Painter?>(null) }
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result) {
                is AuthResult.Authorized<*> -> {
                    Toast.makeText(context, "Sign up successful", Toast.LENGTH_LONG).show()
                    navigator.navigate(HomeScreenDestination) {
                        popUpTo(SignUpScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }

                is AuthResult.Unauthorized<*> -> {
                    Toast.makeText(context, "Sign up failed", Toast.LENGTH_LONG).show()
                }

                is AuthResult.UnknownError<*> -> {
                    Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign Up",
            fontSize = 24.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(top = 116.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .clickable {
                    // Trigger image picker here
                 //   profilePicture = painterResource(id = R.drawable.ic_launcher_background) // Placeholder example
                },
            contentAlignment = Alignment.Center
        ) {
            if (profilePicture == null) {
                Text(
                    text = "Upload Image",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Image(
                    painter = profilePicture!!,
                    contentDescription = null,
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.height(1.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
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
            value = state.signUpUsername,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.SignUpUsernameChanged(it))
            },
            label = { Text("Username") },
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
            value = state.signUpPassword,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.SignUpPasswordChanged(it))
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default,
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
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
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
                text = "Passwords do not match or fields are incomplete.",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Button(
            onClick = {
                if (confirmPassword.isBlank() || name.isBlank()) {
                    showError = true
                } else {
                    showError = false
                    viewModel.onEvent(AuthUiEvent.SignUp)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Sign Up",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(201.dp))
        Text(
            text = "Already have an account? Log in",
            color = Color(0xFFFF6F61),
            modifier = Modifier.clickable { navigator.navigate(AuthScreenDestination) }
        )
    }
}
