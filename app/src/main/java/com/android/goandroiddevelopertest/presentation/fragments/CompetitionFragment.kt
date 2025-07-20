package com.android.goandroiddevelopertest.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.BottomNavigationController
import com.android.goandroiddevelopertest.FlowObserver.observer
import com.android.goandroiddevelopertest.OnCompetitionItemClickListener
import com.android.goandroiddevelopertest.R
import com.android.goandroiddevelopertest.data.model.CompetitionModel
import com.android.goandroiddevelopertest.data.model.CompetitionResponseModel
import com.android.goandroiddevelopertest.databinding.FragmentCompetitionBinding
import com.android.goandroiddevelopertest.presentation.adapter.CompetitionsAdapter
import com.android.goandroiddevelopertest.viewmodel.GoAndroidViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompetitionFragment : Fragment(), OnCompetitionItemClickListener {

    private var _binding: FragmentCompetitionBinding? = null
    private val binding get() = _binding!!

    private val goAndroidViewModel: GoAndroidViewModel by activityViewModels()

    private lateinit var competitionsAdapter: CompetitionsAdapter
    private lateinit var competitionRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCompetitionBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        competitionRecycler = binding.competitionsRv
        binding.competitionCollapsingToolbar.isTitleEnabled = false
        binding.competitionToolbar.setTitle("Competitions")
        hideBottomNavigationOnScroll()

        observer(goAndroidViewModel.allMatches){ allMatches ->
            when(allMatches){
                is GoAndroidViewModel.GoEvent.MatchesSuccessEvent -> {
                    setupCompetitionRecycler(allMatches.matchesResult.competitions)
                }
                is GoAndroidViewModel.GoEvent.Error -> {
                    Toast.makeText(requireContext(), allMatches.errorText, Toast.LENGTH_SHORT).show()
                }
                is GoAndroidViewModel.GoEvent.Empty -> {
                    goAndroidViewModel.getAllMatches()
                }
                else -> Unit
            }
        }

    }

    private fun setupCompetitionRecycler(competitionList: List<CompetitionModel>){
        competitionsAdapter = CompetitionsAdapter(requireContext(), this)
        competitionRecycler.layoutManager = LinearLayoutManager(requireContext())
        competitionRecycler.adapter = competitionsAdapter
        competitionsAdapter.differ.submitList(competitionList)
    }

    private fun hideBottomNavigationOnScroll() {
        competitionRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    (activity as? BottomNavigationController)?.hideBottomNavigation()
                } else if (dy < 0) {
                    (activity as? BottomNavigationController)?.showBottomNavigation()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCompetitionClick(position: Int) {
        findNavController().navigate(R.id.action_competitionsFragment_to_competitionDetailsFragment)
    }
}