package com.thegreatdawn.bizzapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.thegreatdawn.bizzapp.database.AuthRepository
import com.thegreatdawn.bizzapp.ui.BizzApp
import com.thegreatdawn.bizzapp.ui.theme.BizzAppTheme
import com.thegreatdawn.bizzapp.viewModel.AuthViewModel
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel
import com.thegreatdawn.bizzapp.viewModel.MyViewModelFactory
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : ComponentActivity() {
    private val authRepository = AuthRepository()
    private val viewModelFactory = MyViewModelFactory(authRepository, this)
    private val authViewModel by viewModels<AuthViewModel> { viewModelFactory }
    private val briLinkViewModel by viewModels<BriLinkViewModel> { viewModelFactory }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        MobileAds.initialize(this) {}
        setContent {


            BizzAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    showInterstialAd()
//                    SignUpScreen(authViewModel = authViewModel)
//                    SignInScreen(authViewModel = authViewModel)
                    BizzApp(authViewModel = authViewModel, briLinkViewModel = briLinkViewModel, showInertialAd = { showInterstialAd() })
                }
            }
//            FormattedTextField()
        }
    }

    private fun showInterstialAd() {
        InterstitialAd.load(
            this,
            "ca-app-pub-3078167128340272/2432688613", //Change this with your own AdUnitID!
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.show(this@MainActivity)
                }
            }
        )
    }

}








