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
import androidx.compose.foundation.lazy.LazyColumn
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
import com.thegreatdawn.bizzapp.R
import com.thegreatdawn.bizzapp.viewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpSection(authViewModel: AuthViewModel) {
    val context = LocalContext.current

    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    var passwordConfirmVisibility by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF4F4F4), shape = RoundedCornerShape(topStart = 50.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Row(modifier = Modifier.padding(40.dp)) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(100.dp)
                        .background(
                            Color.Gray,
                            shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                        )
                        .clickable {
                            authViewModel.setSignUpVisible(false)
                            authViewModel.setLoginVisible(true)
                        },
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
                            Color(0xFF434367),
                            shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                        ),
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
                    modifier = Modifier.padding(top = 20.dp, start = 15.dp), text = "Username",
                    style = TextStyle(
                        color = Color.Black, // Change text color to white
                        fontWeight = FontWeight.Bold,
                    )
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f), // Mengatur bobot ke 1f untuk mengambil sisa ruang dalam Column
                    value = username,
                    onValueChange = { username = it },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(text = "Masukkan Username Anda")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

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
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
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
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
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
                        imeAction = ImeAction.Next,
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
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.background(Color.White, shape = RoundedCornerShape(5.dp))
            ) {
                Text(
                    modifier = Modifier.padding(top = 20.dp, start = 15.dp),
                    text = "Confirm Password",
                    style = TextStyle(
                        color = Color.Black, // Change text color to white
                        fontWeight = FontWeight.Bold,
                    )
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "Konfirmasi Password",

                            )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (passwordConfirmVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordConfirmVisibility = !passwordConfirmVisibility }) {
                            Icon(
                                painter = if (passwordConfirmVisibility) painterResource(id = R.drawable.baseline_visibility_24) else painterResource(id = R.drawable.baseline_visibility_off_24),
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    }
                    )
            }

            Button(
                onClick = {
                    if (username != "" && email != "" && password != "" && confirmPassword != "") {

                        if (password == confirmPassword) {
                            authViewModel.signUpWithEmailAndPassword(username, email, password)
                        } else {
                            Toast.makeText(context, "Password berbeda", Toast.LENGTH_SHORT).show()

                        }

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
                Text("Daftar")
            }

            Row {
                Text(
                    modifier = Modifier.padding(vertical = 20.dp), text = "Anda sudah terdaftar? ",
                    style = TextStyle(
                        color = Color.Black, // Change text color to white
                        fontWeight = FontWeight.Bold,
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clickable {
                            authViewModel.setLoginVisible(true)
                            authViewModel.setSignUpVisible(false)
                        }
                    , text = "Masuk",
                    style = TextStyle(
                        color = Color(0xFF4991B9), // Change text color to white
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        }
    }
}