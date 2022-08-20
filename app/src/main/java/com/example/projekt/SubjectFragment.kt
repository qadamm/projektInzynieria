package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.projekt.databinding.FragmentSubjectListBinding
import com.example.projekt.placeholder.Subjects

/**
 * A fragment representing a list of Items.
 */
class SubjectFragment : Fragment(), SubjectListListener {
    private lateinit var binding: FragmentSubjectListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubjectListBinding.inflate(inflater, container, false)

        // Set the adapter
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = MySubjectRecyclerViewAdapter(Subjects.ITEMS, this@SubjectFragment)
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(R.id.action_subjectFragment_to_taskFragment)
    }

    override fun onItemLongClick(position: Int) {
        TODO("Not yet implemented")
    }
}