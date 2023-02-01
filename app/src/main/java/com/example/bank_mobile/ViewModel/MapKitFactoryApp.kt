package com.example.bank_mobile.ViewModel

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class MapKitFactoryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("1");
    }
}