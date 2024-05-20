package com.galactapp.composebanners.ui.components

import android.os.Bundle
import android.util.Log
import androidx.annotation.Dimension
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.os.bundleOf
import coil.compose.rememberImagePainter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions




@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AdMobNativeAdy() {
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
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.White)
        ) {
            val (icon, headline, body, advertiser, stars, cta) = createRefs()

            Image(
                painter = rememberImagePainter(ad.icon?.drawable),
               // painter = painterResource(id = R.drawable.dog),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .constrainAs(icon) {
                        start.linkTo(parent.start, 8.dp)
                        top.linkTo(parent.top, 8.dp)
                    }
            )

            Text(
                text = ad.headline ?: "",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.constrainAs(headline) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            )

            Text(
                text = ad.body ?: "",
                modifier = Modifier.constrainAs(body) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(headline.bottom, 4.dp)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            )

            Text(
                text = ad.advertiser ?: "Ad",
                modifier = Modifier.constrainAs(advertiser) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(body.bottom, 4.dp)
                }
            )

            Row(
                modifier = Modifier.constrainAs(stars) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(advertiser.bottom, 4.dp)
                }
            ) {
                for (i in 0 until (ad.starRating?.toInt() ?: 0)) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                }
            }

            Button(
                onClick = { /* TODO: Add click handling */ },
                modifier = Modifier.constrainAs(cta) {
                    start.linkTo(parent.start)
                    top.linkTo(stars.bottom, 8.dp)
                    end.linkTo(parent.end)
                }
            ) {
                Text(text = ad.callToAction ?: "Install")
            }
        }
    }

    adLoadError?.let { error ->
        // Exibir a mensagem de erro na UI para fins de depuração
        Text("Ad failed to load: $error", color = Color.Red)
    }
}

//

@Composable
fun AdMobNativeAdb() {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var adLoadError by remember { mutableStateOf<String?>(null) }

    DisposableEffect(context) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
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
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.White)
        ) {
            val (icon, headline, body, advertiser, stars, cta) = createRefs()

            Image(
                painter = rememberImagePainter(ad.icon?.drawable),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .constrainAs(icon) {
                        start.linkTo(parent.start, 8.dp)
                        top.linkTo(parent.top, 8.dp)
                    }
            )

            Text(
                text = ad.headline ?: "",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.constrainAs(headline) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            )

            Text(
                text = ad.body ?: "",
                modifier = Modifier.constrainAs(body) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(headline.bottom, 4.dp)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            )

            Text(
                text = ad.advertiser ?: "Ad",
                modifier = Modifier.constrainAs(advertiser) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(body.bottom, 4.dp)
                }
            )

            Row(
                modifier = Modifier.constrainAs(stars) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(advertiser.bottom, 4.dp)
                }
            ) {
                for (i in 0 until (ad.starRating?.toInt() ?: 0)) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                }
            }

            Button(
                onClick = { /* TODO: Add click handling */ },
                modifier = Modifier.constrainAs(cta) {
                    start.linkTo(parent.start)
                    top.linkTo(stars.bottom, 8.dp)
                    end.linkTo(parent.end)
                }
            ) {
                Text(text = ad.callToAction ?: "Install")
            }
        }
    }

    adLoadError?.let { error ->
        Text("Ad failed to load: $error", color = Color.Red)
    }
}

//=============================

@Composable
fun AdMobNativeAdc() {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var adLoadError by remember { mutableStateOf<String?>(null) }

    DisposableEffect(context) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
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
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White)
        ) {
            val (icon, headline, body, advertiser, stars, cta) = createRefs()

            Image(
                painter = rememberImagePainter(ad.icon?.drawable),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
            )

            Text(
                text = ad.headline ?: "",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.constrainAs(headline) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            )

            Row(
                modifier = Modifier.constrainAs(stars) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(headline.bottom, 4.dp)
                }
            ) {
                for (i in 0 until (ad.starRating?.toInt() ?: 0)) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                }
            }

            Text(
                text = ad.body ?: "",
                modifier = Modifier.constrainAs(body) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(stars.bottom, 4.dp)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            )

            Text(
                text = ad.advertiser ?: "Ad",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(4.dp)
                    .constrainAs(advertiser) {
                        start.linkTo(icon.end, 8.dp)
                        top.linkTo(body.bottom, 4.dp)
                    }
            )

            Button(
                onClick = { /* TODO: Add click handling */ },
                modifier = Modifier.constrainAs(cta) {
                    start.linkTo(parent.start)
                    top.linkTo(icon.bottom, 8.dp)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            ) {
                Text(text = ad.callToAction ?: "Install")
            }
        }
    }

    adLoadError?.let { error ->
        Text("Ad failed to load: $error", color = Color.Red)
    }
}
//==========================

@Composable
fun AdMobNativeAdAdmob() {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var adLoadError by remember { mutableStateOf<String?>(null) }

    DisposableEffect(context) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
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
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White)
        ) {
            val (icon, headline, body, advertiser, stars, cta) = createRefs()

            Image(
                painter = rememberImagePainter(ad.icon?.drawable),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
            )

            Text(
                text = ad.headline ?: "",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.constrainAs(headline) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            )

            Row(
                modifier = Modifier.constrainAs(stars) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(headline.bottom, 4.dp)
                }
            ) {
                for (i in 0 until (ad.starRating?.toInt() ?: 0)) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                }
            }

            Text(
                text = ad.body ?: "",
                modifier = Modifier.constrainAs(body) {
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(stars.bottom, 4.dp)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            )

            Text(
                text = ad.advertiser ?: "Ad",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(4.dp)
                    .constrainAs(advertiser) {
                        start.linkTo(icon.end, 8.dp)
                        top.linkTo(body.bottom, 4.dp)
                    }
            )

            Button(
                onClick = { /* TODO: Add click handling */ },
                shape = RoundedCornerShape(16.dp), // Arredonda os cantos do botão
                colors = ButtonDefaults.buttonColors(Color(0xFF1E88E5)), // Cor do botão
                modifier = Modifier
                    .constrainAs(cta) {
                        start.linkTo(parent.start)
                        top.linkTo(icon.bottom, 8.dp)
                        end.linkTo(parent.end)
                        width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                    }
            ) {
                Text(text = ad.callToAction ?: "Install", color = Color.White)
            }
        }
    }

    adLoadError?.let { error ->
        Text("Ad failed to load: $error", color = Color.Red)
    }
}
//===================================
@Composable
fun AdMobNativeAdComponente() {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var adLoadError by remember { mutableStateOf<String?>(null) }

    DisposableEffect(context) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(16.dp))
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                val (icon, headline, advertiser, cta, infoIcon) = createRefs()

                Image(
                    painter = rememberImagePainter(ad.icon?.drawable),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .constrainAs(icon) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                )

                Text(
                    text = ad.headline ?: "",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.constrainAs(headline) {
                        start.linkTo(icon.end, 8.dp)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                    }
                )

                Text(
                    text = ad.advertiser ?: "Ad",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .padding(4.dp)
                        .constrainAs(advertiser) {
                            start.linkTo(icon.end, 8.dp)
                            top.linkTo(headline.bottom, 4.dp)
                        }
                )

                Button(
                    onClick = { ad.performClick(Bundle()) },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors( Color(0xFF1E88E5)),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .constrainAs(cta) {
                        start.linkTo(parent.start)
                        top.linkTo(advertiser.bottom, 8.dp)
                        end.linkTo(parent.end)
                        width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                    }
                ) {
                    Text(text = ad.callToAction ?: "Abrir", color = Color.White)
                }

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(infoIcon) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                        .clickable {
                            // Navegar para a tela de informações
                        }
                )
            }
        }
    }

    adLoadError?.let { error ->
        Text("Ad failed to load: $error", color = Color.Red)
    }
}

/// com body =============

@Composable
fun AdMobNativeAdComponent() {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var adLoadError by remember { mutableStateOf<String?>(null) }

    DisposableEffect(context) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(16.dp))
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                val (icon, headline, advertiserBodyRow, cta, infoIcon) = createRefs()

                Image(
                    painter = rememberImagePainter(ad.icon?.drawable),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .constrainAs(icon) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                )

                Text(
                    text = ad.headline ?: "",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.constrainAs(headline) {
                        start.linkTo(icon.end, 8.dp)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                    }
                )

                Row(
                    modifier = Modifier
                        .constrainAs(advertiserBodyRow) {
                            start.linkTo(icon.end, 12.dp)
                            top.linkTo(headline.bottom, 8.dp)
                            end.linkTo(parent.end)
                            width = androidx.constraintlayout.compose.Dimension.fillToConstraints

                        }
                ) {
                    Text(
                        text = "Ad",
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      //  modifier = Modifier.padding(end=5.dp),
                        text = ad.body ?: "",
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray)

                    )
                }

                Button(
                    onClick = {
                        val clickData = Bundle().apply {
                            putString("x", "100") // Coordenada x do clique
                            putString("y", "50")  // Coordenada y do clique
                            putString("duration_millis", "500") // Duração do toque
                        }
                        ad.performClick(clickData)
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF1E88E5)),
                    modifier = Modifier.constrainAs(cta) {
                        start.linkTo(parent.start)
                        top.linkTo(advertiserBodyRow.bottom, 12.dp)
                        end.linkTo(parent.end)
                        width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                    }
                ) {
                    Text(text = ad.callToAction ?: "Abrir", color = Color.White)
                }

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(infoIcon) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                        .clickable {
                            // Navegar para a tela de informações
                        }
                )
            }
        }
    }

    adLoadError?.let { error ->
        Text("Ad failed to load: $error", color = Color.Red)
    }
}
