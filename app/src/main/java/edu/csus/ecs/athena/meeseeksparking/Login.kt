package edu.csus.ecs.athena.meeseeksparking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_login.*
import android.os.AsyncTask
import android.util.Log

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException
import net.schmizz.sshj.transport.verification.PromiscuousVerifier
import java.io.IOException
import java.net.ConnectException
import java.util.concurrent.TimeUnit

var loginCred : String = "meeseeks"
var passwordCred : String = "ynasujge"
var hostName : String = "athena.csus.edu"

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
            var task : AsyncRunSSH = AsyncRunSSH()
            task.execute("","","")
        }
    }
}

private class AsyncRunSSH : AsyncTask<String, String, String> () {
    override fun doInBackground(vararg p0: String?): String {
        var testString : String = ""
        try {
            var client = SSHClient()
            client.addHostKeyVerifier(PromiscuousVerifier())
            client.connect(hostName, 22)
            System.out.println("\nClient Connected")
            client.authPassword(loginCred, passwordCred)

            try {
                var session = client.startSession()
                System.out.println("\nSession Started")
                try {
                    //var command: Session.Command = session.exec("cd html; ls")
                    var command: Session.Command = session.exec("mysql -h athena -u walle_user -p; walle_db; use walle; show tables;")
                    testString = IOUtils.readFully(command.getInputStream()).toString()
                    System.out.println("Aviv: \n" + testString)
                    command.join(5, TimeUnit.SECONDS)
                } catch (e: ConnectException) {
                    Log.e("Session.exec ConnError", e.toString())
                } catch (e: TransportException) {
                    Log.e("Session.exec TransError", e.toString())
                } finally {
                    session.close()
                }
            } finally {
                client.disconnect()
            }
        } catch (e: IOException) {
            Log.e("Login Error", e.toString())
        }
        System.out.println("Aviv2: \n" + testString)
        return testString
    }

    // Update the final status by overriding the OnPostExecute method.
    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        //TODO : open the next page/activity if login sucessful
    }
}
