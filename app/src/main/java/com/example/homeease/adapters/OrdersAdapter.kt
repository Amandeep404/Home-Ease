package com.example.homeease.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homeease.R
import com.example.homeease.model.MyOrder
import com.example.homeease.model.Orders
import com.example.homeease.model.Service

class OrdersAdapter(private val context: Context) :RecyclerView.Adapter<OrdersAdapter.ViewHolder>(){

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<MyOrder>(){
        override fun areItemsTheSame(oldItem: MyOrder, newItem: MyOrder): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyOrder, newItem: MyOrder): Boolean {
            return oldItem==newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_orders, parent, false)
        return OrdersAdapter.ViewHolder(view)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = differ.currentList[position]
        holder.itemView.findViewById<TextView>( R.id.tvCompanyName).text = items.title
        holder.itemView.findViewById<TextView>( R.id.tvServicesOffered).text = items.subtitle
        holder.itemView.findViewById<TextView>( R.id.tvOtp).text = items.otp
        holder.itemView.findViewById<TextView>( R.id.tvDate).text = items.date
        holder.itemView.findViewById<TextView>( R.id.tvTextCost).text = items.cost
    }
}