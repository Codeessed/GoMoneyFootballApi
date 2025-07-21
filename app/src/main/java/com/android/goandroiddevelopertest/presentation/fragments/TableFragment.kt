package com.android.goandroiddevelopertest.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.FlowObserver.observer
import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.databinding.FragmentTableBinding
import com.android.goandroiddevelopertest.db.entities.Table
import com.android.goandroiddevelopertest.presentation.adapter.TableAdapter
import com.android.goandroiddevelopertest.viewmodel.GoAndroidViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TableFragment : Fragment() {

    private var _binding: FragmentTableBinding? = null
    private val binding get() = _binding!!

    private lateinit var tableAdapter: TableAdapter
    private lateinit var tableRecycler: RecyclerView

    private val goAndroidViewModel: GoAndroidViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer(goAndroidViewModel.allStandings){ allStandings ->
            when(allStandings){
                is Resource.Success -> {
                    setupTableRecycler(allStandings.data ?: emptyList())
                }
                is Resource.Error -> {
                    setupTableRecycler(allStandings.data ?: emptyList())
                    Toast.makeText(requireContext(), allStandings.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                }
                else -> Unit
            }
        }

    }

    private fun setupTableRecycler(list: List<Table>){
        tableRecycler = binding.tableRv
        tableAdapter = TableAdapter(requireContext())
        tableRecycler.layoutManager = LinearLayoutManager(requireContext())
        tableRecycler.adapter = tableAdapter
        tableAdapter.differ.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}