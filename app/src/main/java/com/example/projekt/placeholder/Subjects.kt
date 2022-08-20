package com.example.projekt.placeholder

import android.os.Parcel
import android.os.Parcelable
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

    /**
     * A map of sample (placeholder) items, by ID.
     */

    private val COUNT = 10

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
        return SubjectItem(position.toString(), "Subject " + position, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A placeholder item representing a piece of content.
     */

}

data class SubjectItem(val id: String, val name: String, val details: String, val imageName: IMGNAMES = IMGNAMES.MAT): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        IMGNAMES.values()[parcel.readInt()]
    ) {
    }

    override fun toString(): String = name
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(details)
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