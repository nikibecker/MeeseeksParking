package edu.csus.ecs.athena.meeseeksparking

/**
 * Created by Jenny on 3/4/2018.
 */
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View



class MapPS2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_structure_2)
    }

    fun goToActivityPS2(view: View) {
        val intent = Intent(this, MapPS2::class.java)
        startActivity(intent)
    }
}