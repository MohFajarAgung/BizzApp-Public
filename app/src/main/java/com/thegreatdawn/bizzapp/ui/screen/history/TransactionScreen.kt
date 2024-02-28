package com.thegreatdawn.bizzapp.ui.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thegreatdawn.bizzapp.ui.ads.AdBanner
import com.thegreatdawn.bizzapp.ui.screen.component.ListTransaksi
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel


@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    briLinkViewModel: BriLinkViewModel,
    historyKey: String
) {

    val totalTransaksi by briLinkViewModel.totalTransaksi
    val history by briLinkViewModel.historyNode
    briLinkViewModel.getHistoryByDate(historyKey)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val showAd by briLinkViewModel.showAd
        if(showAd){
            Box(modifier = modifier.padding(bottom = 10.dp)){
            AdBanner(briLinkViewModel = briLinkViewModel)
            }
        }
        Text(
            text = "Riwayat Transaksi",
            style = TextStyle(
                color = Color.Black, // Change text color to white
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        Spacer(modifier = modifier.height(20.dp) )
        Box(
            modifier = modifier
                .background(
                    color = Color(0xFF434367),
                    shape = RoundedCornerShape(10.dp)
                )

                .fillMaxWidth()
        ) {
            Column(modifier = modifier.padding(20.dp)) {
                Text(
                    text = historyKey,
                    style = TextStyle(
                        color = Color.White, // Change text color to white
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = modifier.height(20.dp))
                Row(modifier = modifier.fillMaxWidth()) {
                    Column(modifier=modifier.weight(0.5f)) {
                        Text(
                            text = "Total Keuntungan",
                            style = TextStyle(
                                color = Color.White, // Change text color to white
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = modifier.height(10.dp))

                        Text(
                            text = "Rp. ${
                                history?.let { it.keuntungan }
                                    ?.let { briLinkViewModel.formatToCurrency(it) }
                            }",
                            style = TextStyle(
                                color = Color.White, // Change text color to white
                                fontWeight = FontWeight.Bold,
                                fontSize =25.sp
                            )
                        )
                    }
                    Column(modifier = modifier.padding(end = 20.dp)) {
                        Text(
                            text = "Total Transaksi",
                            style = TextStyle(
                                color = Color.White, // Change text color to white
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = modifier.height(10.dp))

                        Text(
                            text = "$totalTransaksi Orang",
                            style = TextStyle(
                                color = Color.White, // Change text color to white
                                fontWeight = FontWeight.Bold,
                                fontSize =25.sp
                            )
                        )
                    }
                }




            }

        }

        ListTransaksi(briLinkViewModel = briLinkViewModel, date = historyKey)
    }

}