package com.n.rv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.n.rv.R
import com.n.rv.roomdatabase.dbmodel.ProductListDbModel

class RecycleViewAdapter(
    private val dataList: List<ProductListDbModel>,
    private val mListener: (ProductListDbModel?) -> Unit
) :

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

        Glide.with(holder.itemView.context)
            .load(dataClass.productImage)
            .placeholder(R.drawable.baseline_android_24)
            .error(R.drawable.baseline_android_24)
            .into(holder.rvImage)

        holder.rvTitle.text = dataClass.productName
        holder.itemView.setOnClickListener {
            dataClass.let { it1 -> mListener.invoke(it1) }
        }
    }

    class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvImage: ImageView = itemView.findViewById(R.id.cardImageView)
        val rvTitle: TextView = itemView.findViewById(R.id.cardTextView)
    }

}
