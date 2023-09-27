package com.example.clasemapas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clasemapas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.google.setOnClickListener{
            var intent = Intent(baseContext, MapsActivity::class.java)
            startActivity(intent)
        }
        binding.street.setOnClickListener{
            var intent = Intent(baseContext, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}