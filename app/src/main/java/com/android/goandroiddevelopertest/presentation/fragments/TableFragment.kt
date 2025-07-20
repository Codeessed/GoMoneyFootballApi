package com.android.goandroiddevelopertest.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.databinding.FragmentTableBinding
import com.android.goandroiddevelopertest.presentation.adapter.TableAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TableFragment : Fragment() {

    private var _binding: FragmentTableBinding? = null
    private val binding get() = _binding!!

    private lateinit var tableAdapter: TableAdapter
    private lateinit var tableRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTableRecycler()

    }

    private fun setupTableRecycler(){
        tableRecycler = binding.tableRv
        tableAdapter = TableAdapter(requireContext())
        tableRecycler.layoutManager = LinearLayoutManager(requireContext())
        tableRecycler.adapter = tableAdapter
        tableAdapter.differ.submitList((1..30).map { it.toString() })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}