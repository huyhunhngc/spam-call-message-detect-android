package com.dotsdev.idcaller.domain.vectorizer

import com.dotsdev.idcaller.utils.toVietnamese
import com.google.gson.Gson

class TfidfVectorizer {
    private var wordSetIdf: HashMap<String, Double> = hashMapOf()

    fun init(data: String) {
        wordSetIdf = Gson().fromJson(data, wordSetIdf::class.java)
    }

    fun transform(documents: List<String>): MutableList<DoubleArray> {
        val tfIdfVectors: MutableList<DoubleArray> = mutableListOf()
        for (doc in documents) {
            tfIdfVectors.add(transformDocument(doc).first)
        }
        return tfIdfVectors
    }


    fun transformDocument(document: String): Pair<DoubleArray, Boolean> {
        val vocabFrequency = hashMapOf<String, Double>()
        for (key in wordSetIdf.keys) {
            vocabFrequency[key] = 0.0
        }
        val documentVocab = document.toVietnamese().toSet()
        var unknownWord = 0.0
        for (vocab in documentVocab) {
            if (vocab in wordSetIdf.keys) {
                vocabFrequency[vocab] = termFrequency(vocab, document)
            } else {
                unknownWord += 1.0
            }
        }

        return vocabFrequency.values.toDoubleArray() to
                (unknownWord / documentVocab.size > 0.5)
    }

    private fun termFrequency(term: String, doc: String): Double {
        val docSplit = doc.split(" ")
        return docSplit.count { it == term }.toDouble() / docSplit.size
    }

    companion object {
        fun newInstance(): TfidfVectorizer {
            return TfidfVectorizer()
        }
    }
}
