package com.mindbody.assessment.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.mindbody.assessment.R
import com.mindbody.assessment.databinding.ActivityWorldRegionsBinding

class WorldRegionsActivity : AppCompatActivity() {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.world_regions_nav_host) as NavHostFragment).navController
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var activityWorldRegionsBinding: ActivityWorldRegionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityWorldRegionsBinding = DataBindingUtil.setContentView(
            this@WorldRegionsActivity,
            R.layout.activity_world_regions
        )

        setSupportActionBar(activityWorldRegionsBinding.toolbar)

        /*
        * Attaches toolbar with navigation controller to keep toolbar synced between page load and navigation events.
        * Like backpress or toolbar titles.
        * Appbar configuration
        * */
        appBarConfiguration = AppBarConfiguration(navController.graph, null)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}