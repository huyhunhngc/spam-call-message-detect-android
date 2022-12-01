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
        val lowerDocument = document.lowercase()
        val documentVocab = lowerDocument.split(" ").toSet()

        var unknownWord = 0.0
        for (vocab in documentVocab) {
            if (vocab in wordSetIdf.keys) {
                vocabFrequency[vocab] = wordSetIdf[vocab]!! * termFrequency(vocab, lowerDocument)
            } else {
                unknownWord += 1.0
            }
        }

        val vocabFrequencyList: MutableList<Pair<String, Double>> = mutableListOf()
        vocabFrequency.forEach {
            vocabFrequencyList.add(Pair(it.key, it.value))
        }
        vocabFrequencyList.sortBy { it.first }

        return vocabFrequencyList.map{ it.second }.toDoubleArray() to
                (unknownWord / documentVocab.size > 0.7)
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
