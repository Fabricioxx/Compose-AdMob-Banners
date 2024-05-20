package com.galactapp.composebanners.ui.components


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.galactapp.composebanners.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

// dispositivo: android:value="ca-app-pub-7984508059642281~1561449281"/>
/// val adLoader = AdLoader.Builder(context, "ca-app-pub-7984508059642281/9777255737")

@Composable
fun AdMobNativeAd() {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var adLoadError by remember { mutableStateOf<String?>(null) }

    DisposableEffect(context) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110") // Use um ID de teste para depuração
            .forNativeAd { ad: NativeAd ->
                nativeAd = ad
                Log.d("AdMobNativeAd", "Ad loaded successfully")
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adLoadError = adError.message
                    Log.e("AdMobNativeAd", "Ad failed to load: ${adError.message}")
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
        Log.d("AdMobNativeAd", "Ad loading initiated")

        onDispose {
            nativeAd?.destroy()
        }
    }

    nativeAd?.let { ad ->
        AndroidView(
            factory = { context ->
                val inflater = LayoutInflater.from(context)
                val adView = inflater.inflate(R.layout.ad_unified, null) as NativeAdView
                populateNativeAdView(ad, adView)
                adView
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.White)
        )
    }

    adLoadError?.let { error ->
        // Exibir a mensagem de erro na UI para fins de depuração
        Text("Ad failed to load: $error", color = Color.Red)
    }
}


fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
    adView.headlineView = adView.findViewById(R.id.ad_headline)
    adView.bodyView = adView.findViewById(R.id.ad_body)
    adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
    adView.iconView = adView.findViewById(R.id.ad_icon)
    adView.advertiserView = adView.findViewById(R.id.ad_advertiser)
    adView.starRatingView = adView.findViewById(R.id.ad_stars)

    (adView.headlineView as TextView).text = nativeAd.headline
    adView.bodyView?.let { (it as TextView).text = nativeAd.body
        it.visibility = if (nativeAd.body != null) View.VISIBLE else View.INVISIBLE
    }
    adView.callToActionView?.let {
        (it as Button).text = nativeAd.callToAction ?: "Install"
        it.visibility = if (nativeAd.callToAction != null) View.VISIBLE else View.INVISIBLE
    }
    adView.iconView?.let {
        (it as ImageView).setImageDrawable(nativeAd.icon?.drawable)
        it.visibility = if (nativeAd.icon != null) View.VISIBLE else View.INVISIBLE
    }
   adView.advertiserView?.let {
        (it as TextView).text = nativeAd.advertiser ?: "Ad"
       it.visibility =  View.VISIBLE
    }
    adView.starRatingView?.let {
        (it as RatingBar).rating = nativeAd.starRating?.toFloat() ?: 0f
        it.visibility = if (nativeAd.starRating != null) View.VISIBLE else View.INVISIBLE
    }
    adView.setNativeAd(nativeAd)
}