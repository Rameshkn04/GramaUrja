package com.example.gramaurja.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gramaurja.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current

    val auth = FirebaseAuth.getInstance()

    val sharedPreferences =
        context.getSharedPreferences(
            "loginPrefs",
            Context.MODE_PRIVATE
        )

    var email by remember {
        mutableStateOf(
            sharedPreferences.getString("email", "") ?: ""
        )
    }

    var password by remember {
        mutableStateOf(
            sharedPreferences.getString("password", "") ?: ""
        )
    }

    var rememberPassword by remember {
        mutableStateOf(true)
    }

    val googleSignInClient = GoogleSignIn.getClient(

        context,

        GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )

            .requestIdToken(
                context.getString(R.string.default_web_client_id)
            )

            .requestEmail()

            .build()
    )

    val launcher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.StartActivityForResult()
        ) { result ->

            val task =
                GoogleSignIn.getSignedInAccountFromIntent(
                    result.data
                )

            if (task.isSuccessful) {

                val account = task.result

                val credential =
                    GoogleAuthProvider.getCredential(
                        account.idToken,
                        null
                    )

                auth.signInWithCredential(credential)

                    .addOnCompleteListener {

                        if (it.isSuccessful) {

                            Toast.makeText(
                                context,
                                "Google Sign In Successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            navController.navigate("home")
                        }
                    }
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B3D2E),
                        Color(0xFF145A32),
                        Color(0xFF1E8449)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),

            verticalArrangement = Arrangement.Center,

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Grama Urja",
                color = Color.White,
                fontSize = 38.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Smart Village Power Monitoring",
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),

                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Login",
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(

                        value = email,

                        onValueChange = {
                            email = it
                        },

                        label = {
                            Text("Email")
                        },

                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null
                            )
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp),

                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(

                        value = password,

                        onValueChange = {
                            password = it
                        },

                        label = {
                            Text("Password")
                        },

                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null
                            )
                        },

                        visualTransformation =
                            PasswordVisualTransformation(),

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp),

                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = rememberPassword,
                            onCheckedChange = {
                                rememberPassword = it
                            }
                        )

                        Text(
                            text = "Remember Password"
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(

                        onClick = {

                            if (
                                email.isEmpty() ||
                                password.isEmpty()
                            ) {

                                Toast.makeText(
                                    context,
                                    "Please fill all fields",
                                    Toast.LENGTH_SHORT
                                ).show()

                                return@Button
                            }

                            auth.signInWithEmailAndPassword(
                                email,
                                password
                            )

                                .addOnCompleteListener {

                                    if (it.isSuccessful) {

                                        if (rememberPassword) {

                                            sharedPreferences.edit()
                                                .putString("email", email)
                                                .putString("password", password)
                                                .apply()
                                        }

                                        Toast.makeText(
                                            context,
                                            "Login Successful",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        navController.navigate("home")

                                    } else {

                                        Toast.makeText(
                                            context,
                                            "Invalid Email or Password",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),

                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Text(
                            text = "Login",
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(

                        onClick = {

                            launcher.launch(
                                googleSignInClient.signInIntent
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),

                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Text(
                            text = "Sign in with Google",
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(

                        onClick = {

                            navController.navigate("register")
                        }

                    ) {

                        Text(
                            text = "New User? Create Account"
                        )
                    }
                }
            }
        }
    }
}