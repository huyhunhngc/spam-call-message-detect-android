package com.dotsdev.idcaller.domain.vectorizer

import com.google.gson.Gson

class TfidfVectorizer {
    private var wordSetIdf: HashMap<String, Double> = hashMapOf()

    fun init(data: String) {
        wordSetIdf = Gson().fromJson(data, wordSetIdf::class.java)
    }

    fun transform(documents: List<String>): MutableList<DoubleArray> {
        val tfIdfVectors: MutableList<DoubleArray> = mutableListOf()
        for (doc in documents) {
            tfIdfVectors.add(transformDocument(doc))
        }
        return tfIdfVectors
    }


    fun transformDocument(document: String): DoubleArray {
        val vocabFrequency = hashMapOf<String, Double>()
        for (key in wordSetIdf.keys) {
            vocabFrequency[key] = 0.0
        }

        val documentVocab = document.split(" ").toSet()
        for (vocab in documentVocab) {
            if (vocab in wordSetIdf.keys) {
                vocabFrequency[vocab] = termFrequency(vocab, document)
            }
        }
        return vocabFrequency.values.toDoubleArray()
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
