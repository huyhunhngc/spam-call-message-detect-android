package com.dotsdev.idcaller.domain.vectorizer

import kotlin.math.log10

class TfidfVectorizer(
    private val dictWord: HashMap<String, Int>
) {
    private val dictWordLength = dictWord.size
    fun transform(document: String): DoubleArray {
        val idfDict = hashMapOf<String, Float>()
        dictWord.forEach { entry ->
            idfDict[entry.key] = entry.value.toFloat()
        }
        dictWord.forEach {
            val word = it.key
            val frequency = it.value
            idfDict[word] = log10(dictWordLength / frequency.toFloat())
        }
        return doubleArrayOf()
    }


    companion object {
        fun newInstance(): TfidfVectorizer {
            // TODO import dict word
            val dictWord = hashMapOf<String, Int>()
            return TfidfVectorizer(dictWord)
        }
    }
}