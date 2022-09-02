package com.example.projekt

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Questions(
    var data: List<Question>?

)

data class Questions2(
    @SerializedName("data")
    var data: Question

)

data class Question(
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

data class Scores(
    var data: List<Score>?

)

data class Score (
    var UserID: Int? = null,
    var User: User,
    var score: Int? = null
)


data class User (
    var ID: Int? = null,
    var CreatedAt: String? = null,
    var UpdatedAt: String? = null,
    var DeletedAt: String? = null,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null
)


class ScoreResponse (
    @SerializedName("score")
    @Expose
    var score: Int? = null,

    @SerializedName("user")
    @Expose
    var user: String? = null
)


class ScoreRequest (
    @SerializedName("score")
    @Expose
    var score: Int? = null
)