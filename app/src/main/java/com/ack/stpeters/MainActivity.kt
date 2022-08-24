package com.ack.stpeters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ack.stpeters.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(){

  private lateinit var binding: ActivityMainBinding
  private lateinit var appBarConfiguration: AppBarConfiguration
  private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        val navView: BottomNavigationView = binding.navView

        //Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.subTitle.text = when (destination.id) {
                R.id.navigation_home -> resources.getString(R.string.member_registration)
                R.id.navigation_addMember -> resources.getString(R.string.personal_info)
                R.id.navigation_addMemberTwo -> resources.getString(R.string.church_info)
                R.id.navigation_addMemberThree -> resources.getString(R.string.career_info)
                R.id.navigation_settings -> resources.getString(R.string.settings)

                else -> resources.getString(R.string.app_name)
            }
        }



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_addMember, R.id.navigation_settings
            )
        )

        navView.setupWithNavController(navController)

        //appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        setupActionBarWithNavController(navController,appBarConfiguration)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}