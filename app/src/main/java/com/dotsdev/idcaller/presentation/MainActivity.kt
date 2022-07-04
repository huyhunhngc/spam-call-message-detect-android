package com.dotsdev.idcaller.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.appSettingState.AppTheme
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.ActivityMainBinding
import com.dotsdev.idcaller.domain.classifier.ClassifierMessage
import com.dotsdev.idcaller.domain.vectorizer.TfidfVectorizer
import com.dotsdev.idcaller.service.NotificationService
import com.dotsdev.idcaller.utils.toStringValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding by viewBindings {
        ActivityMainBinding.inflate(it)
    }
    private val viewModel: MainViewModel by viewModel()
    private val classifierMessage: ClassifierMessage by inject()
    private val tfidfVectorizer: TfidfVectorizer by inject()
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
        startSmsService(this)
        GlobalScope.launch {
            resources.openRawResource(R.raw.models).toStringValue()
                ?.let { classifierMessage.init(it) }
            resources.openRawResource(R.raw.vectorizer).toStringValue()
                ?.let { tfidfVectorizer.init(it) }
        }
        super.onCreate(savedInstanceState)
        binding.apply {
            setContentView(root)
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
    }

    override fun recreate() {
        finish()
        startActivity(intent)
    }

    private fun startSmsService(context: Context) {
        val intentService = Intent(context, NotificationService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }
}
