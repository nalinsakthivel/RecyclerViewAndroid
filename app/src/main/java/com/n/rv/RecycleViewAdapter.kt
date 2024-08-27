package com.n.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleViewAdapter(private val dataList: ArrayList<DataClass>) :
    RecyclerView.Adapter<RecycleViewAdapter.ModelViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_layout, parent, false)
        return ModelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        val dataClass = dataList[position]
        holder.rvImage.setImageResource(dataClass.imageUrl)
        holder.rvTitle.text = dataClass.title
    }


    class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvImage: ImageView = itemView.findViewById(R.id.cardImageView)
        val rvTitle: TextView = itemView.findViewById(R.id.cardTextView)
    }

}