package com.example.projekt

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.projekt.databinding.FragmentSubjectBinding
import com.example.projekt.placeholder.IMGNAMES
import com.example.projekt.placeholder.SubjectItem

/**
 * [RecyclerView.Adapter] that can display a [SubjectItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MySubjectRecyclerViewAdapter(
    private val values: List<SubjectItem>,
    private val eventListener: SubjectListListener
) : RecyclerView.Adapter<MySubjectRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentSubjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val resource = when(item.imageName){
            //rysunki w liscie przedmiotów
            IMGNAMES.MAT -> R.drawable.ic_architecture
            IMGNAMES.POL -> R.drawable.ic_import_contacts
            IMGNAMES.FIZ -> R.drawable.ic_science
            else -> R.drawable.ic_school
        }
        //holder.imgView = setImageResource(resource)
        holder.contentView.text = item.name

        holder.itemContainer.setOnClickListener{
            eventListener.onItemClick(position)
        }

        holder.itemContainer.setOnLongClickListener {
            eventListener.onItemLongClick(position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgView: ImageView = binding.subjectImg  //takie cos bylo .itemNumber
        val contentView: TextView = binding.subjectNameText            // takei cos .content
        val itemContainer: View = binding.root

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}