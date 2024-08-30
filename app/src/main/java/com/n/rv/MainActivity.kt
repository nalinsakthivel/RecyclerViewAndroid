package com.n.rv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.n.rv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var database: AppDatabase
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "my_database"
        ).build()
    }
}