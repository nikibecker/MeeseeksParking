package edu.csus.ecs.athena.meeseeksparking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnParkingMap.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            SQLInsert().execute("users","CSUSID","FirstName","LastName","PrivFlag","SpotPriv","Password",
                    "987987987","Aviv","Test","0","0","Password")
        }
    }
}