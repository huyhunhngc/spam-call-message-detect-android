package com.dotsdev.idcaller.domain.detectSpam

import com.dotsdev.idcaller.domain.classifier.ClassifierMessage
import com.dotsdev.idcaller.domain.vectorizer.TfidfVectorizer

class DetectSpamMessage(
    private val tfidfVectorizer: TfidfVectorizer,
    private val classifierMessage: ClassifierMessage
) {
    operator fun invoke(message: String): Boolean {
        val vector = tfidfVectorizer.transformDocument(message)
        if (vector.second) {
            return false
        }
        val classId = classifierMessage.predict(vector.first)
        return classId != 0
    }
}