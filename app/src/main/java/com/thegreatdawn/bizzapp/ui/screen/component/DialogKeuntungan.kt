package com.thegreatdawn.bizzapp.ui.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thegreatdawn.bizzapp.ui.ads.AdBanner
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel

@Composable
fun DialogKeuntungan(
    modifier: Modifier = Modifier,
    keuntungan: String,
    briLinkViewModel: BriLinkViewModel
){
    AlertDialog(onDismissRequest = {
                                   briLinkViewModel.setShowDialogKeuntungan(false)
    },
        backgroundColor = Color(0xFFEFEFEF),

        text = {
            Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = "Total Keuntungan",
                style = TextStyle(
                    color = Color.Black, // Change text color to white
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
                Text(
                text = "Rp. ${briLinkViewModel.formatToCurrency(keuntungan.toLong())}",
                style = TextStyle(
                    color = Color(0xFF3E7D38), // Change text color to white
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                textAlign = TextAlign.Center
            )
            AdBanner(briLinkViewModel = briLinkViewModel)
            }

        },
        confirmButton = { /*TODO*/ })

}