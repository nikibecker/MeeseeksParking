package edu.csus.ecs.athena.meeseeksparking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.EditText
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_maps.*
//import com.google.android.gms.location.LocationListener
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import java.sql.ResultSet
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap
    private lateinit var myMap1: GoogleMap
    private lateinit var myMap2: GoogleMap
    private lateinit var myMap3: GoogleMap
    private lateinit var myMap4: GoogleMap

    //False Variables to test for Parking Structure Color Representations

    val a: Int = 95
    val b: Int = 100
    val c: Int = 25
    val d: Int = 40
    val e: Int = 75

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Code segments for buttons leading to Settings, Login, and Overview

        btnSettings.setOnClickListener{
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btnOverview.setOnClickListener{
            val intent = Intent(this, Overview::class.java)
            startActivity(intent)
        }



    }

 /*   override fun drawPolygon(googleMap: GoogleMap) {

         val polygon1 = googleMap.addPolygon(PolygonOptions()
                 .clickable(true)
                 .add(
                         LatLng(38.558605, -121.422440),
                         LatLng( 38.558785, -121.422515),
                         LatLng( 38.558897, -121.422112),
                         LatLng(38.558716, -121.422042))
                 .fillColor(0x330000FF)
                 //.setTag("PL5")
         )
         //fillColor(0x330000FF)
         polygon1.setTag("PL5")
     }*/



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    override fun onMapReady(googleMap: GoogleMap) {
        // Get data from database and put data into data structures.
        var sqlQueryStr = "SELECT parkinglot.LotName, parkinglot.SpotCount, parkinglot.SpotTaken, ST_ASText(lotgrid.Poly) FROM lotgrid, parkinglot WHERE parkinglot.LotName=lotgrid.LotName"
        var querySQL = QuerySQL()
        var results : ResultSet = querySQL.execute(sqlQueryStr)

        val lotNames : MutableList<String> = ArrayList()
        val spotCounts : MutableList<Int> = ArrayList()
        val spotsTaken : MutableList<Int> = ArrayList()
        val polys : MutableList<String> = ArrayList()
        val lots : MutableMap<Int, Polygon>
        val names : MutableMap<Int, String>

        while (results.next())
        {
            lotNames.add(results.getString(1))
            spotCounts.add(results.getInt(2))
            spotsTaken.add(results.getInt(3))
            val temp = results.getString(4)
            val temp2 = temp.replace(("[^0-9 .,-]").toRegex(), "")
            polys.add(temp2)
        }
        querySQL.close()

        //Instantiate the Map for Google
        myMap = googleMap
        myMap1 = googleMap
        myMap2 = googleMap
        myMap3 = googleMap
        myMap4 = googleMap

        // Add a marker in Sacramento State and zoom in to view the campus positions well
        val SacState = LatLng(38.5611, -121.4240)
        myMap1.addMarker(MarkerOptions().position(SacState).title("Sacramento State"))
        myMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(SacState, 15.5f))

        //Start code for dynamic polygon creation
        val map = mutableMapOf<String, Int>()
        var i = 0

        for (name in lotNames) {
            val xy : List<String> = polys.get(i).split(",")
            val latLongs : MutableList<LatLng> = ArrayList()
            for (xys in xy) {
                val x = xys.substringBefore(" ").toDouble()
                val y = xys.substringAfter(" ").toDouble()
                latLongs.add(LatLng(x, y))
            }
            var color = 0x33FFFF00
            val a = spotsTaken.get(i)/spotCounts.get(i)
            if (a > .90 && a <= 1.00)
                    color = 0x33990000
            else if(a >= 0 && a <= .80)
                    color = 0x33009900
            else
                    color = 0x33FFFF00

            myMap.addPolygon(PolygonOptions()

                    .clickable(true)
                    .addAll(latLongs)
                    .fillColor(color)
                    .strokeWidth(0.75F)
                    .zIndex(i.toFloat())
            )
            i++
        }

        myMap.setOnPolygonClickListener {
            val intent = Intent(this, LotDisplay::class.java)
            val extras = Bundle()
            val lotName = "Lot1" //need to make dynamic
            extras.putString("lotNameData", lotName)
            extras.putInt("floorNumData", 1)
            startActivity(intent)
        }

        /*
        myMap.setOnPolygonClickListener {
           val intentlot5 = Intent(this, lot5::class.java)
           startActivity(intentlot5)
        }

        myMap1.setOnPolygonClickListener {
            val intentlot7 = Intent(this, lot7::class.java)
            startActivity(intentlot7)
        }

        myMap2.setOnPolygonClickListener {
            val intentps1 = Intent(this, ps1::class.java)
            startActivity(intentps1)
        }

        myMap3.setOnPolygonClickListener {
            val intentps2 = Intent(this, ps2::class.java)
            startActivity(intentps2)
        }

        myMap4.setOnPolygonClickListener {


            val intentps3 = Intent(this, ps3::class.java)
            startActivity(intentps3)

            val intentps2 = Intent(this, ps2::class.java)
            startActivity(intentps2)

            val intentps1 = Intent(this, ps1::class.java)
            startActivity(intentps1)
        }


        /////////////////////////////////
        //Lot Colors are determined by  /
        //0-80% : Green                 /
        //80-90% : Yellow               /
        //90 - 100% : Red               /
        /////////////////////////////////

        ///////
        //Lot 5: if statements to create Polygon and detail how occupied the lot is by color
        ///////

        if (a > 90 && a <= 100) {
          var lot5 =  myMap.addPolygon(PolygonOptions()

                    .clickable(true)

                    .add(
                            LatLng(38.558605, -121.422440),
                            LatLng(38.558785, -121.422515),
                            LatLng(38.558897, -121.422112),
                            LatLng(38.558716, -121.422042))


                    .fillColor(0x33990000)
                    .strokeWidth(0.75F)
                    //.t


            )

        } else if(a >= 0 && a <= 80) {

            myMap.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.558605, -121.422440),
                            LatLng(38.558785, -121.422515),
                            LatLng(38.558897, -121.422112),
                            LatLng(38.558716, -121.422042))
                    .fillColor(0x33009900)
                    .strokeWidth(0.75F)
                    //.t
            )
        }

        else if(a > 80 && a <= 90) {

            myMap.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.558605, -121.422440),
                            LatLng(38.558785, -121.422515),
                            LatLng(38.558897, -121.422112),
                            LatLng(38.558716, -121.422042))
                    .fillColor(0x33FFFF00)
                    .strokeWidth(0.75F)
                    //.
            )

        }


        ////////
        //Lot 7: if statements to create Polygon and detail how occupied the lot is by color
        ////////
        if (b > 90 && b <= 100) {

            myMap1.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.554225, -121.418572),
                            LatLng(38.554273, -121.419824),
                            LatLng(38.557206, -121.420485),
                            LatLng(38.557339, -121.420280),
                            LatLng(38.556935, -121.419811),
                            LatLng(38.557265, -121.418599))
                    .fillColor(0x33990000)
                    .strokeWidth(0.75F)
            )


        } else if(b >=0  && b <= 80) {

            myMap1.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.554225, -121.418572),
                            LatLng(38.554273, -121.419824),
                            LatLng(38.557206, -121.420485),
                            LatLng(38.557339, -121.420280),
                            LatLng(38.556935, -121.419811),
                            LatLng(38.557265, -121.418599))
                    .fillColor(0x33009900)
                    .strokeWidth(0.75F)
            )

        }

        else if(b > 80  && b <= 90) {

            myMap1.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.554225, -121.418572),
                            LatLng(38.554273, -121.419824),
                            LatLng(38.557206, -121.420485),
                            LatLng(38.557339, -121.420280),
                            LatLng(38.556935, -121.419811),
                            LatLng(38.557265, -121.418599))
                    .fillColor(0x33FFFF00)
                    .strokeWidth(0.75F)

            )

        }

        //////////////////////
        //Parking Structure 1: if statements to create Polygon and detail how occupied the lot is by color
        //////////////////////

        if (c > 90 && c <= 100) {

            myMap2.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.559075, -121.427242),
                            LatLng(38.559650, -121.427715),
                            LatLng(38.560192, -121.426635),
                            LatLng(38.560182, -121.426544),
                            LatLng(38.559619, -121.426090))
                    .fillColor(0x33990000)
                    .strokeWidth(0.75F)
                    //.setTag("PL5")
            )

        } else  if(c >= 0 && c <= 80) {

            myMap2.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.559075, -121.427242),
                            LatLng(38.559650, -121.427715),
                            LatLng(38.560192, -121.426635),
                            LatLng(38.560182, -121.426544),
                            LatLng(38.559619, -121.426090))
                    .fillColor(0x33009900)
                    .strokeWidth(0.75F)
                    //.setTag("PL5")
            )

        }
        else  if(c > 80  && c <= 90) {

            myMap2.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.559075, -121.427242),
                            LatLng(38.559650, -121.427715),
                            LatLng(38.560192, -121.426635),
                            LatLng(38.560182, -121.426544),
                            LatLng(38.559619, -121.426090))
                    .fillColor(0x33FFFF00)
                    .strokeWidth(0.75F)
                    //.setTag("PL5")
            )

        }
        //////////////////////
        //Parking Structure 2: if statements to create Polygon and detail how occupied the lot is by color
        //////////////////////

        if (d > 90 && d <= 100) {

            myMap3.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.559409, -121.420965),
                            LatLng(38.559650, -121.420015),
                            LatLng(38.559111, -121.419266),
                            LatLng(38.558766, -121.420606),
                            LatLng(38.558770, -121.420677),
                            LatLng(38.558817, -121.420693),
                            LatLng(38.558811, -121.420714))
                    .fillColor(0x33990000)
                    .strokeWidth(0.75F)
                    //.setTag("PL5")
            )


        } else if(d >= 0 && d <= 80) {

            myMap3.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.559409, -121.420965),
                            LatLng(38.559650, -121.420015),
                            LatLng(38.559111, -121.419266),
                            LatLng(38.558766, -121.420606),
                            LatLng(38.558770, -121.420677),
                            LatLng(38.558817, -121.420693),
                            LatLng(38.558811, -121.420714))
                    .fillColor(0x33009900)
                    .strokeWidth(0.75F)
                    //.setTag("PL5")
            )

        }

        else if(d > 80 && d <= 90) {

            myMap3.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.559409, -121.420965),
                            LatLng(38.559650, -121.420015),
                            LatLng(38.559111, -121.419266),
                            LatLng(38.558766, -121.420606),
                            LatLng(38.558770, -121.420677),
                            LatLng(38.558817, -121.420693),
                            LatLng(38.558811, -121.420714))
                    .fillColor(0x33FFFF00)
                    .strokeWidth(0.75F)
                    //.setTag("PL5")
            )

        }

        //////////////////////
        //Parking Structure 3: if statements to create Polygon and detail how occupied the lot is by color
        //////////////////////

        if (e > 90 && e <= 100) {


            myMap4.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.557533, -121.422433),
                            LatLng(38.557571, -121.422255),
                            LatLng(38.557627, -121.422276),
                            LatLng(38.557683, -121.422086),
                            LatLng(38.557624, -121.422071),
                            LatLng(38.557863, -121.421094),
                            LatLng(38.557808, -121.421146),
                            LatLng(38.557674, -121.420803),
                            LatLng(38.556670, -121.420361),
                            LatLng(38.556625, -121.420520),
                            LatLng(38.556706, -121.420557),
                            LatLng(38.556643, -121.422073),
                            LatLng(38.556632, -121.422149),
                            LatLng(38.556730, -121.422195),
                            LatLng(38.556746, -121.422120))
                    .fillColor(0x33990000)
                    .strokeWidth(0.75F)
                    //.setTag("PL5")
            )

        } else if(e >= 0 && e <= 80) {


            myMap4.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.557533, -121.422433),
                            LatLng(38.557571, -121.422255),
                            LatLng(38.557627, -121.422276),
                            LatLng(38.557683, -121.422086),
                            LatLng(38.557624, -121.422071),
                            LatLng(38.557863, -121.421094),
                            LatLng(38.557808, -121.421146),
                            LatLng(38.557674, -121.420803),
                            LatLng(38.556670, -121.420361),
                            LatLng(38.556625, -121.420520),
                            LatLng(38.556706, -121.420557),
                            LatLng(38.556643, -121.422073),
                            LatLng(38.556632, -121.422149),
                            LatLng(38.556730, -121.422195),
                            LatLng(38.556746, -121.422120))
                    .fillColor(0x33009900)
                    .strokeWidth(0.75F)
                    //.setTag("PL5")
            )
        }

        else if(e > 80 && e <= 90) {


            myMap4.addPolygon(PolygonOptions()
                    .clickable(true)
                    .add(
                            LatLng(38.557533, -121.422433),
                            LatLng(38.557571, -121.422255),
                            LatLng(38.557627, -121.422276),
                            LatLng(38.557683, -121.422086),
                            LatLng(38.557624, -121.422071),
                            LatLng(38.557863, -121.421094),
                            LatLng(38.557808, -121.421146),
                            LatLng(38.557674, -121.420803),
                            LatLng(38.556670, -121.420361),
                            LatLng(38.556625, -121.420520),
                            LatLng(38.556706, -121.420557),
                            LatLng(38.556643, -121.422073),
                            LatLng(38.556632, -121.422149),
                            LatLng(38.556730, -121.422195),
                            LatLng(38.556746, -121.422120))
                    .fillColor(0x33FFFF00)
                    .strokeWidth(0.75F)
                    //.setTag("PL5")
            )

        }*/


    }
}

