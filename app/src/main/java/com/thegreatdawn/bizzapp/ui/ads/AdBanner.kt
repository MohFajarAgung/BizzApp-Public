package com.thegreatdawn.bizzapp.ui.ads

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


@Composable
fun AdBanner(briLinkViewModel: BriLinkViewModel) {
    val context = LocalContext.current
    var adLoaded by remember { mutableStateOf(false) }
    val adView = remember {
        AdView(context).apply {
            adSize = AdSize.BANNER
            adUnitId = "ca-app-pub-3078167128340272/1455698616"
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    adLoaded = true
                }
            }
            loadAd(AdRequest.Builder().build())
        }
    }
    Row {
        AndroidView({ adView })
        if(adLoaded){
        Button(onClick = {  briLinkViewModel.setShowAd(false) },
            colors = ButtonDefaults.buttonColors(Color.Gray)
            ) {
            Text(text = "x")
        }
        }
    }

}



