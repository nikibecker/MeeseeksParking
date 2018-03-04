package edu.csus.ecs.athena.meeseeksparking

/**
 * Created by Jenny on 3/4/2018.
 */
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View



class MapPS1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_structure_1)
    }

    fun goToActivityPS1(view: View) {
        val intent = Intent(this, MapPS1::class.java)
        startActivity(intent)
    }
}