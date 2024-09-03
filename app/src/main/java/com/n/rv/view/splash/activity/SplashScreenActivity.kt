package com.n.rv.view.splash.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.n.rv.databinding.ActivitySplashScreenBinding
import com.n.rv.view.product.activity.ProductActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed(Runnable{
            val i = Intent(this, ProductActivity::class.java)
            startActivity(i)
            finish()
        }, 2000)
    }
}