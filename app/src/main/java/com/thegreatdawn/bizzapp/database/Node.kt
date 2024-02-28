package com.thegreatdawn.bizzapp.database

data class UserNode(
    val username : String = "",
    val email : String = ""
)

data class HistoryNode(
    val date : String = "",
    val saldoRekening : Long =0,
    val saldoCash : Long = 0,
    val keuntungan : Long = 0
)

data class TransactionNode(
    val jenisTransaksi : String = "",
    val nama: String = "",
    val jumlah : Long = 0,
    val keuntungan: Long = 0
)