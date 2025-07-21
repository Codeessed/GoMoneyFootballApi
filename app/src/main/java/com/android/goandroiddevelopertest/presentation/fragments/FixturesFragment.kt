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
import com.android.goandroiddevelopertest.BottomNavigationController
import com.android.goandroiddevelopertest.FlowObserver.observer
import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.databinding.FragmentCompetitionBinding
import com.android.goandroiddevelopertest.databinding.FragmentFixturesBinding
import com.android.goandroiddevelopertest.databinding.FragmentTableBinding
import com.android.goandroiddevelopertest.db.entities.Match
import com.android.goandroiddevelopertest.presentation.adapter.CompetitionsAdapter
import com.android.goandroiddevelopertest.presentation.adapter.MatchesAdapter
import com.android.goandroiddevelopertest.viewmodel.GoAndroidViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixturesFragment : Fragment() {

    private var _binding: FragmentFixturesBinding? = null
    private val binding get() = _binding!!

    private lateinit var matchesAdapter: MatchesAdapter
    private lateinit var matchesRecycler: RecyclerView

    private val goAndroidViewModel: GoAndroidViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFixturesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer(goAndroidViewModel.competitionMatches){ competitionMatches ->
            when(competitionMatches){
                is Resource.Success -> {
                    setupMatchRecycler(competitionMatches.data ?: emptyList())
                }
                is Resource.Error -> {
                    setupMatchRecycler(competitionMatches.data ?: emptyList())
                    Toast.makeText(requireContext(), competitionMatches.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                }
                else -> Unit
            }
        }

    }

    private fun setupMatchRecycler(list: List<Match>){
        matchesRecycler = binding.fixturesRv
        matchesAdapter = MatchesAdapter(requireContext())
        matchesRecycler.layoutManager = LinearLayoutManager(requireContext())
        matchesRecycler.adapter = matchesAdapter
        matchesAdapter.differ.submitList(list)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}