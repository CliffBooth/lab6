package com.example.downloadimage

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

const val imageURL = "https://www.wallpapers13.com/wp-content/uploads/2015/12/Lake-Bled-Slovenia-Island-" +
            "Castle-Mountains-Beautiful-Landscape-Wallpaper-Hd-3840x2400-915x515.jpg"

class ActivityExecutor : AppCompatActivity() {

    lateinit var executorService: ExecutorService
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        executorService = Executors.newSingleThreadExecutor()
        executorService.execute {
            val image = BitmapFactory.decodeStream(URL(imageURL).openConnection().getInputStream())
            imageView.post {
                imageView.setImageBitmap(image)
            }
        }
    }
}