package com.thegreatdawn.bizzapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.thegreatdawn.bizzapp.data.ListBusiness
import com.thegreatdawn.bizzapp.data.ListFeature
import com.thegreatdawn.bizzapp.ui.screen.component.ListFeatureContent
import com.thegreatdawn.bizzapp.viewModel.AuthViewModel
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    agenBriLinkViewModel: BriLinkViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val settingsManager = remember { SettingsManager(context) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, bottom = 30.dp)
    ) {
        item {
            Column(Modifier.padding(bottom = 20.dp, start = 20.dp)) {
                Text(
                    text = "BizApp",
                    style = TextStyle(
                        color = Color.Black, // Change text color to white
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Text("Kelola Bisnismu dengan Efisien")
            }
        }
        item {

            ChooseABusinessContent()
        }
        item {
            Text(
                text = "Fitur",
                style = TextStyle(
                    color = Color.Black, // Change text color to white
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ), modifier = Modifier.padding(20.dp)
            )
        }
        item {
       ListFeatureContent(
           feature = listOf(
               ListFeature("Transfer", "Transfer Uang Pelanggan"),
               ListFeature("Tarik Uang", "Tarik Uang Pelanggan"),
               ListFeature("Keuntungan", "Total Keuntungan dari Semua Transaksi"),
               ListFeature("Riwayat",  "Harian & Transaksi"),
           ),
           screen = "home",
           briLinkViewModel = agenBriLinkViewModel,
           navController = navController,
           date = agenBriLinkViewModel.getCurrentDate()
       )
        }



    }
     Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
    Button(
        onClick = {
          agenBriLinkViewModel.setBusiness(settingsManager, navController)
        },
        colors = ButtonDefaults.buttonColors(Color.Black),
        modifier = Modifier
//                    .padding(vertical = 50.dp)
            .fillMaxWidth()
            .padding(bottom = 20.dp, end = 30.dp, start = 30.dp)
            .height(50.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text("Mulai Bisnis Anda")
    }
     }
    Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center){

    }
}

@Composable
fun ChooseABusinessContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFF434367),
                shape = RoundedCornerShape(10.dp)

            )
            .fillMaxWidth()
    ) {
        Column(modifier = modifier.padding(20.dp)) {
            Row {
                Icon(Icons.Default.Home, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "Pilh Bisnis Anda",
                    style = TextStyle(
                        color = Color.White, // Change text color to white
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            ListBusiness(
                business = listOf(
                    ListBusiness(
                        "Agen BRI Link",
                        "icon1"
                    ),

                    ),

                )

        }

    }
}

@Composable
fun ListBusiness(business: List<ListBusiness>) {

    LazyRow() {
        items(business) { item ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 20.dp),
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                        .size(100.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Default.Home, contentDescription = "IconBusiness")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = item.businessName,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp
                    ),

                    )
            }
        }
    }

}

