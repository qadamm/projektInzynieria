package com.example.projekt

import com.google.gson.annotations.SerializedName


data class Questions(
    var data: List<Question>?

)

data class Questions2(
    @SerializedName("data")
    var data: Question

)

data class Question(
    //val questionn: Question?
    var ID: Int? = null,
    var CreatedAt: String? = null,
    var UpdatedAt: String? = null,
    var DeletedAt: String? = null,
    var question: String? = null,
    var answerA: String? = null,
    var answerB: String? = null,
    var answerC: String? = null,
    var answerD: String? = null,
    var correctAnswer: String? = null,
    var subject: String? = null,
    var year: Int? = null,
    var ImageLink: String? = null

)

//data class Question (
//    var ID: Int? = null,
//    var CreatedAt: String? = null,
//    var UpdatedAt: String? = null,
//    var DeletedAt: String? = null,
//    var question: String? = null,
//    var answerA: String? = null,
//    var answerB: String? = null,
//    var answerC: String? = null,
//    var answerD: String? = null,
//    var correctAnswer: String? = null,
//    var subject: String? = null,
//    var year: Int? = null
//
//
//    )