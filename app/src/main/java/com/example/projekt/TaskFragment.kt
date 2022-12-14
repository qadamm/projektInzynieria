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
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var currentTaskNum = 1
    private var maxTaskNum = 10
    private var correctAnswers = 0
    private var questionHinted = -1
    private var questionHintedAns = 5
    private var isHinted = false
    private var isEnded = false
    private val args: TaskFragmentArgs by navArgs()

    lateinit var retrofit: Retrofit
    lateinit var apiService: ApiService

    private var questionsList: List<Question>? = null
    private var Answers = IntArray(maxTaskNum){9}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(args.lufa){
            getRandomQuestion()
        }else{
            if (args.year == 1) {
                getSelectedQuestions(
                    args.numberOfQuestions,
                    (args.subject?.name ?: "Matematyka"),
                    2010,
                    2022
                )
            } else {
                getSelectedQuestions(
                    args.numberOfQuestions,
                    (args.subject?.name ?: "Matematyka"),
                    args.year,
                    args.year
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        //getAllQuestions()
        if (isEnded) setAnswers()
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
        for (i in 1..maxTaskNum ) {
            if (Answers[i-1] == questionsList!![i - 1].correctAnswer!!.toInt()){
                correctAnswers += 1
            }
        }
        val actionTaskFragmentToResultFragment = TaskFragmentDirections.actionTaskFragmentToResultFragment(maxTaskNum, correctAnswers)
        findNavController().navigate(actionTaskFragmentToResultFragment)
        currentTaskNum = 1
        isEnded = true
        setAnswers()
        binding.ansButton.isClickable = false
        binding.ansButton2.isClickable = false
        binding.ansButton3.isClickable = false
        binding.ansButton4.isClickable = false

    }

    private fun setAnswers() {
        if (questionsList!![currentTaskNum - 1].ImageLink != "") {
            binding.taskImage.visibility = View.VISIBLE
            Picasso.get().load(questionsList!![currentTaskNum - 1].ImageLink).into(binding.taskImage)
        } else {
            binding.taskImage.visibility = View.GONE
        }
        binding.currentSubject.text = questionsList!![currentTaskNum - 1].subject
        binding.taskDescription.text = questionsList!![currentTaskNum - 1].question
        binding.mathRenderA.text = questionsList!![currentTaskNum - 1].answerA
        binding.mathRenderB.text = questionsList!![currentTaskNum - 1].answerB
        binding.mathRenderC.text = questionsList!![currentTaskNum - 1].answerC
        binding.mathRenderD.text = questionsList!![currentTaskNum - 1].answerD
        isAnswerEnable()
        isHintEnable()


        if((currentTaskNum - 1) == questionHinted){
            removeHinted(questionHintedAns)
        }
        if(Answers[currentTaskNum - 1 ] != 9){
            setAnswer(Answers[currentTaskNum - 1])
        }
        if(isEnded && Answers[currentTaskNum -1 ] != 9) {
            setAnswer(Answers[currentTaskNum - 1])
            if (args.isRanked){
                setRankedAnswer()
            }
        }
        if(isEnded && Answers[currentTaskNum -1 ] == 9) {
            if (args.isRanked){
                setRankedAnswer()
            }else{
                when(questionsList!![currentTaskNum - 1].correctAnswer?.toInt() ?: 5) {
                    0 -> {
                        binding.ansButton.setBackgroundColor(Color.parseColor("#ff8800"))
                        binding.ansButton.setTextColor(Color.WHITE)
                    }
                    1 -> {
                        binding.ansButton2.setBackgroundColor(Color.parseColor("#ff8800"))
                        binding.ansButton2.setTextColor(Color.WHITE)
                    }
                    2 -> {
                        binding.ansButton3.setBackgroundColor(Color.parseColor("#ff8800"))
                        binding.ansButton3.setTextColor(Color.WHITE)
                    }
                    3 -> {
                        binding.ansButton4.setBackgroundColor(Color.parseColor("#ff8800"))
                        binding.ansButton4.setTextColor(Color.WHITE)
                    }
                }
            }
        }
    }

    private fun setRankedAnswer(){
        when(questionsList!![currentTaskNum - 1].correctAnswer?.toInt() ?: 5) {
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

    private fun isAnswerEnable() {
        if(Answers[currentTaskNum-1] != 9 || isEnded) {
            binding.ansButton.isClickable = false
            binding.ansButton2.isClickable = false
            binding.ansButton3.isClickable = false
            binding.ansButton4.isClickable = false
        } else {
            binding.ansButton.isClickable = true
            binding.ansButton2.isClickable = true
            binding.ansButton3.isClickable = true
            binding.ansButton4.isClickable = true
        }
    }

    private fun setAnswer(position: Int) {
        if (args.isRanked) {
            when (position) {
                0 -> checkCorrectRanked(0)
                1 -> checkCorrectRanked(1)
                2 -> checkCorrectRanked(2)
                3 -> checkCorrectRanked(3)
            }
        }else {
            when (position) {
                0 -> checkCorrect(0)
                1 -> checkCorrect(1)
                2 -> checkCorrect(2)
                3 -> checkCorrect(3)
            }
        }
        Answers[currentTaskNum - 1] = position
        isHintEnable()
        binding.ansButton.isClickable = false
        binding.ansButton2.isClickable = false
        binding.ansButton3.isClickable = false
        binding.ansButton4.isClickable = false


    }

    private fun checkCorrectRanked(position: Int){
        when(position){
            0 -> {
                binding.ansButton.setBackgroundColor(Color.parseColor("#CCAA22"))
                binding.ansButton.setTextColor(Color.WHITE)
            }
            1 -> {
                binding.ansButton2.setBackgroundColor(Color.parseColor("#CCAA22"))
                binding.ansButton2.setTextColor(Color.WHITE)
            }
            2 -> {
                binding.ansButton3.setBackgroundColor(Color.parseColor("#CCAA22"))
                binding.ansButton3.setTextColor(Color.WHITE)
            }
            3 -> {
                binding.ansButton4.setBackgroundColor(Color.parseColor("#CCAA22"))
                binding.ansButton4.setTextColor(Color.WHITE)
            }
        }

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

    private fun isHintEnable() {
        if(Answers[currentTaskNum -1 ] != 9 || isHinted || isEnded) binding.hintButton.visibility = View.GONE
        else binding.hintButton.visibility = View.VISIBLE
    }

    private fun getHint() {
        isHinted = true;
        binding.hintButton.visibility = View.GONE
        var BadAnswers  = mutableListOf<Int>(0,1,2,3)
        BadAnswers.remove(questionsList!![currentTaskNum -1 ].correctAnswer?.toInt())
        var randomIndex = BadAnswers.random()
        questionHintedAns = randomIndex
        questionHinted = currentTaskNum - 1
        removeHinted(randomIndex)
    }

    private fun removeHinted(Index: Int) {
        when(Index){
            0 -> {
                binding.ansButton.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral90))
                binding.ansButton.setTextColor(resources.getColor(R.color.material_dynamic_neutral90))
                binding.ansButton.isClickable = false
            }
            1 -> {
                binding.ansButton2.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral90))
                binding.ansButton2.setTextColor(resources.getColor(R.color.material_dynamic_neutral90))
                binding.ansButton2.isClickable = false
            }
            2 -> {
                binding.ansButton3.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral90))
                binding.ansButton3.setTextColor(resources.getColor(R.color.material_dynamic_neutral90))
                binding.ansButton3.isClickable = false
            }
            3 -> {
                binding.ansButton4.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral90))
                binding.ansButton4.setTextColor(resources.getColor(R.color.material_dynamic_neutral90))
                binding.ansButton4.isClickable = false
            }
        }
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
        if(isEnded){
            binding.endTestButton.visibility = View.GONE
        }
        clearAnswerColors()
        setAnswers()
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
            }

        })
        return items
    }

    private fun getSelectedQuestions(quantity: Int, subject: String, first: Int, last: Int): List<Question>? {
        var items: List<Question>? = emptyList()
        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)
        apiService.getSelectedQuestions(quantity, subject, first, last).enqueue(object : Callback<Questions> {
            override fun onFailure(call: Call<Questions>, t: Throwable) {
                Log.e("error register!", t.message.toString())
            }

            override fun onResponse(call: Call<Questions>, response: Response<Questions>) {
                val user = response.body()?.data
                questionsList = user!!.shuffled()
                setAnswers()
            }

        })
        return items
    }

    private fun getRandomQuestion(): List<Question>? {
        var items: List<Question>? = emptyList()
        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)
        apiService.getRandomQuestion().enqueue(object : Callback<Questions2> {
            override fun onFailure(call: Call<Questions2>, t: Throwable) {
                Log.e("error register!", t.message.toString())

            }

            override fun onResponse(call: Call<Questions2>, response: Response<Questions2>) {
                val user = response.body()!!.data
                questionsList = listOf(Question(ID=user.ID, CreatedAt=user.CreatedAt, UpdatedAt=user.UpdatedAt, DeletedAt=user.DeletedAt, question=user.question, answerA=user.answerA, answerB=user.answerB, answerC=user.answerC, answerD=user.answerD, correctAnswer=user.correctAnswer, subject=user.subject, year=user.year, ImageLink=user.ImageLink))
                Log.e("DANE!", user.toString())
                setAnswers()
            }

        })
        return items
    }



}