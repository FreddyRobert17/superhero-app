package com.app.kotlinapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import com.app.kotlinapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var heroImage: ImageView
    private var heroBitmap: Bitmap? = null
    private var picturePath = ""

    private val getContent = registerForActivityResult(ActivityResultContracts.TakePicture()){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val superheroName = binding.heroName.text.toString()
            val alterEgo = binding.heroAlterEgo.text.toString()
            val bio = binding.heroBio.text.toString()
            val power = binding.heroRatingBar.rating

            val hero = Hero(superheroName, alterEgo, bio, power)
            openDetailActivity(hero)
        }

        heroImage = binding.heroImage
        binding.heroImage.setOnClickListener {

        }
    }

    private fun openDetailActivity(hero: Hero) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.SUPERHERO_KEY, hero)
        intent.putExtra(DetailActivity.BITMAP_KEY, heroImage.drawable.toBitmap())
        startActivity(intent)
    }
}