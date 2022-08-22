package com.example.projekt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projekt.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var currentTaskNum = 1
    private var maxTaskNum = 10
    private val args: TaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        maxTaskNum = args.numberOfQuestions
        if(maxTaskNum >= 100) binding.currentTask.textSize = 18F

        val isLufa: Boolean = args.lufa

        if(isLufa){
            binding.prevButton.visibility = View.GONE
            binding.nextButton.visibility = View.GONE
        }

        binding.currentTask.text = "$currentTaskNum/$maxTaskNum"
        binding.homeButton.setOnClickListener{backHome()}
        binding.prevButton.setOnClickListener{prevQuestion()}
        binding.nextButton.setOnClickListener{nextQuestion()}
        binding.hintButton.setOnClickListener{getHint()}
        binding.ansButton.setOnClickListener{setAnswerA()}
        binding.ansButton2.setOnClickListener{setAnswerB()}
        binding.ansButton3.setOnClickListener{setAnswerC()}
        binding.ansButton4.setOnClickListener{setAnswerD()}



    }

    private fun setAnswerD() {
        TODO("Not yet implemented")
    }

    private fun setAnswerC() {
        TODO("Not yet implemented")
    }

    private fun setAnswerB() {
        TODO("Not yet implemented")
    }

    private fun setAnswerA() {
        TODO("Not yet implemented")
    }

    private fun getHint() {
        TODO("Not yet implemented")
    }

    private fun nextQuestion() {
        currentTaskNum += 1
        binding.currentTask.text = "$currentTaskNum/$maxTaskNum"
        if(binding.prevButton.visibility == View.GONE) binding.prevButton.visibility = View.VISIBLE
        //View.GONE ? View.VISIBLE cos takiego
        if(currentTaskNum == maxTaskNum) binding.nextButton.visibility = View.GONE
    }

    private fun prevQuestion() {
        currentTaskNum -= 1
        binding.currentTask.text = "$currentTaskNum/$maxTaskNum"
        if(binding.nextButton.visibility == View.GONE) binding.nextButton.visibility = View.VISIBLE
        //View.GONE ? View.VISIBLE cos takiego
        if(currentTaskNum == 1) binding.prevButton.visibility = View.GONE
    }

    private fun backHome() {
        findNavController().popBackStack(R.id.homeFragment, false)
    }
}