package com.example.projekt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.projekt.databinding.FragmentProfileBinding
import com.example.projekt.databinding.FragmentTaskBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    lateinit var retrofit: Retrofit
    lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profilePicture.setImageResource(R.drawable.avatar)
        binding.showRankingButton.setOnClickListener { findNavController().navigate(R.id.action_profileFragment_to_rankFragment) }
        binding.backHomeButton.setOnClickListener { findNavController().popBackStack(R.id.homeFragment, false) }
    }

    private fun getUserResult() {
        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)
        apiService.getResult(Token.myToken).enqueue(object: Callback<ScoreResponse> {
            override fun onFailure(call: Call<ScoreResponse>, t: Throwable) {
                Log.e("error put result", t.message.toString())
                //Toast.makeText(getActivity(), "Niepoprawne dane logowania!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ScoreResponse>, response: Response<ScoreResponse>) {
                val temp = response.body()
                if (temp != null){
                    println(temp.user)
                    println(temp.score)
                    //userToken = temp.token.toString()
                    //Token.myToken = userToken.toString()
                    //Token.email = request.email.toString()
                    // findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    //odnosimy sie do tokenu Token.myToken
                }
                else{
                    Toast.makeText(getActivity(), "Niepoprawne dane logowania!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}