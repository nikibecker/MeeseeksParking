package edu.csus.ecs.athena.meeseeksparking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_maps.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.location.LocationListener
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap

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

        button3.setOnClickListener{
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        button4.setOnClickListener{
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
        myMap = googleMap

        // val a: Int = 80
        //val b: Int = 100

        // Add a marker in Sydney and move the camera
        val SacState = LatLng(38.5611, -121.4240)
        myMap.addMarker(MarkerOptions().position(SacState).title("Sacramento State"))
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SacState, 15.5f))


        if (a > 90 && a <= 100) {
            myMap.addPolygon(PolygonOptions()

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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, lot5::class.java)
                startActivity(intent)

            }
          //  g.to(lot5:: class.java)


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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, lot5::class.java)
                startActivity(intent)

            }

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
                    //.t
            )
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, lot5::class.java)
                startActivity(intent)

            }

        }

        if (b > 90 && b <= 100) {

            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, lot7::class.java)
                startActivity(intent)

            }

        } else if(b >=0  && b <= 80) {

            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this,lot7::class.java)
                startActivity(intent)

            }

        }

        else if(b > 80  && b <= 90) {

            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, lot7::class.java)
                startActivity(intent)

            }

        }


        if (c > 90 && c <= 100) {

            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, ps1::class.java)
                startActivity(intent)

            }

        } else  if(c >= 0 && c <= 80) {

            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, ps1::class.java)
                startActivity(intent)

            }

        }
        else  if(c > 80  && c <= 90) {

            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, ps1::class.java)
                startActivity(intent)

            }

        }

        if (d > 90 && d <= 100) {

            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, ps2::class.java)
                startActivity(intent)

            }


        } else if(d >= 0 && d <= 80) {

            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, ps2::class.java)
                startActivity(intent)

            }

        }

        else if(d > 80 && d <= 90) {

            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, ps2::class.java)
                startActivity(intent)

            }

        }


        if (e > 90 && e <= 100) {


            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, ps3::class.java)
                startActivity(intent)

            }

        } else if(e >= 0 && e <= 80) {


            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, ps3::class.java)
                startActivity(intent)

            }

        }

        else if(e > 80 && e <= 90) {


            myMap.addPolygon(PolygonOptions()
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
            myMap.setOnPolygonClickListener {
                val intent = Intent(this, ps3::class.java)
                startActivity(intent)

            }

        }


    }
}

