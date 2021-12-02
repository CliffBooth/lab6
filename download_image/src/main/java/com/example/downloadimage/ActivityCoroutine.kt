package com.example.downloadimage

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class ActivityCoroutine: AppCompatActivity() {
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        lifecycleScope.launch(Dispatchers.IO) {
            val image = BitmapFactory.decodeStream(URL(imageURL).openConnection().getInputStream())
            imageView.post {
                imageView.setImageBitmap(image)
            }
        }
    }
}