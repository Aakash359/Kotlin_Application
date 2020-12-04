package com.example.kotline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class PicassoImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_picasso)
        val imageView = findViewById(R.id.imageview) as ImageView
        val glideimage = findViewById(R.id.glide_img) as ImageView
        Picasso
            .get()
            .load("https://www.humanesociety.org/sites/default/files/styles/1240x698/public/2018/08/kitten-440379.jpg?h=c8d00152&itok=1fdekAh2")
            .into(imageView)


             Glide.with(applicationContext)
                 .load("https://www.humanesociety.org/sites/default/files/styles/1240x698/public/2018/08/kitten-440379.jpg?h=c8d00152&itok=1fdekAh2")
                 .into(glideimage)
    }
}