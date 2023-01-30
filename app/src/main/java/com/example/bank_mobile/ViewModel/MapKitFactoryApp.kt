package com.example.bank_mobile.ViewModel

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class MapKitFactoryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("ddb6c3ee-81c9-4f11-b57f-a39fc3886c0b");
    }
}