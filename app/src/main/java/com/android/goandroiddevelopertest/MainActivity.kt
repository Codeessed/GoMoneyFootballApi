package com.android.goandroiddevelopertest

import android.os.Bundle
import android.view.animation.AnimationUtils
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.isVisible
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.goandroiddevelopertest.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationController {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navGraph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        bottomNav = binding.bottomNav
        val navHost = supportFragmentManager.findFragmentById(R.id.main_navHost_container)
                as NavHostFragment
        val navController = navHost.findNavController()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.matchesFragment -> bottomNav.isVisible = true
                R.id.competitionsFragment -> bottomNav.isVisible = true
                else ->  bottomNav.isVisible = false
            }
        }

        bottomNav.setupWithNavController(navController)

        navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.matchesFragment)

        navController.graph = navGraph

    }

    override fun showBottomNavigation() {
        if (bottomNav.isShown) {
            return
        }
        val animationSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        bottomNav.startAnimation(animationSlideUp)
        bottomNav.isVisible = true
    }

    override fun hideBottomNavigation() {
        if (bottomNav.isShown) {
            val animationSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)
            bottomNav.startAnimation(animationSlideDown)
            bottomNav.isVisible = false
        }
    }

}