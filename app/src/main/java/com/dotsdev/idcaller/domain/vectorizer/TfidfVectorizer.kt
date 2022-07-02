package com.dotsdev.idcaller.domain.vectorizer

import android.util.Log
import com.google.gson.Gson
import kotlin.math.log10

class TfidfVectorizer {
    private var wordSet: HashMap<String, Int> = hashMapOf()
    private var wordSetLength = wordSet.size
    private var wordSetIdf: HashMap<String, Double> = hashMapOf()

    fun init(data: String) {
        wordSet = Gson().fromJson(data, wordSet::class.java)
        wordSetLength = wordSet.size
        wordSet.map {
            wordSetIdf[it.key] = log10(wordSetLength.toDouble() / it.value.toDouble())
        }
    }

    private fun computeTFIDF(tfDoc: MutableMap<String, Double>): DoubleArray {
        tfDoc.forEach {
            tfDoc[it.key] = it.value * (wordSetIdf[it.key] ?: 1.0)
        }
        return tfDoc.values.toDoubleArray()
    }

    fun transform(document: String): DoubleArray {
        val words = document.split(" ")
        val idfDict = wordSet.keys.associateWith { 0 }.toMutableMap()
        val idfDictDouble = wordSet.keys.associateWith { 0.0 }.toMutableMap()
        words.forEach {
            kotlin.runCatching {
                idfDict[it] = (idfDict[it] ?: 0).plus(1)
            }
        }

        idfDict.forEach {
            idfDictDouble[it.key] = it.value.toDouble() / words.size
        }
        return computeTFIDF(idfDictDouble)
    }

    companion object {
        fun newInstance(): TfidfVectorizer {
            return TfidfVectorizer()
        }
    }
}
