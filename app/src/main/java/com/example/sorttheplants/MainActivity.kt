package com.example.sorttheplants

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sorttheplants.ui.theme.SortThePlantsTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.sorttheplants.businessLayer.LevelBuilder
import com.example.sorttheplants.composables.TubeView
import androidx.compose.foundation.Canvas
import androidx.compose.material3.Scaffold
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



       // TubeView(iconList = iconList)

      //  val linearLayout = LinearLayout(this)
        //linearLayout.orientation = LinearLayout.VERTICAL

        // Create an instance of TestTubeView
       // val testTubeView = TubeView(this)

        // Add the TestTubeView to the container
       // linearLayout.addView(testTubeView)

        // Set the layout as the content view
        //setContentView(linearLayout)

        setContent {
            SortThePlantsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    StaticGrid(level)
                   // GameUI()
                    MainScreen()
//                    val iconList = listOf(
//                        R.drawable.wheat,
//                        R.drawable.barley,
//                        R.drawable.oat,
//                       // R.drawable.sunflower,
//                      //  R.drawable.lucern,
//                        R.drawable.lucern2
//                    )
//
//                    TubeView(iconList = iconList)
                }
            }
        }

    }
}

@Composable
fun HomeScreen(onStartGame: () -> Unit) {
    // Home screen UI with a button to navigate to the game
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to the Homepage!")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onStartGame) {
            Text("Start Game")
        }
    }
}

@Composable
fun MainScreen() {
    // Define the state variable to control which screen to show
    var currentScreen by remember { mutableStateOf(Screen.Home) }

    // Based on the current screen, show either the home or game screen
    when (currentScreen) {
        Screen.Home -> HomeScreen(onStartGame = { currentScreen = Screen.Game })
        Screen.Game -> GameScreen(onBackToHome = { currentScreen = Screen.Home })
    }
}

enum class Screen {
    Home,
    Game
}

@Composable
fun GameScreen(onBackToHome: () -> Unit) {
    // Game screen UI with a button to navigate back to the homepage
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Game Screen")
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(onClick = onBackToHome) {
//            Text("Back to Homepage")
//        }
//    }

    GameUI()

}

@Composable
fun GameContent(level:List<List<String>>?) {
    if(level==null)
    {
        return
    }

    val maxColumn = 5
    val mainRowsCount=level.size / maxColumn + 1

    for (row in 0 until mainRowsCount)
    {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
        {
            for(item in level.indices)
            {
                Column(modifier = Modifier.weight(1f)){
                    var container=level[item]
                    for(cont in container.indices)
                    {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
                        {
                            var currentPlant=container[cont]
                            Image(
                                painter = painterResource(id = getImageByString(currentPlant)),
                                contentDescription = "Custom PNG Icon",
                                modifier = Modifier
                                    .size(75.dp)
                                    .padding(5.dp)
                            )
    //                    Button(
    //                        onClick = {},
    //                        modifier = Modifier
    //                            .weight(1f)
    //                            .padding(8.dp)
    //                    ) {
    //                        Text("Button $row, $cont}")
    //                    }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameContent2(level:List<List<String>>?) {
    if(level==null)
    {
        return
    }

    val maxColumn = 5
    val mainRowsCount=level.size / maxColumn + 1

    for (row in 0 until mainRowsCount)
    {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
        {
            for(item in level.indices)
            {
                Column(modifier = Modifier.weight(1f)){
                val iconList = listOf(
                        R.drawable.wheat,
                        R.drawable.barley,
                        R.drawable.oat,
                       // R.drawable.sunflower,
                      //  R.drawable.lucern,
                        R.drawable.lucern2
                    )

                    TubeView(iconList = iconList)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SortThePlantsTheme {
        Greeting("Android")
    }
}

@Composable
fun PngIconList(key:Int,modifier: Modifier = Modifier) {
    val iconList = listOf(
        R.drawable.wheat,  // Replace with your actual PNG file names
        R.drawable.barley,
        R.drawable.oat,
       // R.drawable.oat2,
        R.drawable.lucern
    )

    LazyColumn(
        modifier = modifier.padding(16.dp)// Assigning a key to LazyColumn ensures it redraws when the key changes
    ) {
        items(iconList, key = { it }) { iconRes ->
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "Custom PNG Icon",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
            )
        }
    }
}

fun getImageByString(code: String):Int
{
    return when (code) {
        "buz" -> R.drawable.wheat
        "arp" -> R.drawable.barley
        "tor" -> R.drawable.corn
        else -> R.drawable.oat // Default image if no match
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGameUI() {
    GameUI()
}

@Composable
fun GameUI() {
    // Main Column to organize the UI elements vertically
    Column(modifier = Modifier.fillMaxSize()) {
        // Row for buttons at the top
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ButtonWithIcon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Play icon from Material Icons
                text = "Home",
                onClick = { /* Handle Start Game action */ }
            )
            ButtonWithIcon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Play icon from Material Icons
                text = "Restart",
                onClick = { /* Handle Start Game action */ }
            )
            ButtonWithIcon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Play icon from Material Icons
                text = "Help",
                onClick = { /* Handle Start Game action */ }
            )
        }

        // Spacer to create some space between the buttons and game content
        Spacer(modifier = Modifier.height(16.dp))

        // Game content (for example, a grid or game board)
        val levelBuilder = LevelBuilder.getInstance()
        levelBuilder.buildLevel()
        var level=levelBuilder.getLevelNextLevel(1)

        GameContent2(level)

    }
}

@Preview(showBackground = true)
@Composable
fun ButtonWithIconPreview() {
    //ButtonWithIcon()
}

@Composable
fun ButtonWithIcon(
    painter: Painter,           // Icon to show in the button
    text: String,                // Text to show next to the icon
    onClick: () -> Unit,         // Action to perform on click
    buttonModifier: Modifier = Modifier, // Optional modifier for customization
    iconModifier: Modifier = Modifier.size(40.dp),    // Size for the icon
    textModifier: Modifier = Modifier // Optional modifier for customization
) {
    Button(
        onClick = onClick,
        modifier = buttonModifier.padding(16.dp)
    ) {
        Row {
            Icon(
                painter = painter,
                contentDescription = "Button Icon",
                modifier = iconModifier.padding(end = 8.dp) // Space between icon and text
            )
            Text(text = text,
                modifier = textModifier)
        }
    }
}

@Composable
fun TubeView(iconList: List<Int>, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(75.dp, 400.dp), // Adjust the size as needed
           // .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Draw the tube using Canvas
        Canvas(modifier = Modifier.matchParentSize()) {
          //  val tubeWidth = size.width * 0.6f
            val tubeWidth = size.width
            val tubeHeight = size.height
            val tubeX = (size.width - tubeWidth) / 2
            val tubeY = 0f

            val borderPaint = Paint().apply {
                color = Color.Black // Set the border color
                strokeWidth = 4f // Set the border width
                style = PaintingStyle.Stroke // Set it to stroke mode (for border)
            }


            drawRoundRect(
                color = Color.LightGray, // Tube color
                topLeft = Offset(tubeX, tubeY),
                size = Size(tubeWidth, tubeHeight),
                cornerRadius = CornerRadius(40f, 40f), // Rounded tube edges
                style = Fill,
            )
        }

        // Overlay the icons inside the tube
        Column(
            modifier = Modifier
               // .fillMaxSize(),
              //  .padding(16.dp),
                .fillMaxWidth(),

            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            iconList.forEach { iconRes ->
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "Tube Icon",
                    //modifier = Modifier.size(50.dp)
                    modifier = Modifier.fillMaxWidth() .size(75.dp)
                        //.padding(5.dp)
                )
            }
        }
    }
}
