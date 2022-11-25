package com.dotsdev.idcaller.presentation

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.dotsdev.idcaller.appSettingState.AppTheme
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.ActivityMainBinding
import com.dotsdev.idcaller.service.NotificationService
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val binding by viewBindings {
        ActivityMainBinding.inflate(it)
    }
    private val viewModel: MainViewModel by viewModel()
    private val appTheme: AppTheme by inject()
    var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        appTheme.obs.observeForever {
            val theme = when (it) {
                false -> AppCompatDelegate.MODE_NIGHT_NO
                true -> AppCompatDelegate.MODE_NIGHT_YES
            }
            AppCompatDelegate.setDefaultNightMode(theme)
            viewModel.onSaveModeNight(it)
        }
        super.onCreate(savedInstanceState)
        startSmsService(this)
        binding.apply {
            setContentView(root)
            lifecycleOwner = this@MainActivity
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.clear()
    }

    private fun startSmsService(context: Context) {
        val intentService = Intent(context, NotificationService::class.java)
        if (!isMyServiceRunning(NotificationService::class.java)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intentService)
            } else {
                context.startService(intentService)
            }
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        return (getSystemService(ACTIVITY_SERVICE) as ActivityManager)
            .getRunningServices(Int.MAX_VALUE)
            .any { serviceClass.name == it.service.className }
    }
}
