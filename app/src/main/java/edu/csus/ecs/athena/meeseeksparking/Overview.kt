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
        val intent = Intent(this, Overview::class.java)
        startActivity(intent)
    }
}