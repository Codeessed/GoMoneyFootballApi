package com.android.goandroiddevelopertest.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.BottomNavigationController
import com.android.goandroiddevelopertest.databinding.FragmentCompetitionBinding
import com.android.goandroiddevelopertest.databinding.FragmentFixturesBinding
import com.android.goandroiddevelopertest.databinding.FragmentTableBinding
import com.android.goandroiddevelopertest.presentation.adapter.CompetitionsAdapter
import com.android.goandroiddevelopertest.presentation.adapter.MatchesAdapter

class FixturesFragment : Fragment() {

    private var _binding: FragmentFixturesBinding? = null
    private val binding get() = _binding!!

    private lateinit var competitionsAdapter: CompetitionsAdapter
    private lateinit var competitionRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFixturesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setupCompetitionRecycler()
//        hideBottomNavigationOnScroll()

    }

    private fun setupCompetitionRecycler(){
//        competitionRecycler = binding.competitionsRv
//        competitionsAdapter = CompetitionsAdapter(requireContext())
        competitionRecycler.layoutManager = LinearLayoutManager(requireContext())
        competitionRecycler.adapter = competitionsAdapter
        competitionsAdapter.differ.submitList((1..30).map { it.toString() })
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
}