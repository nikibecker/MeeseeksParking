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
import android.util.DisplayMetrics
import android.graphics.BitmapFactory







class LotDisplay : AppCompatActivity() {

    var LotNameString : String = "lot1" //pushed from the previous activity
    var FloorNumInt : Int = 0 //pushed from the previous activity
    var conf: Bitmap.Config = Bitmap.Config.ARGB_8888 // see other conf types
    var bmp : Bitmap = Bitmap.createBitmap(1, 1, conf)
    var flag : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lot_display)


        LotNameString = intent.extras.getString("lotNameData") //TODO retrieve correct name
        FloorNumInt = intent.extras.getInt("floorNumData") //TODO retreive correct floor

        getMapBmp()
        if (flag) {
            val scrollyBoi = findViewById<HorizontalScrollView>(R.id.scrollyBoi) as LinearLayout
            val background = Canvass(this)
            scrollyBoi.addView(background)
        }
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

            var sqlQueryPoly = "SELECT LeftLine, RightLine, TopLine, BottomLine, DegRotation, Available FROM parkingspot WHERE LotName = ? AND FloorNum = ?"
            var querySQL = QuerySQL()
            var results : ResultSet = querySQL.execute(sqlQueryPoly,LotNameString, FloorNumInt)
            while(results.next()) {
                var left : Float = results.getFloat("LeftLine")
                var right  : Float = results.getFloat("RightLine")
                var top  : Float = results.getFloat("TopLine")
                var bottom : Float = results.getFloat("BottomLine")
                var degRotation : Float = results.getFloat("DegRotation")
                var avail : Int = results.getInt("Available")

                canvas.save()
                canvas.rotate(degRotation, (left+right)/2, (top+bottom)/2)
                val density : Float = getResources().getDisplayMetrics().density
                if (avail == 1)
                    canvas.drawRect(left * density, top * density, right * density, bottom * density, green)

                else
                    canvas.drawRect(left * density, top * density, right * density, bottom * density, red)

                canvas.drawRect(left * density, top * density, right * density, bottom * density, edge)
                canvas.restore()
            }
            querySQL.close()


            //canvas.save()
           // canvas.rotate(45f, 105f, 1100f)
            //canvas.drawRect(10f, 1000f, 200f, 1200f, red)
            //canvas.restore()
        }
    }

    private fun getMapBmp() {
        val options = BitmapFactory.Options()
        options.inDensity = DisplayMetrics.DENSITY_DEFAULT
        options.inTargetDensity = this.getResources().getDisplayMetrics().densityDpi
        options.inScaled = true

        var sqlQueryStr = "SELECT LotImage FROM lotgrid WHERE LotName = ? AND FloorNum = ? AND LotImage IS NOT NULL"
        var querySQL = QuerySQL()
        var results : ResultSet = querySQL.execute(sqlQueryStr,LotNameString, FloorNumInt)
        if(results.next()) {
            var blob : Blob = results.getBlob("LotImage")
            var mapBytes = blob.getBytes(1, blob.length().toInt())
            bmp = BitmapFactory.decodeByteArray(mapBytes,0, blob.length().toInt(), options)
            flag = true
        }
        else
            flag = false
        querySQL.close()
    }
}
