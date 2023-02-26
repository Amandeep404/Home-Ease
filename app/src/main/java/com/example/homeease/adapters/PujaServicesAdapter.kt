package com.example.homeease.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeease.R
import com.example.homeease.model.Service

class PujaServicesAdapter(private val context: Context, private val services : List<Service>) : RecyclerView.Adapter<PujaServicesAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_most_used_services, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = services.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = services[position]
        val image  = holder.itemView.findViewById<ImageView>(R.id.ivMostUsedServiceIcon)
        Glide.with(context)
            .load(item.icon)
            .into(image)
        holder.itemView.findViewById<TextView>(R.id.tvMostUsedService).text = item.title

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it ->
                it(item)

            }
        }
    }

    private var onItemClickListener: ((Service) -> Unit)? = null

    fun setOnItemClickListener(listener : (Service) -> Unit){
        onItemClickListener = listener
    }
}