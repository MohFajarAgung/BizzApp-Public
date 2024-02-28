package com.thegreatdawn.bizzapp.ui.screen.business

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.TextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionSection(
    modifier: Modifier = Modifier,
    briLinkViewModel: BriLinkViewModel,
    jenisTransaksi : String
){

    var name by remember {
        mutableStateOf("")
    }
    var jumlah by remember {
        mutableStateOf(TextFieldValue())
    }
    var keuntungan by remember {
        mutableStateOf(TextFieldValue())
    }

    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD9D9D9), shape = RoundedCornerShape(topStart = 50.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text( modifier = modifier.padding(20.dp),
            text = jenisTransaksi,
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        Text( modifier = modifier.padding(20.dp).fillMaxWidth(0.9f),
            text = "Masukkan Nama",
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )

        TextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            value = name,
            onValueChange = { name = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                 Text(text = "Masukkan Nama")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),

            )

        Text( modifier = modifier.padding(20.dp).fillMaxWidth(0.9f),
            text = "Masukkan Jumlah",
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )

        TextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            value = jumlah,
            onValueChange = { newJumlah ->
                val filteredValue = newJumlah.text.filter { it.isDigit() }
                val formattedText =  briLinkViewModel.formatInputCurrency(filteredValue)

                jumlah = TextFieldValue(
                    text = formattedText,
                    selection = TextRange(formattedText.length)
                )

              },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(text = "Masukkan Jumlah")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),

            )
        Text( modifier = modifier.padding(20.dp).fillMaxWidth(0.9f),
            text = "Masukkan Keuntungan",
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )

        TextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            value = keuntungan,
            onValueChange = {newKeuntungan ->
                            val formattedValue = briLinkViewModel.formatInputCurrency(newKeuntungan.text.filter { it.isDigit() })
                            keuntungan = TextFieldValue(
                                text = formattedValue,
                                selection = TextRange(formattedValue.length)
                            )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(text = "Masukkan Keuntungan")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),

            )




        Row(modifier = modifier.padding(30.dp)) {
            Button(
                colors = ButtonDefaults.buttonColors(Color.Gray),
                onClick = {
                          briLinkViewModel.setTransactionVisible("")
                },
                shape = RoundedCornerShape(10.dp),
                modifier = modifier.width(100.dp)
            ) {
                Text(
                     text = "Batal",
                    style = TextStyle(
                        color = Color.White, // Change text color to white
                    )
                )
            }
            Spacer(modifier = modifier.padding(horizontal = 10.dp))
            Button(
                colors = ButtonDefaults.buttonColors(Color.Black),
                onClick = {
                    if(name != "" && jumlah.text.length !=0 && keuntungan.text.length != 0) {
                    briLinkViewModel.setTransaction(jenisTransaksi, briLinkViewModel.stringCurrencyToLong(jumlah.text),briLinkViewModel.stringCurrencyToLong(keuntungan.text), name)


                    }else{
                        Toast.makeText(context, "Lengkapi Semua nya", Toast.LENGTH_SHORT).show()
                    }

                },
                shape = RoundedCornerShape(10.dp),
                modifier = modifier.width(100.dp)
            ) {
                Text("Submit")
            }
        }
    }

}
@Composable
fun TransactionAnimatedVisibility(
    visible: String ,
    content: @Composable () -> Unit
) {
    val targetOffset = if (visible!="") 0.dp else 750.dp // Menentukan pergeseran vertikal
    val animatedOffset by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier.offset(y = animatedOffset),
    ) {
        content()
    }
}



