package com.android.goandroiddevelopertest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.goandroiddevelopertest.FlowObserver.observer
import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.databinding.SquadBottomsheetBinding
import com.android.goandroiddevelopertest.db.entities.Squad
import com.android.goandroiddevelopertest.db.entities.Team
import com.android.goandroiddevelopertest.presentation.adapter.SquadAdapter
import com.android.goandroiddevelopertest.presentation.adapter.TeamAdapter
import com.android.goandroiddevelopertest.viewmodel.GoAndroidViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SquadBottomSheet : BottomSheetDialogFragment() {

    private var _binding: SquadBottomsheetBinding? = null
    private val binding get() = _binding!!

    private val goAndroidViewModel: GoAndroidViewModel by activityViewModels()

    private lateinit var squadAdapter: SquadAdapter
    private lateinit var squadRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = SquadBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.squadSheetClose.setOnClickListener {
            dismiss()
        }

        observer(goAndroidViewModel.teamSquads){ teamSquads ->
            setupCompetitionRecycler(teamSquads)
        }

        observer(goAndroidViewModel.selectedTeam){ selectedTeam ->
            binding.squadTeamName.text = selectedTeam.shortName
            binding.squadTeamImg.load(selectedTeam.crest){
                crossfade(true)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.let { bottomSheet ->
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            behavior.isDraggable = true
        }
    }

    private fun setupCompetitionRecycler(squadList: List<Squad>){
        squadRecycler = binding.squadRecycler
        squadAdapter = SquadAdapter(requireContext())
        squadRecycler.layoutManager = LinearLayoutManager(requireContext())
        squadRecycler.adapter = squadAdapter
        squadAdapter.differ.submitList(squadList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}