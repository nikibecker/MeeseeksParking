package edu.csus.ecs.athena.meeseeksparking

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_overview.*


class Overview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        button8.setOnClickListener{
            val intent = Intent(this, lot5::class.java)
            startActivity(intent)
        }

        button9.setOnClickListener{
            val intent = Intent(this, lot7::class.java)
            startActivity(intent)
        }

        button10.setOnClickListener{
            val intent = Intent(this, ps1::class.java)
            startActivity(intent)
        }
        button12.setOnClickListener{
            val intent = Intent(this, ps2::class.java)
            startActivity(intent)
        }
        button11.setOnClickListener{
            val intent = Intent(this, ps3::class.java)
            startActivity(intent)
        }

    }
    fun goToOverview(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
    /*
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
    */
}