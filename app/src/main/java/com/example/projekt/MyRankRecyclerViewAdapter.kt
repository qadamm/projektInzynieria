package com.example.projekt

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.projekt.databinding.FragmentRankBinding
import com.example.projekt.placeholder.RankItem

/**
 * [RecyclerView.Adapter] that can display a [RankItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyRankRecyclerViewAdapter(
    private val values: List<RankItem>,
    private val eventListener: RankListListener
) : RecyclerView.Adapter<MyRankRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentRankBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val resource = R.drawable.ic_account_circle
        holder.imgView.setImageResource(resource)
        holder.nameView.text = item.name

        holder.itemContainer.setOnClickListener{
            eventListener.onItemClick(position)
        }

        holder.itemContainer.setOnLongClickListener {
            eventListener.onItemLongClick(position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentRankBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgView: ImageView = binding.rankAvatar
        val nameView: TextView = binding.rankItemName
        val itemContainer: View = binding.root

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }

}