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
                var moveCurrentUp: Boolean  = false
                if(this[previousId]?.isOnTopOfTube==true)
                {
                    if(this[previousId]?.firstIconRes==this[currentId]?.firstIconRes)
                    {
                        this[previousId]?.nextTubePosition= this[currentId]?.tubePosition!!
                        this[previousId]?.moveToNextTube=true
                    }
                    else
                    {
                        moveCurrentUp=true
                    }
                }else
                {
                    moveCurrentUp=true
                }
                if(moveCurrentUp)
                {
                    this[currentId]?.isOnTopOfTube=!this[currentId]?.isOnTopOfTube!!
                    if(previousId!=-1)
                    {
                        this[previousId]?.isOnTopOfTube=false
                    }
                }
            }
      }
    }

    val updateIcons2:()->Unit={
        tubeViewModelList = tubeViewModelList.toMutableMap().apply {
            this[previousId]?.isOnTopOfTube = false

            var newIcons=getIconListFromCode(listOf("arp", "tor"))
            this[previousId]?.updateIcons(newIcons)

            var newIcons2=getIconListFromCode(listOf("buz", "buz"))
            this[currentId]?.updateIcons(newIcons2)
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
                    model.updateIcons= {
                        updateIcons2()
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
        "loh" -> R.drawable.clover
        "ugo" -> R.drawable.cucumber
        "luc" -> R.drawable.lucern
        "zab" -> R.drawable.oat
        "pit" -> R.drawable.potato
        "nap" -> R.drawable.sunflower
        "par" -> R.drawable.tomato
        else -> throw Exception("Missing Icon")// Default image if no match
    }
}