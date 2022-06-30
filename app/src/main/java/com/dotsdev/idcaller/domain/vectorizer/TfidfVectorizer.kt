package com.dotsdev.idcaller.domain.vectorizer

import android.util.Log
import com.google.gson.Gson
import kotlin.math.log10

class TfidfVectorizer {
    private var dictWord: HashMap<String, Int> = hashMapOf()
    private var dictWordLength = dictWord.size

    fun init(data: String) {
        dictWord = Gson().fromJson(data, dictWord::class.java)
        dictWordLength = dictWord.size
    }

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
        // TODO implement vectorizer
        return doubleArrayOf()
    }

    companion object {
        fun newInstance(): TfidfVectorizer {
            return TfidfVectorizer()
        }
    }
}
