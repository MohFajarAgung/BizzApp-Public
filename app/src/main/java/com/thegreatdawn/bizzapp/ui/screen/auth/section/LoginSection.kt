package com.thegreatdawn.bizzapp.ui.screen.auth.section

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thegreatdawn.bizzapp.R
import com.thegreatdawn.bizzapp.viewModel.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginSection(authViewModel: AuthViewModel, navController: NavController) {
    val context = LocalContext.current


    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisibility by remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier.fillMaxWidth(0.9f),
        value = email,
        onValueChange = { email = it },

        )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF4F4F4), shape = RoundedCornerShape(topStart = 50.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.padding(40.dp)) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(100.dp)
                    .background(
                        Color(0xFF434367),
                        shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Login",
                    style = TextStyle(
                        color = Color.White, // Change text color to white
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(100.dp)
                    .background(
                        Color.Gray,
                        shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                    )
                    .clickable {
                        authViewModel.setLoginVisible(false)
                        authViewModel.setSignUpVisible(true)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sign Up",
                    style = TextStyle(
                        color = Color.White, // Change text color to white
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        }

        Column(
            modifier = Modifier.background(Color.White, shape = RoundedCornerShape(5.dp))
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp, start = 15.dp), text = "Email",
                style = TextStyle(
                    color = Color.Black, // Change text color to white
                    fontWeight = FontWeight.Bold,
                )
            )
            TextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = email,
                onValueChange = { email = it },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(text = "Masukkan Email Anda")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),


                )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.background(Color.White, shape = RoundedCornerShape(5.dp))
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp, start = 15.dp), text = "Password",
                style = TextStyle(
                    color = Color.Black, // Change text color to white
                    fontWeight = FontWeight.Bold,
                )
            )
            TextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = password,
                onValueChange = { password = it },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Masukkan Password Anda",

                        )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),

                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            painter = if (passwordVisibility) painterResource(id = R.drawable.baseline_visibility_24) else painterResource(id = R.drawable.baseline_visibility_off_24),
                            contentDescription = "Toggle password visibility"
                        )
                    }
                }
                )
        }
        Button(
            onClick = {
                if (email != "" && password != "") {
                    authViewModel.signInWithEmailAndPassword(email, password, navController)

                } else {
                    Toast.makeText(context, "Lengkapi Semuanya", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier
                .padding(vertical = 50.dp)
                .fillMaxWidth(0.9f)
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Masuk")
        }

        Row {
        Text(
            modifier = Modifier.padding(vertical = 20.dp), text = "Apakah anda belum mendaftar? ",
            style = TextStyle(
                color = Color.Black, // Change text color to white
                fontWeight = FontWeight.Bold,
            )
        )
            Text(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .clickable {
                        authViewModel.setLoginVisible(false)
                        authViewModel.setSignUpVisible(true)
                    }
                , text = "Daftar",
                style = TextStyle(
                    color = Color(0xFF4991B9), // Change text color to white
                    fontWeight = FontWeight.Bold,
                )
            )
        }

    }
}
