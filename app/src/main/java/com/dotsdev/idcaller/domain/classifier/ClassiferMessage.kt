package com.dotsdev.idcaller.domain.classifier

import kotlin.math.exp
import kotlin.math.max

class ClassifierMessage(
    private val hidden: Activation,
    private val output: Activation,
    private val network: Array<DoubleArray?>,
    private val weights: Array<Array<DoubleArray>>,
    private val bias: Array<DoubleArray>
) {
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
            for (j in 0 until (network[i + 1]?.size ?: 0)) {
                network[i + 1]?.set(j, bias[i][j])
                for (l in 0 until (network[i]?.size ?: 0)) {
                    try {
                        network[i + 1]?.set(j, network[i]!![l] * weights[i][l][j])
                    } catch (e: NullPointerException) {
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
        return if (network[network.size - 1]?.size == 1) {
            if ((network[network.size - 1]?.get(0) ?: 0.0) > .5) {
                1
            } else {
                0
            }
        } else {
            var classIdx = 0
            for (i in 0..(network[network.size - 1]?.size ?: 0)) {
                classIdx = try {
                    if (network[network.size - 1]!![i] > network[network.size - 1]!![classIdx]) i else classIdx
                } catch (e: NullPointerException) {
                    0
                }
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
            // TODO: export weights
            val weights = arrayOf(
                arrayOf(doubleArrayOf())
            )
            // TODO: export bias
            val bias = arrayOf(doubleArrayOf())
            return ClassifierMessage(
                Activation.RELU,
                Activation.SOFTMAX,
                network,
                weights,
                bias
            )
        }
    }
}

enum class Activation(val value: String) {
    RELU("relu"),
    SOFTMAX("softmax")
}
