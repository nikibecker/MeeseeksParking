package edu.csus.ecs.athena.meeseeksparking

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_overview.*


class Overview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        btnLot5.setOnClickListener{
            val intent = Intent(this, lot5::class.java)
            startActivity(intent)
        }

        btnLot7.setOnClickListener{
            val intent = Intent(this, lot7::class.java)
            startActivity(intent)
        }

        btnPS1.setOnClickListener{
            val intent = Intent(this, ps1::class.java)
            startActivity(intent)
        }
        btnPS2.setOnClickListener{
            val intent = Intent(this, ps2::class.java)
            startActivity(intent)
        }
        btnPS3.setOnClickListener{
            val intent = Intent(this, ps3::class.java)
            startActivity(intent)
        }

    }
    fun goToOverview(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
}