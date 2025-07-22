package com.android.goandroiddevelopertest.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.goandroiddevelopertest.BottomNavigationController
import com.android.goandroiddevelopertest.FlowObserver.observer
import com.android.goandroiddevelopertest.data.model.MatchModel
import com.android.goandroiddevelopertest.databinding.FragmentMatchesBinding
import com.android.goandroiddevelopertest.db.entities.Match
import com.android.goandroiddevelopertest.presentation.adapter.FixturesAdapter
import com.android.goandroiddevelopertest.presentation.adapter.MatchesAdapter
import com.android.goandroiddevelopertest.viewmodel.GoAndroidViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesFragment : Fragment() {

    private var _binding: FragmentMatchesBinding? = null
    private val binding get() = _binding!!

    private val goAndroidViewModel: GoAndroidViewModel by activityViewModels()
    private lateinit var matchAdapter: FixturesAdapter
    private lateinit var matchRecycler: RecyclerView
    private lateinit var matchesRefresh: SwipeRefreshLayout
    private lateinit var matchesError: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        matchesError = binding.matchesErrorMessage
        matchRecycler = binding.matchesRv
        matchesRefresh = binding.matchesRefresh
        binding.matchesCollapsingToolbar.isTitleEnabled = false
        binding.matchesToolbar.setTitle("Today's Fixtures")
        matchesRefresh.setOnRefreshListener {
            goAndroidViewModel.getAllMatches()
        }
        hideBottomNavigationOnScroll()




        observer(goAndroidViewModel.allMatchesState){ allMatchesState ->
            matchesRefresh.isRefreshing = false
            matchRecycler.isVisible = true
            matchesError.isVisible = false
            when(allMatchesState){
                is GoAndroidViewModel.GoEvent.AllMatchesSuccessEvent -> {
                    if (allMatchesState.result.matches.isEmpty()){
                        matchesError.text = "No match fixtures for today, swipe to refresh"
                        matchRecycler.isVisible = false
                        matchesError.isVisible = true
                    }
                    setupMatchRecycler(allMatchesState.result.matches)
                }
                is GoAndroidViewModel.GoEvent.Error -> {
                    matchesError.text = allMatchesState.error
                    matchRecycler.isVisible = false
                    matchesError.isVisible = true
                }
                is GoAndroidViewModel.GoEvent.Loading -> {
                    matchesRefresh.isRefreshing = true
                }
                else -> goAndroidViewModel.getAllMatches()
            }
        }

    }

    private fun setupMatchRecycler(list: List<MatchModel>){
        matchAdapter = FixturesAdapter(requireContext())
        matchRecycler.layoutManager = LinearLayoutManager(requireContext())
        matchRecycler.adapter = matchAdapter
        matchAdapter.differ.submitList(list)
    }

    private fun hideBottomNavigationOnScroll() {
        matchRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
}