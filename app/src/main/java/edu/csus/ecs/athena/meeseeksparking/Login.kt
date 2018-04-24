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
            //var temp = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)"
            //ExecuteSQL().execute(temp, "098767890", "Test", "Me", 0, 0, "passwordTest")
            var sqlQueryStr = "SELECT * FROM users WHERE CSUSID = ? AND Password = ? AND PrivFlag = ?"
            var userID = findViewById <EditText> (R.id.etUserID)
            var password = findViewById <EditText> (R.id.etPassword)
            var querySQL = QuerySQL()
            var results : ResultSet = querySQL.execute(sqlQueryStr,userID.text.toString(), password.text.toString(), 1)

            //Checks to see if returned table is empty, if it is,
            // it means the user either doesn't exist or doesn't have privilege
            if(results.next()) {
                querySQL.close()
                val intent = Intent(this, parkingLotList::class.java)
                startActivity(intent)
            }
            else {
                "Login Failed\nTry Again".toast(getApplicationContext())
            }
        }
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_LONG): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }
}