//Counsel: Theodore Dubois
//Counsel: Chad Paine

package edu.csus.ecs.athena.meeseeksparking

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout

class LotDisplay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lot_display)

        val scrollyBoi = findViewById<HorizontalScrollView>(R.id.scrollyBoi) as LinearLayout
        val background = Canvass(this)
        scrollyBoi.addView(background)
    }

    inner class Canvass(context: Context) : View(context) {

        val bmp = BitmapFactory.decodeResource(resources, R.mipmap.lot3)
        val red = Paint()
        init {
            red.color = Color.RED
            red.style = Paint.Style.FILL
        }
        val rededge = Paint()
        init {
            rededge.color = Color.BLACK
            rededge.style = Paint.Style.STROKE
            rededge.strokeWidth = 3.5f
        }
        val green = Paint()
        init {
            green.style = Paint.Style.FILL
            green.color = Color.GREEN
        }
        val greenedge = Paint()
        init {
            greenedge.color = Color.BLACK
            greenedge.style = Paint.Style.STROKE
            greenedge.strokeWidth = 3.5F
        }

        init {
            this.layoutParams = ViewGroup.LayoutParams(bmp.width, bmp.height)
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawRGB(220, 220, 220)
/*
            var sqlQueryStr = "SELECT Poly, Available FROM parkingspot WHERE LotName = ? AND FloorNum = ?"
            var querySQL = QuerySQL()
            var results : ResultSet = querySQL.execute(sqlQueryStr,LotNameString, FloorNumInt)
            if(results.next()) {
                var avail = results.getInt("Available")
                var poly = results.getString("Poly")
            }
            querySQL.close() */

            canvas.drawBitmap(bmp,0f,0f, null)

            //canvas.save()
           // canvas.rotate(45f, 105f, 1100f)
            //canvas.drawRect(10f, 1000f, 200f, 1200f, red)
            //canvas.restore()

            canvas.save()

            canvas.drawRect(90f, 850f, 150f, 990f, green)
            canvas.drawRect(90f, 850f, 150f, 990f, greenedge)

            canvas.restore()
            // What is going to be used in order to pull information from the database.

            canvas.save()
            canvas.rotate(0f, 0f, 0f)
            canvas.drawRect(160f, 850f, 220f, 990f, red)
            canvas.drawRect(160f, 850f, 220f, 990f, rededge)
            canvas.restore()

            canvas.save()
            canvas.rotate(0f, 0f, 0f)
            canvas.drawRect(230f, 850f, 290f, 990f, red)
            canvas.drawRect(230f, 850f, 290f, 990f, rededge)

            canvas.restore()


        }
    }
}
