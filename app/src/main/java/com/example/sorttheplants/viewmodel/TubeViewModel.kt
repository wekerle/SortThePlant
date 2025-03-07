package com.example.sorttheplants.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TubeViewModel(var id: Int = -1, var iconList: MutableList<Int> = mutableListOf())
{
    var isOnTopOfTube by mutableStateOf(false)
    var onOtherClick: ((Int) -> Unit)? = null

    fun setOtherClick(action: (Int) -> Unit) {
        onOtherClick = action
    }
}