package com.android.goandroiddevelopertest.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.goandroiddevelopertest.presentation.fragments.FixturesFragment
import com.android.goandroiddevelopertest.presentation.fragments.TableFragment
import com.android.goandroiddevelopertest.presentation.fragments.TeamsFragment

class CompetitionDetailsViewpagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TableFragment()
            1 -> FixturesFragment()
            else -> TeamsFragment()
        }
    }

}