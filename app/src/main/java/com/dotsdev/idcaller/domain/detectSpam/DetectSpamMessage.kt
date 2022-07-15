package com.dotsdev.idcaller.domain.detectSpam

import android.util.Log
import com.dotsdev.idcaller.domain.classifier.ClassifierMessage
import com.dotsdev.idcaller.domain.vectorizer.TfidfVectorizer
import com.google.gson.Gson

class DetectSpamMessage(
    private val tfidfVectorizer: TfidfVectorizer,
    private val classifierMessage: ClassifierMessage
) {
    operator fun invoke(message: String): Boolean {
        val vector = tfidfVectorizer.transformDocument(message)
//        if (vector.second) {
//            return true
//        }
        val classId = classifierMessage.predict(vector.first)
        return classId != 1
    }
}