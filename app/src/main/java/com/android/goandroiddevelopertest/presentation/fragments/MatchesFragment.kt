package com.android.goandroiddevelopertest.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.BottomNavigationController
import com.android.goandroiddevelopertest.databinding.FragmentMatchesBinding
import com.android.goandroiddevelopertest.presentation.adapter.MatchesAdapter

class MatchesFragment : Fragment() {

    private var _binding: FragmentMatchesBinding? = null
    private val binding get() = _binding!!

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