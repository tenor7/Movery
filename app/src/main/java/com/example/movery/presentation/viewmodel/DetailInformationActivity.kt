package com.example.movery.presentation.viewmodel

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movery.R

class DetailInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_information)

        val imageViewPoster = intent.getStringExtra("imageViewPoster")
        val favorite = intent.getStringExtra("favorite")
        val textViewTitle = intent.getStringExtra("textViewTitle")
        val textViewRating = intent.getFloatExtra("textViewRating", 0f)
        val textViewDescription = intent.getStringExtra("textViewDescription")
        val textGenre = intent.getStringExtra("textGenre")

        val imageView = findViewById<ImageView>(R.id.foreground_image)
        Glide.with(this).load(imageViewPoster).into(imageView)

        findViewById<TextView>(R.id.textViewTitle).text = textViewTitle
        findViewById<TextView>(R.id.textViewRating).text = textViewRating.toString()
        findViewById<TextView>(R.id.textViewDescription).text = textViewDescription
        findViewById<TextView>(R.id.textGenre).text = textGenre
        findViewById<RatingBar>(R.id.ratingBar).rating = (textViewRating / 2)

        val imageViewBookmark = findViewById<ImageView>(R.id.imageViewBookmark)
        if (favorite == "true") {
            imageViewBookmark.setImageResource(R.drawable.bookmark_filled)
        } else {
            imageViewBookmark.setImageResource(R.drawable.bookmark)
        }
    }
}

