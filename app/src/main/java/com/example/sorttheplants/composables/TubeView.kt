import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TubeView(iconList: List<Int>, modifier: Modifier = Modifier) {
    var reorderedIcons by remember { mutableStateOf(iconList) }
    var isFirstIconAtTop by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(75.dp, 300.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        val firstIconOffset by animateFloatAsState(
            targetValue = if (isFirstIconAtTop) -100f else 0f, // Move icon upwards smoothly
            animationSpec = tween(durationMillis = 500)
        )

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
                .fillMaxWidth()
                .clickable {
                    // Handle click here
                    println("TubeView clicked!")
                },
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            iconList.forEach { iconRes ->
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "Tube Icon",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
