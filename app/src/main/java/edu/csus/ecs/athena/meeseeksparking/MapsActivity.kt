package edu.csus.ecs.athena.meeseeksparking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_maps.*
import com.google.android.gms.maps.model.*
import java.sql.ResultSet
import java.util.*
import java.lang.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap
    private lateinit var myMap1: GoogleMap

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
        var sqlQueryStr = "SELECT parkinglot.LotName, parkinglot.SpotCount, parkinglot.SpotTaken, ST_ASText(lotgrid.Poly) FROM lotgrid, parkinglot WHERE parkinglot.LotName=lotgrid.LotName AND parkinglot.FloorNum=1"
        var querySQL = QuerySQL()
        var results : ResultSet = querySQL.execute(sqlQueryStr)

        val lotNames : MutableList<String> = ArrayList()
        val spotCounts : MutableList<Int> = ArrayList()
        val spotsTaken : MutableList<Int> = ArrayList()
        val polys : MutableList<String> = ArrayList()

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

        // Add a marker in Sacramento State and zoom in to view the campus positions well
        val SacState = LatLng(38.5611, -121.4240)
        myMap1.addMarker(MarkerOptions().position(SacState).title("Sacramento State"))
        myMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(SacState, 15.5f))

        //Start code for dynamic polygon creation
        var i = 0

        for (name in lotNames) {
            val xy : List<String> = polys.get(i).split(",")
            val latLongs : MutableList<LatLng> = ArrayList()
            for (xys in xy) {
                val x = xys.substringBefore(" ").toDouble()
                val y = xys.substringAfter(" ").toDouble()
                latLongs.add(LatLng(x, y))
            }
            sqlQueryStr = "SELECT SUM(SpotTaken), SUM(SpotCount) FROM parkinglot WHERE LotName= ? "
            querySQL = QuerySQL()
            var results2 : ResultSet = querySQL.execute(sqlQueryStr, name)

            var taken = 0
            var tot = 0
            while (results2.next())
            {
                taken = results2.getInt(1)
                tot = results2.getInt(2)
            }
            querySQL.close()

            var color: Int
            val a = taken.toDouble() / tot.toDouble()
            if (a >= .85)
                color = Color.RED
            else if(a < .50)
                color = Color.GREEN
            else
                color = Color.YELLOW

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
            val intentLotDisplay = Intent(this, LotDisplay::class.java)
            val ind = it.zIndex
            val lotName = lotNames.get(ind.toInt())
            System.out.println(lotName)
            intentLotDisplay.putExtra("lotNameData", lotName)
            intentLotDisplay.putExtra("floorNumData", 1)
            startActivity(intentLotDisplay)
        }
    }
}

