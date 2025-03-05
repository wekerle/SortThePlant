import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun TubeView(iconList: List<Int>, modifier: Modifier = Modifier) {
    var tubePosition by remember { mutableStateOf(Offset.Zero) }
    var firstIconPosition by remember { mutableStateOf(Offset.Zero) }
    var isOnTopOfTube by remember { mutableStateOf(false) }
    var imageHeight by remember { mutableStateOf(0) }

    val animatedOffset by animateOffsetAsState(
        targetValue = if (isOnTopOfTube)
            Offset(tubePosition.x, tubePosition.y-imageHeight/2)
        else
            firstIconPosition,
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing) // Smooth animation
    )


    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                tubePosition = Offset(
                    x = coordinates.positionInRoot().x,
                    y = coordinates.positionInRoot().y
                )
            }
            .size(75.dp, 265.dp)
            .clickable()  {
                isOnTopOfTube = !isOnTopOfTube
            },
        contentAlignment = Alignment.BottomCenter
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {
            val tubeWidth = size.width
            val tubeHeight = size.height
            val tubeX = (size.width - tubeWidth) / 2
            val tubeY = 0f

            drawRoundRect(
                color = Color.LightGray, // Tube color
                topLeft = Offset(tubeX, tubeY),
                size = Size(tubeWidth, tubeHeight),
                cornerRadius = CornerRadius(40f, 40f),
               // style = Fill,
                Stroke(width = 4f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            iconList.forEachIndexed  { index,iconRes ->
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "Tube Icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            if (index == 0) {  // Only store position for the first icon
                                firstIconPosition = Offset(
                                    x = coordinates.positionInRoot().x,
                                    y = coordinates.positionInRoot().y
                                )
                                imageHeight = coordinates.size.height
                            }
                        }
                        .offset {
                            if (index == 0) {
                                IntOffset(
                                    (animatedOffset.x - firstIconPosition.x).toInt(),
                                    (animatedOffset.y - firstIconPosition.y).toInt()
                                  //  firstIconPosition.x.toInt(),
                                   // firstIconPosition.y.toInt()
                                    //animatedOffset.x.toInt(),
                                   // animatedOffset.y.toInt()
                                )
                            } else {
                                IntOffset(0, 0) // Other icons stay in place
                           }
                        }
                )
            }
        }
    }
}
