package com.example.homeease.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeease.model.MyOrder
import com.example.homeease.repo.OrderRepository
import kotlinx.coroutines.launch

class OrdersViewModel(
    val orderRepository: OrderRepository
) : ViewModel(){
    fun bookedOrders(order:MyOrder) = viewModelScope.launch {
        orderRepository.upsert(order)
    }

    fun getSavedBooks() = orderRepository.getBookedOrders()
}

