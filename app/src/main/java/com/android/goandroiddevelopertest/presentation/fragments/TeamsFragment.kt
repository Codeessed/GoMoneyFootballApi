package com.android.goandroiddevelopertest.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.OnTeamItemClickListener
import com.android.goandroiddevelopertest.databinding.FragmentTeamBinding
import com.android.goandroiddevelopertest.presentation.adapter.TeamAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsFragment : Fragment(), OnTeamItemClickListener {

    private var _binding: FragmentTeamBinding? = null
    private val binding get() = _binding!!

    private lateinit var teamAdapter: TeamAdapter
    private lateinit var teamRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCompetitionRecycler()

    }

    private fun setupCompetitionRecycler(){
        teamRecycler = binding.teamRv
        teamAdapter = TeamAdapter(requireContext(), this)
        teamRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        teamRecycler.adapter = teamAdapter
        teamAdapter.differ.submitList((1..100).map { it.toString() })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTeamClick(position: Int) {

    }
}