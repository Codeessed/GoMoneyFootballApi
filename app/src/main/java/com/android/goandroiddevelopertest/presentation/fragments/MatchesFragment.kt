package com.android.goandroiddevelopertest.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.BottomNavigationController
import com.android.goandroiddevelopertest.FlowObserver.observer
import com.android.goandroiddevelopertest.databinding.FragmentMatchesBinding
import com.android.goandroiddevelopertest.presentation.adapter.MatchesAdapter
import com.android.goandroiddevelopertest.viewmodel.GoAndroidViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesFragment : Fragment() {

    private var _binding: FragmentMatchesBinding? = null
    private val binding get() = _binding!!

    private val goAndroidViewModel: GoAndroidViewModel by activityViewModels()
    private lateinit var matchAdapter: MatchesAdapter
    private lateinit var matchRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.matchesCollapsingToolbar.isTitleEnabled = false
        binding.matchesToolbar.setTitle("Today's Fixtures")
        setupMatchRecycler()
        hideBottomNavigationOnScroll()


//        observer(goAndroidViewModel.allMatches){ allMatches ->
//            when(allMatches){
//                is GoAndroidViewModel.GoEvent.MatchesSuccessEvent -> {
//                   Log.d("all_matches", allMatches.matchesResult)
//                }
//                is GoAndroidViewModel.GoEvent.Error -> {
//                    Log.d("all_matches", allMatches.errorText)
//                }
//                is GoAndroidViewModel.GoEvent.Empty -> {
//                    goAndroidViewModel.getAllMatches()
//                }
//                else -> {
//                    Log.d("all_matches", allMatches.toString())
//                }
//            }
//        }
    }

    private fun setupMatchRecycler(){
        matchRecycler = binding.matchesRv
        matchAdapter = MatchesAdapter(requireContext())
        matchRecycler.layoutManager = LinearLayoutManager(requireContext())
        matchRecycler.adapter = matchAdapter
        matchAdapter.differ.submitList((1..30).map { it.toString() })
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