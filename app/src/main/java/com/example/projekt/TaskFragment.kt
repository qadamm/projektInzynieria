package com.example.projekt

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projekt.databinding.FragmentTaskBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var currentTaskNum = 1
    private var correctAnswers = 0
    private var maxTaskNum = 10
    private var isEnded = false
    private var Answers = IntArray(maxTaskNum){9}
    private val args: TaskFragmentArgs by navArgs()



    lateinit var retrofit: Retrofit
    lateinit var apiService: ApiService
    private var mSelectedOptionPosition : String = ""
    private var questionsList: List<Question>? = listOf(Question(ID=1, CreatedAt="2022-08-03T12:53:46Z", UpdatedAt="2022-08-03T12:53:46Z", DeletedAt=null, question="Ladowanie pytania...", answerA="CA", answerB="CB", answerC="CC", answerD="CD", correctAnswer="0", subject="Polski", year=2021),
        Question(ID=2, CreatedAt="2022-08-03T12:53:46Z", UpdatedAt="2022-08-03T12:53:46Z", DeletedAt=null, question="Tresc Pytania2xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", answerA="CA", answerB="CB", answerC="CC", answerD="CD", correctAnswer="3", subject="Polski", year=2021),
        Question(ID=3, CreatedAt="2022-08-03T12:53:46Z", UpdatedAt="2022-08-03T12:33:46Z", DeletedAt=null, question="Tresc Pytania3xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", answerA="CA", answerB="CB", answerC="CC", answerD="CD", correctAnswer="2", subject="Angielski", year=2022))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        getAllQuestions()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        maxTaskNum = args.numberOfQuestions

        val isLufa: Boolean = args.lufa
        if(isLufa){
            binding.prevButton.visibility = View.GONE
            binding.nextButton.visibility = View.GONE
        }

        binding.currentTask.text = "$currentTaskNum/$maxTaskNum"
        binding.homeButton.setOnClickListener{findNavController().popBackStack(R.id.homeFragment, false)}
        binding.prevButton.setOnClickListener{prevQuestion()}
        binding.nextButton.setOnClickListener{nextQuestion()}
        binding.hintButton.setOnClickListener{getHint()}
        binding.ansButton.setOnClickListener{setAnswer(0)}
        binding.ansButton2.setOnClickListener{setAnswer(1)}
        binding.ansButton3.setOnClickListener{setAnswer(2)}
        binding.ansButton4.setOnClickListener{setAnswer(3)}
        binding.endTestButton.setOnClickListener{endTest()}


    }

    private fun endTest() {
        val actionTaskFragmentToResultFragment = TaskFragmentDirections.actionTaskFragmentToResultFragment(maxTaskNum, correctAnswers)
        findNavController().navigate(actionTaskFragmentToResultFragment)
        currentTaskNum = 1
        isEnded = true
        setAnswers()



    }

    private fun setAnswers() {
        binding.currentSubject.text = questionsList!![currentTaskNum - 1].subject
        binding.taskDescription.text = questionsList!![currentTaskNum - 1].question
        binding.ansButton.text = "A: " + questionsList!![currentTaskNum - 1].answerA
        binding.ansButton2.text = "B: " + questionsList!![currentTaskNum - 1].answerB
        binding.ansButton3.text = "C: " + questionsList!![currentTaskNum - 1].answerC
        binding.ansButton4.text = "D: " + questionsList!![currentTaskNum - 1].answerD

        Log.e("skonczony?", isEnded.toString())
        if(Answers[currentTaskNum - 1 ] != 9){
            setAnswer(Answers[currentTaskNum -1 ])
        }
        if(isEnded) {
            setAnswer(Answers[currentTaskNum -1 ])
        }

    }

    private fun setAnswer(position: Int) {
        when(position){
            0 -> checkCorrect(0)
            1 -> checkCorrect(1)
            2 -> checkCorrect(2)
            3 -> checkCorrect(3)
        }
        Answers[currentTaskNum -1 ] = position
        binding.ansButton.isClickable = false
        binding.ansButton2.isClickable = false
        binding.ansButton3.isClickable = false
        binding.ansButton4.isClickable = false

    }

    private fun checkCorrect(position: Int) {
        if(position == questionsList!![currentTaskNum - 1].correctAnswer?.toInt() ?: 5){
            when(position){
                0 -> {
                    binding.ansButton.setBackgroundColor(Color.parseColor("#008800"))
                    binding.ansButton.setTextColor(Color.WHITE)
                }
                1 -> {
                    binding.ansButton2.setBackgroundColor(Color.parseColor("#008800"))
                    binding.ansButton2.setTextColor(Color.WHITE)
                }
                2 -> {
                    binding.ansButton3.setBackgroundColor(Color.parseColor("#008800"))
                    binding.ansButton3.setTextColor(Color.WHITE)
                }
                3 -> {
                    binding.ansButton4.setBackgroundColor(Color.parseColor("#008800"))
                    binding.ansButton4.setTextColor(Color.WHITE)
                }
            }
        } else {
            when(position){
                0 -> {
                    binding.ansButton.setBackgroundColor(Color.RED)
                    binding.ansButton.setTextColor(Color.WHITE)
                }
                1 -> {
                    binding.ansButton2.setBackgroundColor(Color.RED)
                    binding.ansButton2.setTextColor(Color.WHITE)
                }
                2 -> {
                    binding.ansButton3.setBackgroundColor(Color.RED)
                    binding.ansButton3.setTextColor(Color.WHITE)
                }
                3 -> {
                    binding.ansButton4.setBackgroundColor(Color.RED)
                    binding.ansButton4.setTextColor(Color.WHITE)
                }
            }
            when(questionsList!![currentTaskNum - 1].correctAnswer?.toInt()){
                0 -> {
                    binding.ansButton.setBackgroundColor(Color.parseColor("#008800"))
                    binding.ansButton.setTextColor(Color.WHITE)
                }
                1 -> {
                    binding.ansButton2.setBackgroundColor(Color.parseColor("#008800"))
                    binding.ansButton2.setTextColor(Color.WHITE)
                }
                2 -> {
                    binding.ansButton3.setBackgroundColor(Color.parseColor("#008800"))
                    binding.ansButton3.setTextColor(Color.WHITE)
                }
                3 -> {
                    binding.ansButton4.setBackgroundColor(Color.parseColor("#008800"))
                    binding.ansButton4.setTextColor(Color.WHITE)
                }
            }
        }
    }

    private fun getHint() {
        TODO("Not yet implemented")
    }

    private fun prevQuestion() {
        currentTaskNum -= 1
        binding.currentTask.text = "$currentTaskNum/$maxTaskNum"
        if(binding.nextButton.visibility == View.GONE){
            binding.nextButton.visibility = View.VISIBLE
            binding.endTestButton.visibility = View.GONE
        }
        if(currentTaskNum == 1) binding.prevButton.visibility = View.GONE
        clearAnswerColors()
        setAnswers()
    }

    private fun nextQuestion() {
        currentTaskNum += 1
        binding.currentTask.text = "$currentTaskNum/$maxTaskNum"
        if(binding.prevButton.visibility == View.GONE) binding.prevButton.visibility = View.VISIBLE
        if(currentTaskNum == maxTaskNum){
            binding.nextButton.visibility = View.GONE
            binding.endTestButton.visibility = View.VISIBLE
        }
        clearAnswerColors()
        setAnswers()
        if(Answers[currentTaskNum - 1] == 9) {
            binding.ansButton.isClickable = true
            binding.ansButton2.isClickable = true
            binding.ansButton3.isClickable = true
            binding.ansButton4.isClickable = true
        }
    }

    private fun clearAnswerColors() {
        binding.ansButton.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral90))
        binding.ansButton2.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral90))
        binding.ansButton3.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral90))
        binding.ansButton4.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral90))
        binding.ansButton.setTextColor(Color.BLACK)
        binding.ansButton2.setTextColor(Color.BLACK)
        binding.ansButton3.setTextColor(Color.BLACK)
        binding.ansButton4.setTextColor(Color.BLACK)
    }

    private fun getAllQuestions(): List<Question>? {
        //var pytanie = findViewById<TextView>(R.id.taskDescription)
        var items: List<Question>? = emptyList()
        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)
        apiService.getQuestions().enqueue(object : Callback<Questions> {
            override fun onFailure(call: Call<Questions>, t: Throwable) {
                Log.e("error register!", t.message.toString())
            }

            override fun onResponse(call: Call<Questions>, response: Response<Questions>) {
                val user = response.body()?.data
                questionsList = user
                setAnswers()

//                for (i in 0 until user!!.count()) {
//                    //val temp =  user!![i].question?:"N/A"
//                    //pytanie.text = user!![i].question?.toString()
//                    println(user!![i].question?.toString())
//                    break
//                }
//                Log.e("Zarejestrowany email", items!!.toString())
            }

        })

        // Log.e("Zarejestrowany email", items!!.toString())
        return items
    }
}