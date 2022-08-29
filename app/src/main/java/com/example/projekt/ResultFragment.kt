package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projekt.databinding.FragmentHomeBinding
import com.example.projekt.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resultPoints.text = args.correctAnswers.toString() + "/" + args.numOfTasks.toString()
        binding.resultScore.text = ((args.correctAnswers.toDouble() / args.numOfTasks)*100).toInt().toString() + "%"
        binding.returnHomeButton.setOnClickListener { findNavController().popBackStack(R.id.homeFragment, false) }
        binding.showAnsButton.setOnClickListener { findNavController().popBackStack(R.id.taskFragment, false) }
    }
}