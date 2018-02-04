package edu.csus.ecs.athena.meeseeksparking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

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
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val SacState = LatLng(38.5611, -121.4240)
        mMap.addMarker(MarkerOptions().position(SacState).title("Sacramento State"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SacState, 15.5f))
    }
}
