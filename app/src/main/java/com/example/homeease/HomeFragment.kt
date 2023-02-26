package com.example.homeease

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeease.adapters.PujaServicesAdapter
import com.example.homeease.adapters.ServicesAdapter
import com.example.homeease.databinding.FragmentHomeBinding
import com.example.homeease.model.Service
import com.example.homeease.model.Services
import com.google.gson.Gson


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var myAdapter: ServicesAdapter
    private lateinit var myPujaServicesAdapter: PujaServicesAdapter
    private var list0fServices = listOf<Service>()
    lateinit var services : Services

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val assetManager =  requireActivity().assets
        val inputStream = assetManager.open("most_used_services.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val gson  = Gson()
        services = gson.fromJson(jsonString, Services::class.java)

        myAdapter = ServicesAdapter(requireContext(), services.services)
        binding.rvFragmentHome.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false)
        binding.rvFragmentHome.adapter = myAdapter

        binding.rvFragmentHomePujaServices

        myPujaServicesAdapter = PujaServicesAdapter(requireContext(),services.puja_services)
        binding.rvFragmentHomePujaServices.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvFragmentHomePujaServices.adapter = myPujaServicesAdapter

        myAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putString(SERVICE_TITLE, it.title)
                putString(SERVICE_IMG, it.icon)

            }

            findNavController().navigate(
                R.id.action_nav_home_to_serviceDetailFragment,bundle
            )
        }

        myPujaServicesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString(SERVICE_TITLE, it.title)
                putString(SERVICE_IMG, it.icon)
            }

            findNavController().navigate(
                R.id.action_nav_home_to_serviceDetailFragment,bundle
            )
        }
    }

    companion object{
        const val SERVICE_TITLE = "service"
        const val SERVICE_IMG = "img"

    }


}