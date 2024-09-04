package com.n.rv.view.product.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.n.rv.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(tag, "onCreate called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy called")
    }
}