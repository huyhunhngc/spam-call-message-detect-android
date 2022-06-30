package com.dotsdev.idcaller.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.data.broadcast.SmsReceiveRepository
import com.dotsdev.idcaller.data.model.Message
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
    var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startSmsService(this)
        with(binding) {
            setContentView(root)
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
        GlobalScope.launch {
            resources.openRawResource(R.raw.models).toStringValue()
                ?.let { classifierMessage.init(it) }
            resources.openRawResource(R.raw.vectorizer).toStringValue()
                ?.let { tfidfVectorizer.init(it) }
        }
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
