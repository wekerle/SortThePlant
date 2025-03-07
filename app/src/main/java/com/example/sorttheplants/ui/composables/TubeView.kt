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
import com.example.sorttheplants.viewmodel.TubeViewModel

@Composable
fun TubeView(viewModel: TubeViewModel,modifier: Modifier) {

    var firstIconPosition by remember { mutableStateOf(Offset.Zero) }
    var imageHeight by remember { mutableStateOf(0) }
    var hasStarted by remember { mutableStateOf(false) }
    var firstAnimationDone by remember { mutableStateOf(false) }

    val moveToNextTube by viewModel::moveToNextTube

    val secondAnimatedOffset by animateOffsetAsState(
        targetValue = if (firstAnimationDone) {

            Offset(viewModel.nextTubePosition.x-150, viewModel.nextTubePosition.y -150)
        } else {
            firstIconPosition
        },
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
    )

    val animatedOffset by animateOffsetAsState(
        targetValue = if (viewModel.isOnTopOfTube && hasStarted && !firstAnimationDone)
        {
            if(moveToNextTube)
            {
                Offset(viewModel.nextTubePosition.x, viewModel.nextTubePosition.y-imageHeight/2)
            }else
            {
                Offset(viewModel.tubePosition.x, viewModel.tubePosition.y-imageHeight/2)
            }
        }else if(firstAnimationDone)
        {
            Offset(viewModel.nextTubePosition.x, viewModel.nextTubePosition.y-150)
        }
        else
        {
            firstIconPosition
        },

        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        finishedListener = {
            //firstAnimationDone = true
            //IntOffset(
              //  (if (firstAnimationDone) (secondAnimatedOffset.x - firstIconPosition.x).toInt() else 0),
               // (if (firstAnimationDone) (secondAnimatedOffset.y - firstIconPosition.y).toInt() else 0)
           // )
            if (viewModel.moveToNextTube) {
                firstAnimationDone = true  // Mark first animation as complete
            }
        }
    )




    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                viewModel.tubePosition = Offset(
                    x = coordinates.positionInRoot().x,
                    y = coordinates.positionInRoot().y
                )
            }
            .size(75.dp, 265.dp)
            .clickable()  {
                //viewModel.isOnTopOfTube = !viewModel.isOnTopOfTube
                hasStarted = true
                viewModel.onOtherClick?.let { it(viewModel.id) }
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
            viewModel.iconList.forEachIndexed  { index,iconRes ->
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
                                viewModel.firstIconRes = iconRes
                            }
                        }
                        .offset {
                            if (index == 0) {
                                IntOffset(
                                    (if (hasStarted) (animatedOffset.x - firstIconPosition.x).toInt() else 0),
                                    (if (hasStarted) (animatedOffset.y - firstIconPosition.y).toInt() else 0)
                                )
//                                if(hasStarted)
//                                {
//                                    IntOffset(
//                                        (if (hasStarted){ (animatedOffset.x - firstIconPosition.x).toInt()} else 0),
//                                        (if (hasStarted) {(animatedOffset.y - firstIconPosition.y).toInt()} else 0)
//                                    )
//                                }else
//                                {
//                                    IntOffset(
//                                        (if (hasStarted) (secondAnimatedOffset.x - firstIconPosition.x).toInt() else 0),
//                                        (if (hasStarted) (secondAnimatedOffset.y - firstIconPosition.y).toInt() else 0)
//                                    )
//                                }
                            } else {
                                IntOffset(0, 0) // Other icons stay in place
                           }
                        }
                )
            }
        }
    }
}
