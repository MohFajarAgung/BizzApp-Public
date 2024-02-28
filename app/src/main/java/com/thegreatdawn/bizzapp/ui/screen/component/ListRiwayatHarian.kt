package com.thegreatdawn.bizzapp.ui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel


@Composable
fun ListRiwayatHarian(
    modifier: Modifier = Modifier,
    briLinkViewModel: BriLinkViewModel,
    navigateToTransaction : (String) -> Unit
) {
    val historyData by briLinkViewModel.historyList
    briLinkViewModel.getAllDailyHistory()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()

    ) {
        items(historyData) { item ->
            item?.let {
                Box(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(5.dp))
                    .clickable {
                          navigateToTransaction(item.date)
                    }

                ) {
                    Row {

                        Column(
                            modifier = Modifier.padding(15.dp)
                        ) {
                            Text(
                                text = item.date,
                                style = TextStyle(
                                    color = Color(0xFF4991B9),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ),

                                )
                            Spacer(modifier = modifier.height(10.dp))
                            Text(
                                text = "Saldo Rekening: Rp. ${briLinkViewModel.formatToCurrency(item.saldoRekening)}",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 15.sp
                                ),

                                )
                            Text(
                                text = "Saldo Cash: Rp. ${briLinkViewModel.formatToCurrency(item.saldoCash)}",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 15.sp
                                ),

                                )
                            Text(
                                text = "Keuntungan: Rp. ${briLinkViewModel.formatToCurrency(item.keuntungan)}",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 15.sp
                                ),

                                )

                        }
//                        Box(
//                            modifier = modifier
//                                .fillMaxWidth()
//                                .padding(15.dp)
//                                .height(80.dp)
//                              , contentAlignment = Alignment.BottomEnd
//                        ) {
//                            Button(
//                                modifier = modifier.width(80.dp).height(30.dp),
//                                onClick = {
////                                    briLinkViewModel.editSaldo()
//                                },
//                                shape = RoundedCornerShape(5.dp),
//                                colors = ButtonDefaults.buttonColors(Color.Black)
//                            ) {
//                                Text(
//                                    text = "Uabh",
//                                    style = TextStyle(
//                                        color = Color.White,
//                                        fontSize = 12.sp
//                                    ),
//
//                                    )
//                            }
//                        }
                    }
                }
            }
        }
    }
}