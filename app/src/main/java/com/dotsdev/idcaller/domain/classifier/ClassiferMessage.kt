package com.dotsdev.idcaller.domain.classifier

import android.util.Log
import com.dotsdev.idcaller.domain.classifier.model.Models
import com.google.gson.Gson
import kotlin.math.exp
import kotlin.math.max

class ClassifierMessage(
    private val hidden: Activation,
    private val output: Activation,
    private val network: Array<DoubleArray?>,
) {
    private var weights: Array<Array<DoubleArray>> = arrayOf(
        arrayOf(doubleArrayOf())
    )
    private var bias: Array<DoubleArray> = arrayOf(doubleArrayOf())

    fun init(data: String) {
        val models = Gson().fromJson(data, Models::class.java)
        weights = models.weights
        bias = models.bias
    }

    private fun compute(activation: Activation, v: DoubleArray?): DoubleArray {
        val layer = v?.size ?: return doubleArrayOf()
        when (activation) {
            Activation.RELU -> {
                for (i in 0 until layer) {
                    v[i] = max(0.0, v[i])
                }
            }
            Activation.SOFTMAX -> {
                var max = Double.NEGATIVE_INFINITY
                for (x in v) {
                    if (x > max) {
                        max = x
                    }
                }
                run {
                    for (i in 0 until layer) {
                        v[i] = exp(v[i] - max)
                    }
                }
                var sum = 0.0
                for (x in v) {
                    sum += x
                }
                for (i in 0 until layer) {
                    v[i] /= sum
                }
            }
        }
        return v
    }

    fun predict(neurons: DoubleArray?): Int {
        network[0] = neurons
        for (i in 0 until network.size - 1) {
            for (j in 0 until network[i + 1]!!.size) {
                kotlin.runCatching {
                    network[i + 1]!![j] = bias[i][j]
                }
                for (l in 0 until network[i]!!.size) {
                    kotlin.runCatching {
                        network[i + 1]!![j] += network[i]!![l] * weights[i][l][j]
                    }
                }
            }
            if (i + 1 < network.size - 1) {
                network[i + 1] = compute(hidden, network[i + 1])
            }
        }
        network[network.size - 1] = compute(
            output,
            network[network.size - 1]
        )

        return if (network[network.size - 1]!!.size == 1) {
            if (network[network.size - 1]!![0] > .5) {
                1
            } else 0
        } else {
            var classIdx = 0
            for (i in 0 until network[network.size - 1]!!.size) {
                classIdx =
                    if (network[network.size - 1]!![i] > network[network.size - 1]!![classIdx]) i else classIdx
            }
            classIdx
        }
    }

    companion object {
        fun newInstance(): ClassifierMessage {
            val layers = intArrayOf(32, 32, 2)
            val network: Array<DoubleArray?> = arrayOfNulls(layers.size + 1)
            val l = layers.size
            for (i in 0 until l) {
                network[i + 1] = DoubleArray(layers[i])
            }
            return ClassifierMessage(
                Activation.RELU,
                Activation.SOFTMAX,
                network,
            )
        }
    }
}

enum class Activation(val value: String) {
    RELU("relu"),
    SOFTMAX("softmax")
}
