package com.android.goandroiddevelopertest.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.goandroiddevelopertest.BottomNavigationController
import com.android.goandroiddevelopertest.FlowObserver.observer
import com.android.goandroiddevelopertest.OnCompetitionItemClickListener
import com.android.goandroiddevelopertest.R
import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.db.entities.Competition
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
    private lateinit var competitionRefresh: SwipeRefreshLayout
    private lateinit var competitionError: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCompetitionBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        competitionError = binding.competitionErrorContainer
        competitionRecycler = binding.competitionsRv
        competitionRefresh = binding.competitionsRefresh
        binding.competitionCollapsingToolbar.isTitleEnabled = false
        binding.competitionToolbar.setTitle("Competitions")
        competitionRefresh.setOnRefreshListener {
            goAndroidViewModel.refreshAllCompetitions()
        }
        binding.competitionRetryButton.setOnClickListener {
            goAndroidViewModel.refreshAllCompetitions()
        }
        hideBottomNavigationOnScroll()

        observer(goAndroidViewModel.allCompetitions){ allMatches ->
            competitionRefresh.isRefreshing = false
            competitionRecycler.isVisible = true
            competitionError.isVisible = false
            when(allMatches){
                is Resource.Success -> {
                    setupCompetitionRecycler(allMatches.data ?: emptyList())
                }
                is Resource.Error -> {
                    if(!allMatches.data.isNullOrEmpty()){
                        Toast.makeText(requireContext(), allMatches.message, Toast.LENGTH_SHORT).show()
                        setupCompetitionRecycler(allMatches.data)
                        return@observer
                    }
                    binding.competitionErrorMessage.text = allMatches.message ?: ""
                    competitionRecycler.isVisible = false
                    competitionError.isVisible = true
                }
                is Resource.Loading -> {
                    competitionRefresh.isRefreshing = true
                }
                else -> Unit
            }
        }

    }

    private fun setupCompetitionRecycler(competitionList: List<Competition>){
        competitionsAdapter = CompetitionsAdapter(requireContext(), this)
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

    override fun onCompetitionClick(competitionId: Int) {
        goAndroidViewModel.updatedSelectedCompetitionId(competitionId)
        findNavController().navigate(R.id.action_competitionsFragment_to_competitionDetailsFragment)
    }
}