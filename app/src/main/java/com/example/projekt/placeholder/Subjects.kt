package com.example.projekt.placeholder

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.example.projekt.R
import java.util.ArrayList

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object Subjects {

    /**
     * An array of sample (placeholder) items.
     */

    val ITEMS: MutableList<SubjectItem> = ArrayList()

    val subList = listOf<String>("Matematyka", "Fizyka", "Chemia")

    private val COUNT = subList.size

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i))
        }
    }

    private fun addItem(item: SubjectItem) {
        ITEMS.add(item)
    }

    private fun createPlaceholderItem(position: Int): SubjectItem {
//        val stringArray = R.array.subjectArray
//        Log.e("stringARRAYAAYAYAYAYA", stringArray.toString())
        return SubjectItem(position.toString(), "Subject " + position)
    }

    /**
     * A placeholder item representing a piece of content.
     */

}

data class SubjectItem(val id: String, val name: String, val imageName: IMGNAMES = IMGNAMES.MAT): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        IMGNAMES.values()[parcel.readInt()]
    ) {
    }

    override fun toString(): String = name
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubjectItem> {
        override fun createFromParcel(parcel: Parcel): SubjectItem {
            return SubjectItem(parcel)
        }

        override fun newArray(size: Int): Array<SubjectItem?> {
            return arrayOfNulls(size)
        }
    }
}

enum class IMGNAMES {
    MAT, POL, FIZ //Nazwy przedmiotów pod rysunki
}