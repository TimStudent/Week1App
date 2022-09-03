package com.example.week1app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week1app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        val view = binding.root
        setContentView(view)
        binding.CreateAccountButton.setOnClickListener{
            Intent(baseContext, MainActivity2::class.java).apply{
                startActivity(this)
            }
        }
    }

}