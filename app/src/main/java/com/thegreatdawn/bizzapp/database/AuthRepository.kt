package com.thegreatdawn.bizzapp.database

import androidx.navigation.NavController
import com.thegreatdawn.bizzapp.SettingsManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val dbs = Firebase.database

    val userRef = dbs.getReference("users")
    fun getCurrentUserUid(): String? {
        return auth.currentUser?.uid
    }

    val briLinkRef = getCurrentUserUid()?.let { userRef.child(it).child("business/agenBriLink") }

    fun getUsername(UserNode: (String?, String?) -> Unit) {
        var username: String
        getCurrentUserUid()?.let { userRef.child(it) }
            ?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val username = snapshot.child("username").value as? String
                    val email = snapshot.child("email").value as? String
                    UserNode(username, email)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        auth.signOut()
    }


    fun signUpWithEmailAndPassword(
        username: String,
        email: String,
        pw: String,
        onResult: (Boolean, Exception?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, pw)
            .addOnSuccessListener { result ->
                onResult(true, null) // Sign-up berhasil
                userRef.child(result.user!!.uid).child("username").setValue(username)
                userRef.child(result.user!!.uid).child("email").setValue(email)
            }
            .addOnFailureListener { exception ->
                onResult(false, exception) // Sign-up gagal
            }
    }

    fun signInWithEmailAndPassword(
        email: String,
        pw: String,
        onResult: (Boolean, Exception?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, pw)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                }
            }
            .addOnFailureListener { exception ->
                onResult(false, exception)
            }
    }

//    Bri Link

    fun setBusiness(currentDateArg: String, result: (String?) -> Unit) {
        getCurrentUserUid()?.let {
            val dateRef =
                userRef.child(it).child("business/agenBriLink").child("date").child(currentDateArg)

            dateRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() == false) {
                        dateRef.child("date").setValue(currentDateArg)
                        dateRef.child("saldoRekening").setValue(0)
                        dateRef.child("saldoCash").setValue(0)
                        dateRef.child("keuntungan").setValue(0)
                        result("berhasil")

                    } else {
//                        tampilkan sesuatu jika tanggal sudah ada
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })

        }
    }


    fun getHistoryByDate(currentDateArg: String, History: (HistoryNode?) -> Unit) {
        getCurrentUserUid()?.let {
            val dateRef =
                userRef.child(it).child("business/agenBriLink").child("date").child(currentDateArg)


            dateRef.get().addOnSuccessListener {
                History(it.getValue(HistoryNode::class.java))
            }

            dateRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() == false) {
//                        settingsManager.businessIsStarted= false
                    } else {
//                        tampilkan sesuatu jika tanggal sudah ada
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }

    }

    fun dateCheck(
        currentDateArg: String,
        settingsManager: SettingsManager,
        navController: NavController
    ) {
        getCurrentUserUid()?.let {
            val dateRef =
                userRef.child(it).child("business/agenBriLink").child("date").child(currentDateArg)


            dateRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() == false) {
                        settingsManager.businessIsStarted = false
                        navController.navigate("home")
                    } else {
                        settingsManager.businessIsStarted = true
//                        tampilkan sesuatu jika tanggal sudah ada
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
    }

    fun editSaldo(currentDateArg: String, saldoCash: Long?, saldoRekening: Long?) {
        getCurrentUserUid()?.let {
            val dateRef =
                userRef.child(it).child("business/agenBriLink").child("date").child(currentDateArg)

            dateRef.child("saldoRekening").setValue(saldoRekening)
            dateRef.child("saldoCash").setValue(saldoCash)
        }
    }

    fun getAllDailyHistory(historyList: (List<HistoryNode?>) -> Unit) {
        getCurrentUserUid()?.let {
            val dateRef = userRef.child(it).child("business/agenBriLink").child("date")
            dateRef.get().addOnSuccessListener { dataSnapshot ->
                val dataList = mutableListOf<HistoryNode>()
                dataSnapshot.children.forEach { snapshot ->
                    val data = snapshot.getValue(HistoryNode::class.java)
                    data?.let {
                        dataList.add(it)
                    }
                }
                historyList(dataList)
            }
        }
    }

    fun setTransactionByCurrentDate(
        currentDateArg: String,
        name: String,
        jenisTransaksi: String,
        jumlah: Long,
        keuntungan: Long,
        result: (String?) -> Unit
    ) {
        getCurrentUserUid()?.let {
            val dateRef =
                userRef.child(it).child("business/agenBriLink").child("date").child(currentDateArg)

            dateRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val transactionRef = dateRef.child("transaksi").push()
                    transactionRef.child("nama").setValue(name)
                    transactionRef.child("jenisTransaksi").setValue(jenisTransaksi)
                    transactionRef.child("jumlah").setValue(jumlah)
                    transactionRef.child("keuntungan").setValue(keuntungan)


                    dateRef.child("saldoCash").get().addOnSuccessListener { cash ->
                        dateRef.child("saldoRekening").get()
                            .addOnSuccessListener { rekening ->
                                dateRef.child("keuntungan").get()
                                    .addOnSuccessListener { keuntunganCurrent ->

                                        val currentSaldoCash = cash.value as? Long ?: 0
                                        val currentSaldoRek = rekening.value as? Long ?: 0
                                        val currentKeuntungan = keuntunganCurrent.value as? Long ?: 0
                                        if (jenisTransaksi == "Transfer") {
                                            val newSaldoCash = currentSaldoCash + jumlah
                                            val newSaldoRek = currentSaldoRek - jumlah
                                            val keuntungan = currentKeuntungan + keuntungan
                                            dateRef.child("saldoRekening").setValue(newSaldoRek)
                                            dateRef.child("saldoCash").setValue(newSaldoCash)
                                            dateRef.child("keuntungan").setValue(keuntungan)
                                        }
                                        else if(jenisTransaksi == "Tarik Uang"){
                                            val newSaldoCash = currentSaldoCash - jumlah
                                            val newSaldoRek = currentSaldoRek + jumlah
                                            val keuntungan = currentKeuntungan + keuntungan
                                            dateRef.child("saldoRekening").setValue(newSaldoRek)
                                            dateRef.child("saldoCash").setValue(newSaldoCash)
                                            dateRef.child("keuntungan").setValue(keuntungan)
                                        }
                                        result("berhasil")
                                    }
                            }
                    }.addOnFailureListener {
                        result("gagal")
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
    }

    fun getAllTransaction(
        currentDateArg: String,
        transaction: (List<Pair<String, TransactionNode?>>) -> Unit
    ) {
        getCurrentUserUid()?.let {
            val dateRef =
                userRef.child(it).child("business/agenBriLink").child("date").child(currentDateArg)
                    .child("transaksi")

            dateRef.get().addOnSuccessListener { dataSnapshot ->
                val dataList = mutableListOf<Pair<String, TransactionNode>>()
                dataSnapshot.children.forEach { snapshot ->
                    val key = snapshot.key
                    val data = snapshot.getValue(TransactionNode::class.java)
                    data?.let {
                        dataList.add((key to data) as Pair<String, TransactionNode>)
                    }
                }
                transaction(dataList)
            }
        }
    }


    fun deleteTransactionByKey(key: String, currentDateArg: String, jenisTransaksi: String, jumlah:Long, keuntungan:Long, result: (String?) -> Unit) {
        getCurrentUserUid()?.let {
            val dateRef = userRef.child(it).child("business/agenBriLink").child("date").child(currentDateArg)
            val transactionRef = dateRef.child("transaksi")
            transactionRef.child(key).removeValue()
            dateRef.child("saldoCash").get().addOnSuccessListener { cash ->
                dateRef.child("saldoRekening").get()
                    .addOnSuccessListener { rekening ->
                        dateRef.child("keuntungan").get()
                            .addOnSuccessListener { keuntunganCurrent ->

                                val currentSaldoCash = cash.value as? Long ?: 0
                                val currentSaldoRek = rekening.value as? Long ?: 0
                                val currentKeuntungan = keuntunganCurrent.value as? Long ?: 0
                                if (jenisTransaksi == "Transfer") {
                                    val newSaldoCash = currentSaldoCash - jumlah
                                    val newSaldoRek = currentSaldoRek + jumlah
                                    val keuntungan = currentKeuntungan - keuntungan
                                    dateRef.child("saldoRekening").setValue(newSaldoRek)
                                    dateRef.child("saldoCash").setValue(newSaldoCash)
                                    dateRef.child("keuntungan").setValue(keuntungan)
                                } else if(jenisTransaksi == "Tarik Uang"){
                                    val newSaldoCash = currentSaldoCash + jumlah
                                    val newSaldoRek = currentSaldoRek - jumlah
                                    val keuntungan = currentKeuntungan - keuntungan
                                    dateRef.child("saldoRekening").setValue(newSaldoRek)
                                    dateRef.child("saldoCash").setValue(newSaldoCash)
                                    dateRef.child("keuntungan").setValue(keuntungan)
                                }
                                result("berhasil")
                            }
                    }
            }.addOnFailureListener {
                result("gagal")
            }

        }
    }
}