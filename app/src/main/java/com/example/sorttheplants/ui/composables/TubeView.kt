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
import androidx.compose.runtime.key
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
import kotlin.random.Random

@Composable
fun TubeView(viewModel: TubeViewModel,modifier: Modifier) {

    var imageHeight by remember { mutableStateOf(0) }
    var secondAnimationFinish by remember { mutableStateOf(false) }
    var firstAnimationDone by remember { mutableStateOf(false) }

    val moveToNextTube by viewModel::moveToNextTube
    val firstIconPositionSet by viewModel::firstIconPositionSet

    var secondAnimationStart:Boolean=false

    val animatedOffset by animateOffsetAsState(
        targetValue = if (viewModel.isOnTopOfTube && viewModel.hasStarted && !firstAnimationDone)
        {
            if(moveToNextTube)
            {
                Offset(viewModel.nextTubePosition.x, viewModel.nextTubePosition.y-imageHeight/2)
            }else
            {
                Offset(viewModel.tubePosition.x, viewModel.tubePosition.y-imageHeight/2)
            }
        }else if(moveToNextTube && firstAnimationDone)
        {
            secondAnimationStart=true;
            Offset(viewModel.nextTubePosition.x, viewModel.nextTubePosition.y+imageHeight/2+imageHeight)
        }else if(secondAnimationFinish)
        {
            viewModel.isOnTopOfTube=false
            viewModel.firstIconPosition
        }
        else
        {
            viewModel.firstIconPosition
        },

        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        finishedListener = {
            if (viewModel.moveToNextTube) {
                firstAnimationDone = true  // Mark first animation as complete
            }
            if(secondAnimationStart)
            {
                viewModel.hasStarted=false
                firstAnimationDone=false
                secondAnimationStart=false
                secondAnimationFinish=true
                viewModel.moveToNextTube=false
                viewModel.updateIcons?.let { it1 -> it1() }
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
                viewModel.hasStarted = true
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
        if (viewModel.triggerRecomposition) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                viewModel.iconList.forEachIndexed { index, iconRes ->
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = "Tube Icon",
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    if (index == 0 && !firstIconPositionSet) {  // Only store position for the first icon
                                        viewModel.firstIconPosition = Offset(
                                            x = coordinates.positionInRoot().x,
                                            y = coordinates.positionInRoot().y
                                        )
                                        viewModel.firstIconPositionSet = true
                                        imageHeight = coordinates.size.height
                                        viewModel.firstIconRes = iconRes
                                    }
                                }
                                .offset {
                                    if (index == 0) {
                                        if (viewModel.hasStarted) {
                                            IntOffset(
                                                (animatedOffset.x - viewModel.firstIconPosition.x).toInt(),
                                                (animatedOffset.y - viewModel.firstIconPosition.y).toInt()
                                            )
                                        }
                                        else {
                                            IntOffset(0, 0)
                                        }

                                    }
                                    else {
                                        IntOffset(0, 0) // Other icons stay in place
                                    }
                                }
                        )
                }
            }
        }
    }
}
