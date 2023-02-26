package com.example.homeease

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeease.adapters.OrdersAdapter
import com.example.homeease.adapters.ServicesAdapter
import com.example.homeease.databinding.FragmentCheckOutBinding
import com.example.homeease.databinding.FragmentMyOrdersBinding
import com.example.homeease.model.MyOrder
import com.example.homeease.viewmodel.OrdersViewModel
import kotlin.math.cos


class MyOrdersFragment : Fragment() {

    private lateinit var myAdapter: OrdersAdapter
    private lateinit var viewModel: OrdersViewModel
    private var _binding : FragmentMyOrdersBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()
        val name = "aman"
        val cost = "14$"
        val subtitle = "hero is the machine and dishwasher"
        val item = MyOrder(0, name, subtitle,"",  cost)

        viewModel.getSavedBooks().observe(viewLifecycleOwner, Observer {order ->
            myAdapter.differ.submitList(order)

        })

        binding.profileBackButtonOrders.setOnClickListener{
            findNavController().navigate(
                R.id.action_nav_my_orders_to_nav_home
            )
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
            val navController = findNavController()
            navController.navigate(R.id.action_nav_my_orders_to_nav_home)
        }
        callback.isEnabled = true



    }

    private fun setUpRecyclerView() {
        myAdapter = OrdersAdapter(requireContext())
        binding.rvMyOrders.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

}