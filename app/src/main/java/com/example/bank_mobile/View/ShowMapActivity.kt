package com.example.bank_mobile.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bank_mobile.databinding.ActivityShowMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView


class ShowMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this);

        binding = ActivityShowMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        includeMap()
    }

    private fun includeMap() {
        with(binding) {
            var map : MapView = mapview
            mapview.map.move(
                CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 0F),
                null
            )
        }
    }
}