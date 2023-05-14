package com.app.kotlinapp

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kotlinapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object{
        const val SUPERHERO_KEY = "superhero_key"
        const val BITMAP_KEY = "bitmap_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras!!

        val hero = bundle.getParcelable<Hero>(SUPERHERO_KEY)!!
        val bitmap = bundle.getParcelable<Bitmap>(BITMAP_KEY)!!

        binding.superhero = hero
        binding.heroImage.setImageBitmap(bitmap)
    }
}