package edu.csus.ecs.athena.meeseeksparking

/**
 * Created by Jenny on 3/4/2018.
 */
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View



class MapLot7 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_lot_7)
    }

    fun goToActivityLot7(view: View) {
        val intent = Intent(this, MapLot7::class.java)
        startActivity(intent)
    }
}