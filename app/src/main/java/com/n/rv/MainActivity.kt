package com.n.rv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.n.rv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private  var dataClass: ArrayList<DataClass> = arrayListOf()

    private  var image: Array<Int> = arrayOf()
    private  var text: Array<String> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecycleView()
        image = arrayOf(
            R.drawable.about_us,
            R.drawable.apple,
            R.drawable.book_bus,
            R.drawable.delete,
            R.drawable.fb,
            R.drawable.grievance,
            R.drawable.home_menu,
            R.drawable.home_menu,
            R.drawable.home_menu,
            R.drawable.home_menu,
            R.drawable.home_menu,
            R.drawable.home_menu,
            R.drawable.home_menu,
            R.drawable.home_menu,
            R.drawable.home_menu,
            R.drawable.home_menu,

            )
        text = arrayOf(
            "About Us",
            "Apple",
            "Book Bus",
            "Delete",
            "Facebook",
            "Grievance",
            "Home",
            "Home",
            "Home",
            "Home",
            "Home",
            "Home",
            "Home",
            "Home",
            "Home",
            "Home",
        )


       // dataClass = arrayListOf()
        setRecycleView()
    }


    private fun setRecycleView() {
        for (item in image.indices) {
            val dataList = DataClass(image[item], text[item])
            dataClass.add(dataList)
        }
        recycleViewAdapter = RecycleViewAdapter(dataClass)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = recycleViewAdapter
    }


}