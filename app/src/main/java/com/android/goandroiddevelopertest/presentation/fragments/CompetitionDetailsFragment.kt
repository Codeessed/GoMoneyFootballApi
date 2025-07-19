package com.android.goandroiddevelopertest.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.android.goandroiddevelopertest.databinding.FragmentCompetitionDetailsBinding
import com.android.goandroiddevelopertest.presentation.adapter.CompetitionDetailsViewpagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CompetitionDetailsFragment: Fragment() {
    private var _binding: FragmentCompetitionDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var competitionDetailsViewpager: ViewPager2
    private lateinit var competitionDetailsAdapter: CompetitionDetailsViewpagerAdapter
    private lateinit var competitionDetailsTab: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompetitionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = binding.competitionDetailsToolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Competition Title"
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(requireContext(), android.R.color.black))


        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        setupVaultTransViewpager()
    }

    private fun setupVaultTransViewpager(){
        competitionDetailsViewpager = binding.competitionDetailsViewpager
        competitionDetailsAdapter = CompetitionDetailsViewpagerAdapter(this)
        competitionDetailsViewpager.adapter = competitionDetailsAdapter
        competitionDetailsTab = binding.competitionDetailsTab
        TabLayoutMediator(competitionDetailsTab, competitionDetailsViewpager){tab, position ->
            tab.text = getCompetitionTabTitle(position)
        }.attach()
    }

    private fun getCompetitionTabTitle(position: Int):String?{
        return when(position){
            0->"Table"
            1->"Fixtures"
            2->"Team"
            else -> null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}