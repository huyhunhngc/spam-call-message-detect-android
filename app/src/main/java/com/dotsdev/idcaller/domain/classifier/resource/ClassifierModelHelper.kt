package com.dotsdev.idcaller.domain.classifier.resource

import android.content.Context
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.domain.classifier.ClassifierMessage
import com.dotsdev.idcaller.domain.vectorizer.TfidfVectorizer
import com.dotsdev.idcaller.utils.toStringValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClassifierModelHelper(
    private val classifierMessage: ClassifierMessage,
    private val tfidfVectorizer: TfidfVectorizer,
    private val context: Context
) {
    fun initRawResource() {
        CoroutineScope(Dispatchers.IO).launch {
            context.resources.openRawResource(R.raw.models).toStringValue()
                ?.let { classifierMessage.init(it) }
            context.resources.openRawResource(R.raw.vectorizer).toStringValue()
                ?.let { tfidfVectorizer.init(it) }
        }
    }
}