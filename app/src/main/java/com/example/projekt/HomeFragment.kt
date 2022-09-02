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

        binding.loggedAccountName.text = "Email użytkowanika: " + Token.email
        binding.testButton.setOnClickListener{ findNavController().navigate(R.id.action_homeFragment_to_subjectFragment) }
        binding.lufaButton.setOnClickListener{ startLufa() }
        binding.rankButton.setOnClickListener{ startRanked() }
        binding.profileButton.setOnClickListener{ findNavController().navigate(R.id.action_homeFragment_to_profileFragment) }
    }

    private fun startLufa() {
        val actionHomeFragmentToTaskFragment = HomeFragmentDirections.actionHomeFragmentToTaskFragment(lufa = true, subject = null, isRanked = false)
        findNavController().navigate(actionHomeFragmentToTaskFragment)
    }

    private fun startRanked() {
        val actionHomeFragmentToTaskFragment = HomeFragmentDirections.actionHomeFragmentToTaskFragment(lufa = false, numberOfQuestions = 10, subject = null, isRanked = true)
        findNavController().navigate(actionHomeFragmentToTaskFragment)
    }
}