package com.example.projekt.placeholder

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.example.projekt.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.ArrayList
import java.util.HashMap


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object Ranks {

    /**
     * An array of sample (placeholder) items.
     */
    lateinit var retrofit: Retrofit
    lateinit var apiService: ApiService

    val ITEMS: MutableList<RankItem> = ArrayList()

    private var rankList: List<Score>? = null

    init {

    }


    private fun addItem(item: RankItem) {
        ITEMS.add(item)


    }

    private fun createPlaceholderItem(position: Int, username: String, score: String): RankItem {
        return RankItem(position.toString(), username, score)
    }

    fun getAllScores() {
        //var items: List<Scores>? = emptyList()
        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)
        apiService.getAllScores().enqueue(object : Callback<Scores> {
            override fun onFailure(call: Call<Scores>, t: Throwable) {
                Log.e("error all scores!", t.message.toString())
            }

            override fun onResponse(call: Call<Scores>, response: Response<Scores>) {
                val allScores = response.body()!!.data
                rankList = allScores

                ITEMS.clear()
                rankList = rankList!!.sortedBy { it.score }.reversed()
                var username = ""
                var score = ""
                for (i in 1..rankList!!.size){
                    username = rankList!![i-1].User.username.toString()
                    score = rankList!![i-1].score.toString()
                    println(username)
                    println(score)
                    addItem(createPlaceholderItem(i, username, score ))
                }
            }

        })
        return
    }

    /**
     * A placeholder item representing a piece of content.
     */

}

data class RankItem(val id: String, val name: String, val score: String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun toString(): String = name
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RankItem> {
        override fun createFromParcel(parcel: Parcel): RankItem {
            return RankItem(parcel)
        }

        override fun newArray(size: Int): Array<RankItem?> {
            return arrayOfNulls(size)
        }
    }
}