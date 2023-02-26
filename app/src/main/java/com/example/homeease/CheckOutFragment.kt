package com.example.homeease

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.navigation.fragment.findNavController
import com.example.homeease.databinding.FragmentCheckOutBinding
import com.example.homeease.model.MyOrder
import com.example.homeease.viewmodel.OrdersViewModel
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wallet.*
import java.text.SimpleDateFormat
import java.util.*


class CheckOutFragment : Fragment() {

    private var _binding : FragmentCheckOutBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectedDate: Calendar

    private lateinit var viewModel: OrdersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel = (activity as MainActivity).viewModel

        val serviceName = arguments?.getString(HomeFragment.SERVICE_TITLE)
        binding.ServicseSelectedAtCheckOut.text = serviceName

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        selectedDate = Calendar.getInstance()

        calendarView.maxDate = System.currentTimeMillis()
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

            selectedDate.apply {
                set(year, month, dayOfMonth)
            }
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
             val dateString = dateFormat.format(selectedDate.time)

            val otp = generateOTP()
            val item = MyOrder(subtitle = serviceName!!, date = dateString, otp = otp)


            binding.btnPayAfterService.setOnClickListener{
                viewModel.bookedOrders(item)
                findNavController().navigate(
                    R.id.action_checkOutFragment_to_nav_my_orders
                )
            }



        }



        }

    fun generateOTP(): String {
        val random = Random()
        val otp = random.nextInt(10000)
        return String.format("%04d", otp)
    }

}