package edu.csus.ecs.athena.meeseeksparking

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import java.sql.ResultSet

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnParkingMap.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            var sqlQueryStr = "SELECT * FROM users WHERE CSUSID = ? AND Password = ? AND PrivFlag = ?"
            var userID = findViewById <EditText> (R.id.etUserID).text.toString()
            var password = findViewById <EditText> (R.id.etPassword).text.toString()

            if (userID.length == 9 && userID.matches("[0-9]+".toRegex())) {

                var querySQL = QuerySQL()
                var results: ResultSet = querySQL.execute(sqlQueryStr, userID, password, 1)

                //Checks to see if returned table is empty, if it is,
                // it means the user either doesn't exist or doesn't have privilege
                if (results.next()) {
                    querySQL.close()
                    val intent = Intent(this, parkingLotList::class.java)
                    startActivity(intent)
                } else {
                    "Login Failed\nTry Again".toast(getApplicationContext())
                }
            }
            else
                "Invalid User ID".toast(getApplicationContext())
        }
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_LONG): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }
}