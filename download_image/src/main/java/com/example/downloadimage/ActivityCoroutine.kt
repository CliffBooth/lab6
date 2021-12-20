package com.example.downloadimage

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.net.URL

class MyViewModel: ViewModel() {}

class ActivityCoroutine : AppCompatActivity() {
    private lateinit var imageView: ImageView
    val model: MyViewModel by viewModels()
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
    }

    override fun onStart() {
        super.onStart()
        job = model.viewModelScope.launch(Dispatchers.IO) {
            val image =
                BitmapFactory.decodeStream(URL(imageURL).openConnection().getInputStream())
            withContext(Dispatchers.Main) {
                imageView.setImageBitmap(image)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        job.cancel()
    }
}