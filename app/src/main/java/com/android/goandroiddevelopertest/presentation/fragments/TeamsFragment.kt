package com.android.goandroiddevelopertest.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.FlowObserver.observer
import com.android.goandroiddevelopertest.OnTeamItemClickListener
import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.databinding.FragmentTeamBinding
import com.android.goandroiddevelopertest.db.entities.Team
import com.android.goandroiddevelopertest.presentation.SquadBottomSheet
import com.android.goandroiddevelopertest.presentation.adapter.TeamAdapter
import com.android.goandroiddevelopertest.viewmodel.GoAndroidViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsFragment : Fragment(), OnTeamItemClickListener {

    private var _binding: FragmentTeamBinding? = null
    private val binding get() = _binding!!

    private lateinit var teamAdapter: TeamAdapter
    private lateinit var teamRecycler: RecyclerView

    private val goAndroidViewModel: GoAndroidViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer(goAndroidViewModel.allTeams){ allTeams ->
//            competitionRefresh.isRefreshing = false
//            competitionRecycler.isVisible = true
//            competitionError.isVisible = false
            when(allTeams){
                is Resource.Success -> {
                    setupCompetitionRecycler(allTeams.data ?: emptyList())
                }
                is Resource.Error -> {
//                    if(!allMatches.data.isNullOrEmpty()){
                        Toast.makeText(requireContext(), allTeams.message, Toast.LENGTH_SHORT).show()
                        setupCompetitionRecycler(allTeams.data ?: emptyList())
//                        return@observer
//                    }
//                    binding.competitionErrorMessage.text = allMatches.message ?: ""
//                    competitionRecycler.isVisible = false
//                    competitionError.isVisible = true
                }
                is Resource.Loading -> {
//                    competitionRefresh.isRefreshing = true
                }
                else -> Unit
            }
        }

    }





    private fun setupCompetitionRecycler(teamList: List<Team>){
        teamRecycler = binding.teamRv
        teamAdapter = TeamAdapter(requireContext(), this)
        teamRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        teamRecycler.adapter = teamAdapter
        teamAdapter.differ.submitList(teamList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTeamClick(team: Team) {
        goAndroidViewModel.updatedSelectedTeam(team)
        val bottomSheet = SquadBottomSheet()
        bottomSheet.show(requireActivity().supportFragmentManager, "")
    }
}