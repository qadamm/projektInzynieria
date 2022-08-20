package com.example.projekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.projekt.databinding.ActivityMainBinding
//import com.example.projekt.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var retrofit: Retrofit
    lateinit var apiService: ApiService
    //@RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
//        getAllQuestions()
//        login()
//        register()
    }

//    fun login() {
//        val request = LoginRequest()
//        request.email = "fifi@fifi.pl"
//        request.password = "fifi"
//        retrofit = ApiClient.getRetrofit()
//        apiService = retrofit.create(ApiService::class.java)
//        apiService.login(request).enqueue(object: Callback<LoginResponse>{
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                Log.e("error login", t.message.toString())
//            }
//
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                val user = response.body()
//                Log.e("Login token", user!!.token.toString())
//            }
//        })
//    }
//
//    fun register() {
//        val request = RegisterRequest()
//        request.username = "ahhahaahaaa"
//        request.email = "ahahaaaaa@email.ru"
//        request.password = "ahhaa"
//        retrofit = ApiClient.getRetrofit()
//        apiService = retrofit.create(ApiService::class.java)
//        apiService.register(request).enqueue(object: Callback<RegisterResponse>{
//            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
//                Log.e("error register!", t.message.toString())
//            }
//
//            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
//                val user = response.body()
//                Log.e("Zarejestrowany email", user!!.email.toString())
//                Log.e("Zarejestrowany id", user!!.userId.toString())
//                Log.e("Zarejestrowany username", user!!.username.toString())
//            }
//        })
//    }

    private fun getAllQuestions(): MutableList<Question> {
        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)
        val givenList = mutableListOf<Question>()
      CoroutineScope(Dispatchers.IO).launch {
          val response = apiService.getQuestions()
          withContext(Dispatchers.Main){
              if (response.isSuccessful){
                  val items = response.body()?.data
                  Log.e("questionnf", items.toString())
                  if (items != null){
                      for (i in 0 until items.count()){
                          givenList.add(items[i])
                          val id = items[i].answerA?:"N/A"
                          //val id = items[i].questionn?.answerA ?:"N/A"
                          println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                          println(items[i].toString())
                          println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                          Log.e("quesDXtsssssionnf", items[i].toString())
                          //Log.e("quesDXtionnf", id)
                      }

                  }
              }
              else{
                  Log.e("RETROFIT_ERROR", response.code().toString())
              }
          }
      }

        return givenList

    }






    //@RequiresApi(Build.VERSION_CODES.M)
//    private fun initApiCall(){
//        retrofit = ApiClient.getRetrofit()
//        apiService = retrofit.create(ApiService::class.java)
//        val questionCall: Call<List<Question>> = apiService.getQuestions()
//        questionCall.enqueue(object : Callback<List<Question>>{
//        override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
//                if (response.isSuccessful) {
//                    val question = response.body()!!
//                    println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
//                    println(question)
//                    binding.apply {
//                        question.apply {
//                            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
//                            //println(question.answerA)
//                            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
//                            //animalName.text = animal.author
//
//                        }
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
//            }
//        })
//        }
    }




