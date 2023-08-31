package com.sopt.umbba_android.util

import android.annotation.SuppressLint
import android.content.res.Resources
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.annotation.IntRange
import kotlin.math.roundToInt

const val DEFAULT_INTERVAL = 30
const val MINUTES_MIN = 0
const val MINUTES_MAX = 60

@SuppressLint("PrivateApi")
fun TimePicker.setTimeInterval(
    @IntRange(from = 0, to = 30)
    timeInterval: Int = DEFAULT_INTERVAL
) {
    try {
        val minuteResourcesId = Resources.getSystem().getIdentifier("minute", "id", "android")
        findViewById<NumberPicker?>(minuteResourcesId)?.apply {
            value = (value.toFloat() / timeInterval).roundToInt()
            minValue = MINUTES_MIN
            maxValue = MINUTES_MAX / timeInterval - 1
            displayedValues = mutableListOf<String>().apply {
                for (i in MINUTES_MIN..MINUTES_MAX step timeInterval) {
                    add(String.format("%02d", i))
                }
            }.toTypedArray()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}