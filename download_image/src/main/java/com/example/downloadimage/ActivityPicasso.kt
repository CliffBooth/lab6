package com.example.downloadimage

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class ActivityPicasso: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView = findViewById<ImageView>(R.id.imageView)

        Picasso.get().load(imageURL).into(imageView)
    }
}