package com.dotsdev.idcaller.presentation.template

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.data.model.NavigationGraphInfo

class NormalAppbarActivity : AppCompatActivity() {
    private val args: NormalAppbarActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        setupNavigation(args.navigationGraphInfo)
    }

    private fun setupNavigation(graphInfo: NavigationGraphInfo) {
        (supportFragmentManager
            .findFragmentById(R.id.nav_fragment) as NavHostFragment).let { navHost ->
            val graph = navHost.navController.navInflater.inflate(graphInfo.graphId)
                .apply {
                    setStartDestination(graphInfo.startDestinationId)
                }
            navHost.navController.run {
                val toolbar: Toolbar = findViewById(R.id.toolbar)
                setGraph(graph, bundleOf(DATA_FROM_KEY to graphInfo.dataFrom))
                setSupportActionBar(toolbar)
                toolbar.setNavigationOnClickListener {
                    if (navHost.childFragmentManager.backStackEntryCount > 0) {
                        findNavController(R.id.nav_fragment).popBackStack()
                    } else {
                        finish()
                    }
                }
                setupActionBarWithNavController(
                    this,
                    AppBarConfiguration.Builder(graph).build()
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        const val DATA_FROM_KEY = "data_from_key"
    }
}
