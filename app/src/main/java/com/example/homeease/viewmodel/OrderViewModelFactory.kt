package com.example.homeease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homeease.repo.OrderRepository

class OrderViewModelFactory(
    val orderRepository: OrderRepository
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrdersViewModel(orderRepository) as T
    }
}