package com.noemi.android.readme.helper

import android.view.View
import java.util.*
import kotlin.math.abs

abstract class DebounceClickListener(private val timeLimit: Long = 900L) : View.OnClickListener{

    private val map: MutableMap<View, Long>

    init {
        map = WeakHashMap<View, Long>()
    }

    abstract fun onDebounce(view: View)

    override fun onClick(view: View) {
        val currentTime = System.currentTimeMillis()
        val previousTime = map[view]
        map[view] = currentTime
        if (previousTime == null || (abs(currentTime - previousTime.toLong())) > timeLimit)
            onDebounce(view)
    }
}