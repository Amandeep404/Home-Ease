package com.example.homeease

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.homeease.database.MyOrderDatabase
import com.example.homeease.repo.OrderRepository
import com.example.homeease.viewmodel.OrderViewModelFactory
import com.example.homeease.viewmodel.OrdersViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: OrdersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val orderRepository = OrderRepository(MyOrderDatabase(this))
        val viewModelFactory = OrderViewModelFactory(orderRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(OrdersViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewMain) as NavHostFragment
        val navController =navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNavBar).setupWithNavController(navController)


    }
}