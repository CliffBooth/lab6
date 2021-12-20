package com.example.downloadimage

import android.app.Application
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

const val imageURL =
    "https://www.wallpapers13.com/wp-content/uploads/2015/12/Lake-Bled-Slovenia-Island-" +
            "Castle-Mountains-Beautiful-Landscape-Wallpaper-Hd-3840x2400-915x515.jpg"

class MyApplication : Application() {
    val executorService: ExecutorService = Executors.newSingleThreadExecutor()
}

class ActivityExecutor : AppCompatActivity() {

    private lateinit var imageView: ImageView
    lateinit var future: Future<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
    }

    override fun onStart() {
        super.onStart()
        future = (application as MyApplication).executorService.submit {
            val image =
                BitmapFactory.decodeStream(URL(imageURL).openConnection().getInputStream())
            Log.i("my", "thread ${Thread.currentThread().id} downloaded!")
            imageView.post {
                imageView.setImageBitmap(image)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        future.cancel(true)
    }
}