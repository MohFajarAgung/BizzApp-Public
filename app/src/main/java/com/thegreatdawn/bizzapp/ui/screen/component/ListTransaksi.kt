package com.thegreatdawn.bizzapp.ui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel

@Composable
fun ListTransaksi(
    modifier: Modifier = Modifier,
    briLinkViewModel: BriLinkViewModel,
    date: String
) {
    val transactionData by briLinkViewModel.transactionList

    val showDialogHapus by briLinkViewModel.showDialogHapus

    if (date != "") {
        briLinkViewModel.getAllTransaction(date)
    } else {
        briLinkViewModel.getAllTransaction(briLinkViewModel.getCurrentDate())
    }

    if(transactionData.isEmpty()){
        Text(text = "Belum Ada Transaksi",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            textAlign = TextAlign.Center
            )
    }else{

    LazyColumn(
        modifier = if (date != "") {
            modifier.fillMaxSize()
        } else {
            modifier
                .fillMaxWidth()
                .height(200.dp)
        }


    ) {
        items(transactionData) { item ->
            item?.let { (key, transaction) ->
                transaction?.let {
//                    briLinkViewModel.setTotalTransaksi(totalTransaksi + 1)
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(5.dp))

                    ) {
                        Row {

                            Column(
                                modifier = Modifier.padding(15.dp)
                            ) {
                                Text(
                                    text = transaction.jenisTransaksi,
                                    style = TextStyle(
                                        color = if (transaction.jenisTransaksi == "Transfer") Color(
                                            0xFF4991B9
                                        ) else Color(0xFFAB1717),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    ),

                                    )
                                Spacer(modifier = modifier.height(10.dp))
                                Text(
                                    text = "Nama: ${transaction.nama}",
                                    style = TextStyle(
                                        color = Color.Gray,
                                        fontSize = 12.sp
                                    ),

                                    )
                                Text(
                                    text = "jumlah: Rp. ${briLinkViewModel.formatToCurrency(transaction.jumlah)}",
                                    style = TextStyle(
                                        color = Color.Gray,
                                        fontSize = 12.sp
                                    ),

                                    )
                                Text(
                                    text = "Keuntungan: Rp. ${briLinkViewModel.formatToCurrency(transaction.keuntungan)}",
                                    style = TextStyle(
                                        color = Color.Gray,
                                        fontSize = 12.sp
                                    ),

                                    )

                            }
                            Box(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                                    .height(80.dp), contentAlignment = Alignment.BottomEnd
                            ) {
                                Button(
                                    modifier = modifier
                                        .width(100.dp)
                                        .height(30.dp),
                                    onClick = {
//                                              briLinkViewModel.setShowDialogHapus(true)
                                        if(date !=""){
                                        briLinkViewModel.deleteTransactionByKey(key, date, transaction.jenisTransaksi, transaction.jumlah, transaction.keuntungan)
                                        }else{
                                            briLinkViewModel.deleteTransactionByKey(key,briLinkViewModel.getCurrentDate(), transaction.jenisTransaksi, transaction.jumlah, transaction.keuntungan)
                                        }
                                    },
                                    shape = RoundedCornerShape(5.dp),
                                    colors = ButtonDefaults.buttonColors(Color.Black)
                                ) {
                                    Text(
                                        text = "Hapus",
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 12.sp
                                        ),

                                        )
                                }
                            }
                        }

                    }
                }
            }
        }
    }
    }


}


@Composable
fun dialogHapusTransaksi(
    modifier: Modifier = Modifier,
    briLinkViewModel: BriLinkViewModel,
    key:String?,
    date : String?,
    jenisTransaksi:String?,
    jumlah:Long?,
    keuntungan:Long,
){
    AlertDialog(
        backgroundColor = Color(0xFFEFEFEF),
        onDismissRequest = {
            briLinkViewModel.setShowDialogHapus(false)
        },
        title = {
            Text(
                text = "Yakin Ingin Menghapus?",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp),
                textAlign = TextAlign.Center
            )
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
                            briLinkViewModel.setShowDialogHapus(false)
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = modifier.width(100.dp)
                    ) {
                        androidx.compose.material.Text("Batal")
                    }
                    Spacer(modifier = modifier.padding(horizontal = 10.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(Color.Black),
                        onClick = {
                            if(date !=""){
                                briLinkViewModel.setShowDialogHapus(false)
                                briLinkViewModel.deleteTransactionByKey(key!!, date!!, jenisTransaksi!!, jumlah!!, keuntungan!!)
                            }else{
                                briLinkViewModel.setShowDialogHapus(false)
                                briLinkViewModel.deleteTransactionByKey(key!!,briLinkViewModel.getCurrentDate()!!, jenisTransaksi!!, jumlah!!, keuntungan!!)
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = modifier.width(100.dp)
                    ) {
                        Text("hapus")
                    }
                }
            }

        },

        )
}