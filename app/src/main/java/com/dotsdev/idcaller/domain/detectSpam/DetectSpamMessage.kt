package com.dotsdev.idcaller.domain.detectSpam

import com.dotsdev.idcaller.domain.classifier.ClassifierMessage
import com.dotsdev.idcaller.domain.vectorizer.TfidfVectorizer

class DetectSpamMessage(
    private val tfidfVectorizer: TfidfVectorizer,
    private val classifierMessage: ClassifierMessage
) {
    operator fun invoke(message: String): Boolean {
        val classId = classifierMessage.predict(tfidfVectorizer.transformDocument(message))
        return classId != 0
    }
}