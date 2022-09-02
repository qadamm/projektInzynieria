package com.example.projekt

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.projekt.databinding.FragmentSubjectBinding
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
        val resource = when(item.name){
            //rysunki w liscie przedmiotów
            "Matematyka" -> R.drawable.ic_maths
            "Język Polski" -> R.drawable.ic_polish
            "Chemia" -> R.drawable.ic_chemistry
            "Geografia" -> R.drawable.ic_geography
            "Język Angielski" -> R.drawable.ic_english_speaking_icon
            "Fizyka" -> R.drawable.ic_physics
            "Biologia" -> R.drawable.ic_biology
            else -> R.drawable.ic_school
        }
        holder.imgView.setImageResource(resource)
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