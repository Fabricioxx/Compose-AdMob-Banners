# Compose-AdMob-Banners


Inicializar o SDK do AdMob
Certifique-se de que o SDK do AdMob está sendo inicializado no método onCreate da sua MainActivity.


```kotlin

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
                .setTestDeviceIds(listOf("ID-DISPOSITIVO-TESTE-ADMOB")) // Substitua pelo ID do seu dispositivo de teste
                .build()
        )


```


Verificação do Manifesto
Seu AndroidManifest.xml está correto, mas vamos garantir que todas as permissões e metadados estejam no lugar certo:

```xml

<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

     <!-- permições internet -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="com.google.android.gms.permission.AD_ID" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MeuTheme"
        tools:targetApi="31">

        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.MeuTheme">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- ID do aplicativo AdMob -->
        <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="ca-app-pub-3940256099942544~3347511713"/>

    </application>
</manifest>

```




## Banner admob Small

1. **@drawable/rounded_background** e **@drawable/rounded_button** são recursos drawable que você precisará criar para definir os fundos com bordas arredondadas. Aqui estão exemplos de como você pode defini-los:

**res/drawable/rounded_background.xml**
```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#FFFFFF"/>
    <stroke android:width="1dp" android:color="#808080"/>
    <corners android:radius="16dp"/>
</shape>
```

**res/drawable/rounded_button.xml**
```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#1E88E5"/>
    <corners android:radius="16dp"/>
</shape>
```

2. Criando o layout e os componentes internos:

**res/layout/ad_unified.xml**   
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.google.android.gms.ads.nativead.NativeAdView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/ad_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@drawable/rounded_background"
        android:padding="8dp">

        <ImageView
            android:id="@+id/ad_icon"
            android:layout_width="60dp"
            android:layout_height="60dp"
            android:layout_alignParentStart="true"
            android:layout_marginStart="9dp"
            android:layout_marginEnd="8dp"
            android:contentDescription="Image anuncio"
            android:scaleType="fitCenter" />

        <TextView
            android:id="@+id/ad_headline"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_toEndOf="@id/ad_icon"
            android:textColor="#000000"
            android:textSize="16sp"
            android:textStyle="bold" />

        <LinearLayout
            android:id="@+id/ad_advertiser_body"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_below="@id/ad_headline"
            android:layout_marginTop="8dp"
            android:layout_toEndOf="@id/ad_icon"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/ad_advertiser"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_vertical"
                android:layout_marginEnd="4dp"
                android:background="#D3D3D3"
                android:padding="4dp"
                android:text="Ad"
                android:textColor="#000000"
                android:textSize="12sp"
                android:textStyle="bold" />

            <TextView
                android:id="@+id/ad_body"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="#808080"
                android:textSize="12sp" />

        </LinearLayout>

        <Button
            android:id="@+id/ad_call_to_action"
            android:layout_width="349dp"
            android:layout_height="48dp"
            android:layout_below="@id/ad_advertiser_body"
            android:layout_alignParentEnd="true"
            android:layout_marginTop="18dp"
            android:layout_marginEnd="8dp"
            android:background="@drawable/rounded_button"
            android:text="Install"
            android:textColor="#FFFFFF"
            android:contentDescription="Call to Action Button"
            android:padding="12dp"
            />
    </RelativeLayout>
</com.google.android.gms.ads.nativead.NativeAdView>

```
3. Composable do Anúncio Nativo
**Aqui está o Composable para carregar o anúncio nativo, com logs adicionais para depuração:**
```kotlin
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

```





