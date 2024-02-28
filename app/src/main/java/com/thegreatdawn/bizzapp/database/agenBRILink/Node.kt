package com.thegreatdawn.bizzapp.database.agenBRILink

data class DailyHistory(
    val date : String ="",
    val accountBalance  : Long = 0L,
    val cashBalance : Long = 0L,
    val overallProfit : Long =0L
)

data class TransactionHistory(
    val time : String ="",
    val TransactionType: String = "",
    val customerName :String = "",
    val amount : Long = 0L,
    val profit : Long = 0L,
)

