package com.galactapp.composebanners

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.galactapp.composebanners.ui.theme.ComposeBannersTheme
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import androidx.compose.ui.unit.dp
import com.galactapp.composebanners.ui.components.AdMob
import com.galactapp.composebanners.ui.components.AdMobNativeAd
import com.galactapp.composebanners.ui.components.AdMobNativeAd2
import com.galactapp.composebanners.ui.components.AdMobNativeAdAdmob
import com.galactapp.composebanners.ui.components.AdMobNativeAdComponent
import com.galactapp.composebanners.ui.components.AdMobNativeAdComponente
import com.galactapp.composebanners.ui.components.AdMobNativeAdc
import com.galactapp.composebanners.ui.components.loadNative


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o SDK do AdMob
        MobileAds.initialize(this) { initializationStatus ->
            Log.d("MainActivity", "AdMob initialized: $initializationStatus")
        }
        // Configure seu dispositivo como dispositivo de teste
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("2620e952-1202-478f-b8ae-9187d98cf81b")) // Substitua pelo ID do seu dispositivo de teste
                .build()
        )



        setContent {
            ComposeBannersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                   AdMobNativeAd()

                   // AdMobNativeAdAdmob() - sem xml

                 //   AdMobNativeAdComponente() - sem xml
                  //  AdMobNativeAdComponent() - sem xml
                    // AdMobNativeAdc() - sem xml
                    //AdMobNativeAdb() - sem xml
                    // AdMobNativeAdy()  - sem xml

                   // loadNative() - errro
                    Spacer(modifier = Modifier.height(30.dp).width(30.dp))
                    Spacer(modifier = Modifier.size(70.dp))

                   // AdMob()
                  //  Greeting("Android")
                  // AdMobNativeAd2()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeBannersTheme {
        Greeting("Android")
    }
}