package com.dotsdev.idcaller.domain.classifier.model

import com.google.gson.annotations.SerializedName

data class Models(
    @SerializedName("weights")
    val weights: Array<Array<DoubleArray>> = arrayOf(
        arrayOf(
            doubleArrayOf()
        )
    ),
    @SerializedName("bias")
    val bias: Array<DoubleArray> = arrayOf(doubleArrayOf())
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Models
        if (!weights.contentDeepEquals(other.weights)) return false
        if (!bias.contentDeepEquals(other.bias)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = weights.contentDeepHashCode()
        result = 31 * result + bias.contentDeepHashCode()
        return result
    }
}
