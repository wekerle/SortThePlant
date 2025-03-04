package com.example.sorttheplants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sorttheplants.ui.theme.SortThePlantsTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.sorttheplants.businessLayer.LevelBuilder
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.sorttheplants.composables.ButtonWithIcon
import com.example.sorttheplants.composables.GameContent

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
        modifier = Modifier.fillMaxSize().background(Color(0xFF8BB8C7)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Tube Icon",
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Developed and created by Wekerle Tibor", fontWeight = FontWeight.Bold)

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
    GameUI()
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
                painter = painterResource(id = R.drawable.home),
                text = "Home",
                onClick = { /* Handle Start Game action */ },
                Modifier.padding(1.dp).weight(1f) ,
                Modifier.size(25.dp),
                Modifier.size(20.dp)
            )
            ButtonWithIcon(
                painter = painterResource(id = R.drawable.restart),
                text = "Restart",
                onClick = { /* Handle Start Game action */ },
                Modifier.padding(1.dp).weight(1f) ,
                Modifier.size(25.dp),
                Modifier.size(20.dp)
            )
            ButtonWithIcon(
                painter = painterResource(id = R.drawable.tubeplus),
                text = "Help",
                onClick = { /* Handle Start Game action */ },
                Modifier.padding(1.dp).weight(1f) ,
                Modifier.size(25.dp),
                Modifier.size(20.dp)
            )
        }

        // Spacer to create some space between the buttons and game content
        Spacer(modifier = Modifier.height(16.dp))

        // Game content (for example, a grid or game board)
        val levelBuilder = LevelBuilder.getInstance()
        levelBuilder.buildLevel()
        var level=levelBuilder.getLevelNextLevel(1)

        GameContent(level)
    }
}
