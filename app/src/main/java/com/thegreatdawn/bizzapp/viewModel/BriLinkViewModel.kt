package com.thegreatdawn.bizzapp.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.thegreatdawn.bizzapp.database.HistoryNode
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.compose.runtime.State
import androidx.navigation.NavController
import com.thegreatdawn.bizzapp.SettingsManager
import com.thegreatdawn.bizzapp.database.AuthRepository
import com.thegreatdawn.bizzapp.database.TransactionNode
import java.text.NumberFormat

class BriLinkViewModel(
    val authRepository: AuthRepository
)  : ViewModel()
{

    private val _showDialog = mutableStateOf(false)
    val showDialog = _showDialog
    fun setShowDialog(value : Boolean){
        _showDialog.value = value
    }
    private val _showAd = mutableStateOf(false)
    val showAd = _showAd
    fun setShowAd(value : Boolean){
        _showAd.value = value
    }

    private val _showDialogKeuntungan = mutableStateOf(false)
    val showDialogKeuntungan = _showDialogKeuntungan

    fun setShowDialogKeuntungan(value : Boolean){
        _showDialogKeuntungan.value = value
    }

    private val _showDialogHapus = mutableStateOf(false)
    val showDialogHapus = _showDialogHapus

    fun setShowDialogHapus(value : Boolean){
        _showDialogHapus.value = value
    }



    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    fun getCurrentDate(): String{
        val currentDate = Calendar.getInstance().time
        return dateFormat.format(currentDate)
    }

    fun setBusiness(settingsManager: SettingsManager, navController: NavController){
        authRepository.setBusiness(getCurrentDate()){ result ->
            if(result == "berhasil"){
                settingsManager.businessIsStarted = true
                navController.navigate("home")
            }
        }
    }
   fun dateCheck(settingsManager: SettingsManager, navController: NavController){
       authRepository.dateCheck( getCurrentDate(),settingsManager,navController)
   }

    private val _historyNode = mutableStateOf<HistoryNode?>(null)
    val historyNode : State<HistoryNode?> = _historyNode

    fun getHistoryByDate(date: String){
        authRepository.getHistoryByDate(date){ history ->
            _historyNode.value = history
        }
    }

    private val _historyList = mutableStateOf<List<HistoryNode?>>(emptyList())
    val historyList: State<List<HistoryNode?>>
        get() = _historyList
   fun getAllDailyHistory(){
       authRepository.getAllDailyHistory{ historyList ->
           _historyList.value = historyList
       }
   }

    fun editSaldo(saldoRekening: Long, saldoCash: Long, date: String){
        authRepository.editSaldo(getCurrentDate(), saldoCash, saldoRekening)
        getHistoryByDate(date)
    }


  fun setTransaction(jenisTransaksi : String, jumlah: Long, keuntungan : Long, name : String){
      authRepository.setTransactionByCurrentDate(
          getCurrentDate(),
          name,
          jenisTransaksi,
          jumlah,
          keuntungan
          ){result ->
          if(result == "berhasil"){
              setShowAd(true)
              getHistoryByDate(getCurrentDate())
              getAllTransaction(getCurrentDate())
              setTransactionVisible("")
          }

      }
  }
    private val _transactionList = mutableStateOf<List<Pair<String, TransactionNode?>>>(emptyList())
    val transactionList: State<List<Pair<String, TransactionNode?>>>
        get() = _transactionList
    fun getAllTransaction(date: String){
        authRepository.getAllTransaction(date){ transactionList ->
        _transactionList.value=transactionList
            _totalTransaksi.value = _transactionList.value.size
        }
    }

    fun deleteTransactionByKey(key : String, date : String, jenisTransaksi: String, jumlah: Long, keuntungan: Long){
        authRepository.deleteTransactionByKey(key, date, jenisTransaksi, jumlah, keuntungan){result ->
            if(result=="berhasil"){
                getHistoryByDate(date)
            }
        }
        getAllTransaction(date)
    }


    private val _isTrasactionVisible = mutableStateOf("")
    val isTransactionVisible get() = _isTrasactionVisible

    fun setTransactionVisible(value: String) {
        _isTrasactionVisible.value = value
    }

    private val _totalTransaksi = mutableStateOf(0)
    val totalTransaksi get() = _totalTransaksi



    fun formatToCurrency(nilai : Long) :String{
        val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
        return numberFormat.format(nilai)
    }


    fun stringCurrencyToLong(currency: String): Long {
        // Remove any non-numeric characters (e.g., commas or currency symbols)
        val cleanedCurrency = currency.replace(Regex("[^0-9]"), "")

        val currencyValue = cleanedCurrency.toDouble()

        // Convert the currency value to a long value
        return currencyValue.toLong()
    }

    // Fungsi untuk memformat angka menjadi format Rupiah


    fun formatInputCurrency(input: String): String {
        val numberFormat = NumberFormat.getInstance(Locale("id", "ID"))
        numberFormat.isGroupingUsed = true
        val number = try {
            input.toLong()
        } catch (e: NumberFormatException) {
            return ""
        }
        return numberFormat.format(number)
    }


    // Function to parse the input value and update the formatted value



}