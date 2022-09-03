package com.example.projekt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projekt.databinding.FragmentHomeBinding
import com.example.projekt.databinding.FragmentResultBinding
import com.example.projekt.placeholder.Ranks
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private val args: ResultFragmentArgs by navArgs()
    lateinit var retrofit: Retrofit
    lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        putResult()
        Ranks.getAllScores()
        binding.resultPoints.text = args.correctAnswers.toString() + "/" + args.numOfTasks.toString()
        binding.resultScore.text = ((args.correctAnswers.toDouble() / args.numOfTasks)*100).toInt().toString() + "%"
        binding.returnHomeButton.setOnClickListener { findNavController().popBackStack(R.id.homeFragment, false) }
        binding.showAnsButton.setOnClickListener { findNavController().popBackStack(R.id.taskFragment, false) }
    }

    fun putResult() {
        val request = ScoreRequest()
        request.score = args.correctAnswers
        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)
        apiService.putResult(Token.myToken, request).enqueue(object: Callback<ScoreResponse> {
            override fun onFailure(call: Call<ScoreResponse>, t: Throwable) {
                Log.e("error put result", t.message.toString())
                //Toast.makeText(getActivity(), "Niepoprawne dane logowania!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ScoreResponse>, response: Response<ScoreResponse>) {
                val temp = response.body()
                if (temp != null){
                    println(temp)
                }
                else{
                    Toast.makeText(getActivity(), "Niepoprawne dane logowania!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}