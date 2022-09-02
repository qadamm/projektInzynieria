package com.example.projekt.placeholder

import android.os.Parcel
import android.os.Parcelable
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

    val ITEMS: MutableList<RankItem> = ArrayList()

    private val rankList: List<String> = mutableListOf("Gracz 1", "Gracz 2", "Gracz 3", "Gracz 4", "Gracz 5", "Gracz 6", "Gracz 7", "Gracz 8", "Gracz 9", "Gracz 10")

    private val COUNT = rankList.size

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i))
        }
    }

    private fun addItem(item: RankItem) {
        ITEMS.add(item)
    }

    private fun createPlaceholderItem(position: Int): RankItem {
        return RankItem(position.toString(), "Gracz " + position, 100 + position)
    }

    /**
     * A placeholder item representing a piece of content.
     */

}

data class RankItem(val id: String, val name: String, val score: Int): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun toString(): String = name
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(score)
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