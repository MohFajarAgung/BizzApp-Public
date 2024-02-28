package com.thegreatdawn.bizzapp.ui.screen.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thegreatdawn.bizzapp.ui.ads.AdBanner
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogUbahSaldo(
    modifier: Modifier = Modifier,
    briLinkViewModel: BriLinkViewModel
){
    var saldoRekening by remember {
        mutableStateOf(TextFieldValue())
    }
    var saldoCash by remember {
        mutableStateOf(TextFieldValue())
    }

    val context = LocalContext.current

    AlertDialog(
        backgroundColor = Color(0xFFEFEFEF),
        onDismissRequest = {
          briLinkViewModel.setShowDialog(false)
        },
        title = {
          Text(
                text = "Ubah Saldo",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
       Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
           Column(
               modifier = Modifier.background(Color(0xFFEFEFEF), shape = RoundedCornerShape(5.dp))
           ) {
               Text(
                   modifier = Modifier.padding(top = 20.dp, start = 15.dp), text = "Saldo Rekening",
                   style = TextStyle(
                       color = Color.Black, // Change text color to white
                       fontWeight = FontWeight.Bold,
                   )
               )
               Spacer(modifier = modifier.height(10.dp))
               TextField(
                   modifier = Modifier
                       .fillMaxWidth(0.9f), // Mengatur bobot ke 1f untuk mengambil sisa ruang dalam Column
                   value =saldoRekening ,
                   onValueChange = {
                           newValue ->
                       val formattedValue = briLinkViewModel.formatInputCurrency(newValue.text.filter { it.isDigit() })
                       saldoRekening = TextFieldValue(
                           text = formattedValue,
                           selection = TextRange(formattedValue.length)
                       )
                   },
                   colors = TextFieldDefaults.textFieldColors(
                       containerColor = Color.White,
                       unfocusedIndicatorColor = Color.Transparent
                   ),
                   placeholder = {
                       Text(text = "Masukkan Saldo Rekening Anda")
                   },
                   keyboardOptions = KeyboardOptions.Default.copy(
                       imeAction = ImeAction.Next,
                       keyboardType = KeyboardType.Number
                   ),
               )
           }
           Spacer(modifier = Modifier.height(10.dp))
           Column(
               modifier = Modifier.background(Color(0xFFEFEFEF), shape = RoundedCornerShape(5.dp))
           ) {
               Text(
                   modifier = Modifier.padding(top = 20.dp, start = 15.dp), text = "Saldo Cash",
                   style = TextStyle(
                       color = Color.Black, // Change text color to white
                       fontWeight = FontWeight.Bold,
                   )
               )
               Spacer(modifier = modifier.height(10.dp))

               TextField(
                   modifier = Modifier
                       .fillMaxWidth(0.9f), // Mengatur bobot ke 1f untuk mengambil sisa ruang dalam Column
                   value = saldoCash,
                   onValueChange = {newValue ->
                                  val formattedValue = briLinkViewModel.formatInputCurrency(newValue.text.filter { it.isDigit() })
                                   saldoCash = TextFieldValue(
                                       text = formattedValue,
                                       selection = TextRange(formattedValue.length)
                                   )

                   },
                   colors = TextFieldDefaults.textFieldColors(
                       containerColor = Color.White,
                       unfocusedIndicatorColor = Color.Transparent
                   ),
                   placeholder = {
                       Text(text = "Masukkan Saldo Cash Anda")
                   },
                   keyboardOptions = KeyboardOptions.Default.copy(
                       imeAction = ImeAction.Done,
                       keyboardType = KeyboardType.Number
                   ),
               )
           }
       }


        },
        confirmButton = {
            Column(
                modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                        Row() {
                            Button(
                                colors = ButtonDefaults.buttonColors(Color.Gray),
                                onClick = {
                                briLinkViewModel.setShowDialog(false)
                                },
                                shape = RoundedCornerShape(10.dp),
                                modifier = modifier.width(100.dp)
                            ) {
                              Text("Batal")
                            }
                            Spacer(modifier = modifier.padding(horizontal = 10.dp))
                           Button(
                                colors = ButtonDefaults.buttonColors(Color.Black),
                                onClick = {
                                    if(saldoRekening.text.length !=0 && saldoCash.text.length != 0){
                                        briLinkViewModel.editSaldo(briLinkViewModel.stringCurrencyToLong(saldoRekening.text), briLinkViewModel.stringCurrencyToLong(saldoCash.text), briLinkViewModel.getCurrentDate())
                                        briLinkViewModel.setShowDialog(false)
                                    }else{
                                        Toast.makeText(context, "Lengkapi Semuanya", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                modifier = modifier.width(100.dp)
                            ) {
                                androidx.compose.material3.Text("Submit")
                            }
                        }

                AdBanner(briLinkViewModel = briLinkViewModel)
            }

        },

        )

}
