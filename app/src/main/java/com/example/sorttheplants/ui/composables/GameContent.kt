package com.example.sorttheplants.ui.composables

import TubeView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sorttheplants.R
import com.example.sorttheplants.viewmodel.TubeViewModel

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

    var tubeViewIndex=0;

    var tubeViewModelList by remember {
        mutableStateOf(
            chunkedLevels.flatten().map { item ->
                val model = TubeViewModel(id=tubeViewIndex,iconList = getIconListFromCode(item).toMutableList())
                tubeViewIndex++
                model
            }.associateBy { it.id }
        )
    }

    var previousId=-1
    var currentId=-1
    val onOtherClick:(Int)->Unit={clickedId ->
        tubeViewModelList = tubeViewModelList.toMutableMap().apply {
           previousId=currentId
           currentId=clickedId

            if(previousId==currentId)
            {
                this[currentId]?.isOnTopOfTube=!this[currentId]?.isOnTopOfTube!!
            }else
            {
                this[currentId]?.isOnTopOfTube=!this[currentId]?.isOnTopOfTube!!
                if(previousId!=-1)
                {
                    this[previousId]?.isOnTopOfTube=false
                }
            }
      }
    }

    tubeViewIndex=0;
    for (chunk in chunkedLevels)
    {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
        {
            for(item in chunk)
            {
                Column(modifier = Modifier.weight(1f).padding(horizontal = 4.dp, vertical = 0.dp)){
                    val model = tubeViewModelList[tubeViewIndex] ?: return@Column
                    model.setOtherClick { clickedId ->
                        onOtherClick(clickedId)
                    }
                    TubeView(model,modifier = Modifier)
                }

                tubeViewIndex++
            }
        }
    }
}

private fun getIconListFromCode(iconCodes:List<String>) :List<Int>
{
    return iconCodes.map{ getIconFromCode(it) }
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