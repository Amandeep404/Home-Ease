package com.example.homeease.repo

import com.example.homeease.database.MyOrderDatabase
import com.example.homeease.model.MyOrder

class OrderRepository(
    val db : MyOrderDatabase
) {
    suspend fun upsert(order : MyOrder) = db.getOrders().upsert(order)

    fun getBookedOrders() =db.getOrders().getAllOrders()
}