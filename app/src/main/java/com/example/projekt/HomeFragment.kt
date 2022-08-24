package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.projekt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.testButton.setOnClickListener{startTest()}
        binding.lufaButton.setOnClickListener{findNavController().navigate(R.id.action_homeFragment_to_taskFragment)}
        binding.rankButton.setOnClickListener{startRanked()}
        binding.profileButton.setOnClickListener{showProfile()}
    }

    private fun showProfile() {
        TODO("Not yet implemented")
    }

    private fun startRanked() {
        TODO("Not yet implemented")
    }

    private fun startTest() {
        findNavController().navigate(R.id.action_homeFragment_to_subjectFragment)
    }
}