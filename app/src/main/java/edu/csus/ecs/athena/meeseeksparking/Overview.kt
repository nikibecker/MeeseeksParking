package edu.csus.ecs.athena.meeseeksparking

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View



class Overview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
    }
    fun goToOverview(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
    fun goToActivityLot5(view: View) {
        val intent = Intent(this, MapLot5::class.java)
        startActivity(intent)
    }
    fun goToActivityLot7(view: View) {
        val intent = Intent(this, MapLot7::class.java)
        startActivity(intent)
    }
    fun goToActivityPS1(view: View) {
        val intent = Intent(this, MapPS1::class.java)
        startActivity(intent)
    }
    fun goToActivityPS2(view: View) {
        val intent = Intent(this, MapPS2::class.java)
        startActivity(intent)
    }
    fun goToActivityPS3(view: View) {
        val intent = Intent(this, MapPS3::class.java)
        startActivity(intent)
    }
}