package com.app.kotlinapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.app.kotlinapp.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var heroImage: ImageView
    private var heroBitmap: Bitmap? = null
    private var picturePath = ""

    private val getContent = registerForActivityResult(ActivityResultContracts.TakePicture()){
        success ->
        if(success && picturePath.isNotEmpty()){
            heroBitmap = BitmapFactory.decodeFile(picturePath)
            if (heroBitmap != null) {
                heroImage.setImageBitmap(heroBitmap)
            }
        }
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
            openCamera()
        }
    }

    private fun openCamera(){
        val file = createImageFile()
        val uri = FileProvider.getUriForFile(this, "$packageName.provider", file)
        getContent.launch(uri)
    }

    private fun createImageFile(): File {
        val fileName = "superhero_image"
        val fileDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(fileName, ".jpg", fileDirectory)
        picturePath = file.absolutePath
        return file
    }

    private fun openDetailActivity(hero: Hero) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.SUPERHERO_KEY, hero)
        intent.putExtra(DetailActivity.BITMAP_KEY, picturePath)
        startActivity(intent)
    }
}