package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.projekt.databinding.FragmentRankListBinding
import com.example.projekt.placeholder.Ranks
import com.example.projekt.placeholder.Subjects

/**
 * A fragment representing a list of Items.
 */
class RankFragment : Fragment(), RankListListener {

    private lateinit var binding: FragmentRankListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankListBinding.inflate(inflater, container, false)

        // Set the adapter
        with(binding.rankList) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyRankRecyclerViewAdapter(Ranks.ITEMS, this@RankFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Ranks.getAllScores()

        binding.backProfileButton.setOnClickListener { findNavController().popBackStack(R.id.profileFragment, false) }
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int) {
        TODO("Not yet implemented")
    }

}