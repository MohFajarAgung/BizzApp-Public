package com.thegreatdawn.bizzapp.ui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thegreatdawn.bizzapp.R
import com.thegreatdawn.bizzapp.data.ListFeature
import com.thegreatdawn.bizzapp.ui.InHistory
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel

@Composable
fun ListFeatureContent(
    feature: List<ListFeature>,
    screen: String,
    briLinkViewModel: BriLinkViewModel,
    navController: NavController,
    date: String
) {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        Modifier
            .fillMaxWidth()
            .height(230.dp)
    ) {
        items(feature) { item ->
            if (screen == "business") {
                Box(modifier = Modifier
                    .height(120.dp)
                    .padding(8.dp)
                    .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(5.dp))
                    .clickable {

                        when(item.featureName){
                            "Keuntungan"->{
                                briLinkViewModel.setShowDialogKeuntungan(true)
                            }
                            "Transfer" ->{
                                briLinkViewModel.setTransactionVisible(item.featureName)

                            }
                            "Tarik Uang"->{
                                briLinkViewModel.setTransactionVisible(item.featureName)

                            }
                            "Riwayat"->{
                                briLinkViewModel.setShowAd(true)
                                navController.navigate(InHistory.Transaction.createRoute(date))
                            }
                        }


                    }
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        when (item.featureName) {
                            "Transfer" -> Icon(
                                painter = painterResource(id = R.drawable.mdi__bank_transfer_out),
                                contentDescription = "iconTransfer"
                            )

                            "Tarik Uang" -> Icon(
                                painter = painterResource(id = R.drawable.uil__money_withdrawal),
                                contentDescription = "iconTarikUang"
                            )

                            "Keuntungan" -> Icon(
                                painter = painterResource(id = R.drawable.tdesign__money),
                                contentDescription = "iconKeuntungan"
                            )

                            "Riwayat" -> Icon(
                                painter = painterResource(id = R.drawable.solar__history_outline),
                                contentDescription = "iconRiwayat"
                            )


                        }

                        Text(
                            text = item.featureName,
                            style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            ),

                            )
                        Text(
                            text = item.desc,
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 12.sp
                            ),

                            )

                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .padding(8.dp)
                        .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(5.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        when (item.featureName) {
                            "Transfer" -> Icon(
                                painter = painterResource(id = R.drawable.mdi__bank_transfer_out),
                                contentDescription = "iconTransfer"
                            )

                            "Tarik Uang" -> Icon(
                                painter = painterResource(id = R.drawable.uil__money_withdrawal),
                                contentDescription = "iconTarikUang"
                            )

                            "Keuntungan" -> Icon(
                                painter = painterResource(id = R.drawable.tdesign__money),
                                contentDescription = "iconKeuntungan"
                            )

                            "Riwayat" -> Icon(
                                painter = painterResource(id = R.drawable.solar__history_outline),
                                contentDescription = "iconRiwayat"
                            )
                        }
                        Text(
                            text = item.featureName,
                            style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            ),

                            )
                        Text(
                            text = item.desc,
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 12.sp
                            ),

                            )

                    }
                }
            }
        }
    }
}