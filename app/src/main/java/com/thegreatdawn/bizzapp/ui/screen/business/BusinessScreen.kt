package com.thegreatdawn.bizzapp.ui.screen.business

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thegreatdawn.bizzapp.SettingsManager
import com.thegreatdawn.bizzapp.data.ListFeature
import com.thegreatdawn.bizzapp.ui.ads.AdBanner
import com.thegreatdawn.bizzapp.ui.screen.component.DialogKeuntungan
import com.thegreatdawn.bizzapp.ui.screen.component.DialogUbahSaldo
import com.thegreatdawn.bizzapp.ui.screen.component.ListFeatureContent
import com.thegreatdawn.bizzapp.ui.screen.component.ListTransaksi
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel

@Composable
fun BusinessScreen(
    modifier: Modifier = Modifier,
    briLinkViewModel: BriLinkViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val settingsManager = remember {
        SettingsManager(context)
    }
    briLinkViewModel.dateCheck(settingsManager, navController)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, bottom = 30.dp)
    ) {
        item {
            Column(Modifier.padding(bottom = 20.dp, start = 20.dp)) {
                val showAd by briLinkViewModel.showAd
                if(showAd){
                    Box(modifier = modifier.fillMaxWidth().padding(bottom = 10.dp), contentAlignment = Alignment.Center ){
                        AdBanner(briLinkViewModel)
                    }
                }
                Text(
                    text = "Agen BRI Link",
                    style = TextStyle(
                        color = Color.Black, // Change text color to white
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
        }
        item {
            SaldoContent(briLinkViewModel = briLinkViewModel)
            Spacer(modifier = modifier.height(10.dp))
        }
        item{
            ListFeatureContent(
                feature =
                listOf(
                    ListFeature("Transfer",  "Transfer Uang Pelanggan"),
                    ListFeature("Tarik Uang",  "Tarik Uang Pelanggan"),
                    ListFeature("Keuntungan", "Total Keuntungan dari Semua Transaksi"),
                    ListFeature("Riwayat",  "Harian & Transaksi"),
                ),
                screen = "business",
                briLinkViewModel = briLinkViewModel,
                navController = navController,
                date = briLinkViewModel.getCurrentDate()
            )
        }
        item {
            Text(
                text = "Transaksi Hari Ini",
                style = TextStyle(
                    color = Color.Black, // Change text color to white
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ), modifier = Modifier.padding(vertical = 20.dp)
            )
        }
        item{
//            ListRiwayatHarian(briLinkViewModel = briLinkViewModel)
            ListTransaksi(briLinkViewModel = briLinkViewModel, date = "")
        }


    }

    val isTransactionVisible by briLinkViewModel.isTransactionVisible
    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){

    TransactionAnimatedVisibility(visible = isTransactionVisible) {
        TransactionSection(briLinkViewModel= briLinkViewModel, jenisTransaksi = isTransactionVisible)
    }
    }

}

@Composable
fun SaldoContent(
    modifier: Modifier = Modifier,
    briLinkViewModel: BriLinkViewModel
) {
    val history by briLinkViewModel.historyNode
    briLinkViewModel.getHistoryByDate(briLinkViewModel.getCurrentDate())


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
                text = "Saldo Anda",
                style = TextStyle(
                    color = Color.White, // Change text color to white
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Spacer(modifier = modifier.height(20.dp))
            Text(
                text = "Saldo Rekening",
                style = TextStyle(
                    color = Color.White, // Change text color to white
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                )
            )
            Spacer(modifier = modifier.height(10.dp))


            Text(
                text = "Rp. ${history?.let { it.saldoRekening }
                    ?.let { briLinkViewModel.formatToCurrency(it) }}",
                style = TextStyle(
                    color = Color.White, // Change text color to white
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp
                )
            )
            Spacer(modifier = modifier.height(20.dp))
            Text(
                text = "Saldo Cash",
                style = TextStyle(
                    color = Color.White, // Change text color to white
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                )
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(
                text = "Rp. ${history?.let { it.saldoCash }
                    ?.let { briLinkViewModel.formatToCurrency(it) }}",
                style = TextStyle(
                    color = Color.White, // Change text color to white
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp
                )
            )
            Spacer(modifier = modifier.height(20.dp))
            Button(
                onClick = {
                  briLinkViewModel.setShowDialog(true)
                },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF4991B9))
            ) {
                Text("Ubah Saldo")
            }

        }

    }
    val showDialogUbahSaldo by briLinkViewModel.showDialog
    if(showDialogUbahSaldo){
        DialogUbahSaldo(briLinkViewModel = briLinkViewModel)
    }
    val showDialogKeuntungan by briLinkViewModel.showDialogKeuntungan
    if(showDialogKeuntungan){
        DialogKeuntungan(keuntungan = history?.keuntungan.toString(), briLinkViewModel = briLinkViewModel)
    }

}

