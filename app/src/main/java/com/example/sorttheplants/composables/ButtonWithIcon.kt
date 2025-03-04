package com.example.sorttheplants.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithIcon(
    painter: Painter,
    text: String,
    onClick: () -> Unit,
    buttonModifier: Modifier,
    iconModifier: Modifier,
    textModifier: Modifier
) {
    Button(
        onClick = onClick,
        modifier = buttonModifier
    ) {
        Row {
            Icon(
                painter = painter,
                contentDescription = "Button Icon",
                modifier = iconModifier.weight(1f)
            )
            Text(text = text,
                modifier = textModifier.weight(2f))
        }
    }
}