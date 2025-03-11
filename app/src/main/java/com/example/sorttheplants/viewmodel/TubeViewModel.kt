package com.example.sorttheplants.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

class TubeViewModel(var id: Int = -1, var iconList: MutableList<Int> = mutableListOf())
{
    var triggerRecomposition by mutableStateOf(true)
        private set
    var tubePosition by mutableStateOf (Offset.Zero)
    var nextTubePosition by mutableStateOf (Offset.Zero)
    var isOnTopOfTube by mutableStateOf(false)
    var moveToNextTube by mutableStateOf(false)
    var firstIconRes by mutableIntStateOf(0)
    var onOtherClick: ((Int) -> Unit)? = null
    var updateIcons: (() -> Unit)? = null
    var firstIconPositionSet  by mutableStateOf(false)
    var hasStarted  by mutableStateOf(false)
    var firstIconPosition by mutableStateOf (Offset.Zero)

    fun setOtherClick(action: (Int) -> Unit) {
        onOtherClick = action
    }

    fun updateIcons(newIcons: List<Int>) {
        firstIconPositionSet=false
        hasStarted=false
        iconList = newIcons.toMutableList()
        firstIconPosition=Offset.Zero

        triggerRecomposition = false
        triggerRecomposition = true
    }
}