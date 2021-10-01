package com.android_academy.custompagination

import java.util.concurrent.ConcurrentHashMap

object Measurements {

    private val serializationCost: ConcurrentHashMap<String, Long> = ConcurrentHashMap()
    private val deserializationCost: ConcurrentHashMap<String, Long> = ConcurrentHashMap()

    fun getAverageSerializationCost(): Float {
        return serializationCost.values.sum().toFloat() / serializationCost.size
    }

    fun getAverageDeserializationCost(): Float {
        return deserializationCost.values.sum().toFloat() / deserializationCost.size
    }

    fun getTotalSerializationCost(): Long {
        return serializationCost.values.sum()
    }

    fun getTotalDeserializationCost(): Long {
        return deserializationCost.values.sum()
    }

    fun addSerializationMeasurement(id: String, measurement: Long) {
        serializationCost[id] = measurement
    }

    fun addDeserializationMeasurement(id: String, measurement: Long) {
        deserializationCost[id] = measurement
    }
    fun getNumbObjectSerialized() = serializationCost.size
    fun getNumbObjectDeserialized() = deserializationCost.size
}