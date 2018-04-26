package edu.csus.ecs.athena.meeseeksparking

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.opengl.ETC1.getWidth
import android.text.Layout
import android.view.View


class LotDisplay: AppCompatActivity () {

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView (R.layout.activity_lot_display)

        val layout1 = findViewById<View>(R.id.layout1) as android.support.constraint.ConstraintLayout
        val background = Canvass (this)
        layout1.addView (background)
    }
    // coding180.com
    class Canvass (context: Context): View (context) {

        override fun onDraw (canvas: Canvas) {
            canvas.drawRGB (220, 220, 220)
            val width = getWidth ()
            val brush1 = Paint ()
            val brush2 = Paint ()


            brush1.setARGB (255, 255, 0, 0)
            brush1.setStyle (Paint.Style.STROKE)
            brush1.setStrokeWidth(6F)
            brush2.setARGB (255, 0, 255, 0)
            brush2.setStyle (Paint.Style.STROKE)
            brush2.setStrokeWidth(6F)
            canvas.drawRect (10f, 10f,100f , 40f, brush1)

            canvas.drawRect (10f, 46f, 100f, 90f, brush1)

            canvas.drawRect (10f, 96f, 200f, 190f, brush1)

            canvas.drawRect (10f, 196f, 200f, 290f, brush2)

            canvas.drawRect (10f, 296f, 200f, 390f, brush1)
        }
    }
}
