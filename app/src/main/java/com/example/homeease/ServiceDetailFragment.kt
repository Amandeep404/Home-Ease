package com.example.homeease

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.homeease.HomeFragment.Companion.SERVICE_TITLE
import com.example.homeease.databinding.FragmentServiceDetailBinding


class ServiceDetailFragment : Fragment() {

    private var _binding : FragmentServiceDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentServiceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val serviceName = arguments?.getString(SERVICE_TITLE)
        binding.tvCompanyServiceSelected.text = serviceName


        binding.btnCheckOut.setOnClickListener{
            val bundle = Bundle().apply {
                putString(SERVICE_TITLE, serviceName)
            }

            findNavController().navigate(
                R.id.action_serviceDetailFragment_to_checkOutFragment, bundle
            )
        }
    }

}