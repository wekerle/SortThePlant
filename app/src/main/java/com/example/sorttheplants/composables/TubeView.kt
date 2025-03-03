package com.example.sorttheplants.composables

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.compose.ui.res.painterResource
import com.example.sorttheplants.R

class TubeView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    // Paint objects to set the color and style of the tube and the liquid inside
    private val tubePaint = Paint().apply {
        color = 0xFF808080.toInt() // Gray color for the tube
        style = Paint.Style.FILL
    }

    private val iconList = listOf(
        R.drawable.wheat,  // Replace with your actual PNG file names
        R.drawable.barley,
        R.drawable.oat,
        R.drawable.lucern,
        R.drawable.lucern2
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Calculate the tube's position and size
        val left = width * 0.2f
        val top = height * 0.1f
        val right = width * 0.8f
        val bottom = height * 0.8f

        // Draw the tube (a rectangle with rounded corners)
        val tubeRect = RectF(left, top, right, bottom)
        canvas.drawRoundRect(tubeRect, 70f, 70f, tubePaint)

        val iconSpacing = (bottom - top) / (iconList.size + 1)

        // Loop through the icon list and draw each icon inside the tube
        for (i in iconList.indices) {
            val iconTop = top + (iconSpacing * (i + 1))
            val iconBottom = iconTop + 75f // Icon height (adjust size as needed)
            val iconLeft = left + (right - left - 75f) / 2 // Center the icon horizontally
            val iconRight = iconLeft + 75f // Icon width (adjust size as needed)

            // Draw the icon
          //  val iconBitmap = painterResource(id = iconList[i]).toBitmap()
            //canvas.drawBitmap(iconBitmap, iconLeft, iconTop, null)
        }
    }
}