//Counsel: Theodore Dubois
//Counsel: Chad Paine

package edu.csus.ecs.athena.meeseeksparking

import android.content.Context
import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import java.sql.Blob
import java.sql.ResultSet
import android.graphics.Bitmap



class LotDisplay : AppCompatActivity() {

    var LotNameString : String = "lot1" //pushed from the previous activity
    var FloorNumInt : Int = 0 //pushed from the previous activity
    var conf: Bitmap.Config = Bitmap.Config.ARGB_8888 // see other conf types
    var bmp = Bitmap.createBitmap(0, 0, conf)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lot_display)

        LotNameString = "GetNameFromActivityPush" //TODO retrieve correct name
        FloorNumInt = 1 //TODO retreive correct floor

        getMapBmp()

        val scrollyBoi = findViewById<HorizontalScrollView>(R.id.scrollyBoi) as LinearLayout
        val background = Canvass(this)
        scrollyBoi.addView(background)
    }

    inner class Canvass(context: Context) : View(context) {

        //val bmp = BitmapFactory.decodeResource(resources, R.mipmap.lot3)

        val red = Paint()
        init {
            red.color = Color.RED
            red.style = Paint.Style.FILL
        }
        val green = Paint()
        init {
            green.style = Paint.Style.FILL
            green.color = Color.GREEN
        }
        val edge = Paint()
        init {
           edge.color = Color.BLACK
            edge.style = Paint.Style.STROKE
            edge.strokeWidth = 3.5F
        }

        init {
            this.layoutParams = ViewGroup.LayoutParams(bmp.width, bmp.height)
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawRGB(220, 220, 220)

            canvas.drawBitmap(bmp,0f,0f, null)

            var sqlQueryPoly = "SELECT Left, Right, Top, Bottom, DegRotation, Available FROM parkingspot WHERE LotName = ? AND FloorNum = ?"
            var querySQL = QuerySQL()
            var results : ResultSet = querySQL.execute(sqlQueryPoly,LotNameString, FloorNumInt)
            while(results.next()) {
                var left : Float = results.getFloat("Left")
                var right  : Float = results.getFloat("Right")
                var top  : Float = results.getFloat("Top")
                var bottom : Float = results.getFloat("Bottom")
                var degRotation : Float = results.getFloat("DegRotation")
                var avail : Int = results.getInt("Available")

                canvas.save()
                canvas.rotate(degRotation, (left+right)/2, (top+bottom)/2)
                if (avail == 1)
                    canvas.drawRect(left, top, right, bottom, green)

                else
                    canvas.drawRect(left, top, right, bottom, red)

                canvas.drawRect(left, top, right, bottom, edge)
                canvas.restore()
            }
            querySQL.close()


            //canvas.save()
           // canvas.rotate(45f, 105f, 1100f)
            //canvas.drawRect(10f, 1000f, 200f, 1200f, red)
            //canvas.restore()
        }
    }

    private fun getMapBmp() : Bitmap{
        var sqlQueryStr = "SELECT lotMap FROM parkinglot WHERE LotName = ? AND FloorNum = ?"
        var querySQL = QuerySQL()
        var results : ResultSet = querySQL.execute(sqlQueryStr,LotNameString, FloorNumInt)
        if(results.next()) {
            var blob : Blob = results.getBlob("lotMap")
            var mapBytes = blob.getBytes(1, blob.length().toInt())
            bmp = BitmapFactory.decodeByteArray(mapBytes,0, blob.length().toInt())
        }
        querySQL.close()
        return bmp
    }
}
