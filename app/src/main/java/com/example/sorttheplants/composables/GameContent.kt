package com.example.sorttheplants.composables

import TubeView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sorttheplants.R

@Composable
fun GameContent(level:List<List<String>>?) {
    if(level==null)
    {
        return
    }

    val maxColumn = 6

    val resultMod = level.size % maxColumn
    var mainRowsCount=level.size / maxColumn
    if(resultMod!=0)
    {
        mainRowsCount += 1
    }

    val chunkedLevels = level.chunked(level.size/mainRowsCount)
    for (chunk in chunkedLevels)
    {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
        {
            for(item in chunk)
            {
                Column(modifier = Modifier.weight(1f).padding(horizontal = 4.dp, vertical = 0.dp)){
                    TubeView(iconList = getIconListFromCode(item))
                }
            }
        }
    }
}

private fun getIconListFromCode(iconCodes:List<String>) :List<Int>
{
    return iconCodes.map{getIconFromCode(it)}
}

private fun getIconFromCode(code:String):Int
{
    return when (code) {
        "buz" -> R.drawable.wheat
        "arp" -> R.drawable.barley
        "tor" -> R.drawable.corn
        else -> R.drawable.oat // Default image if no match
    }
}