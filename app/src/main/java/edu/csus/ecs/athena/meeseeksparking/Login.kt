package edu.csus.ecs.athena.meeseeksparking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_login.*
import android.os.AsyncTask
import android.util.Log

import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.common.IOUtils
import net.schmizz.sshj.connection.channel.direct.Session
import net.schmizz.sshj.transport.TransportException
import net.schmizz.sshj.transport.verification.PromiscuousVerifier
import java.io.IOException
import java.net.ConnectException
import java.util.concurrent.TimeUnit
import net.schmizz.sshj.connection.channel.direct.Session.Shell

import net.sf.expectit.ExpectBuilder
import net.sf.expectit.Expect
import net.sf.expectit.filter.Filters.removeColors
import net.sf.expectit.filter.Filters.removeNonPrintable
import net.sf.expectit.filter.Filters.replaceInString
import net.sf.expectit.matcher.Matchers.contains
import net.sf.expectit.matcher.Matchers.anyString
import net.sf.expectit.matcher.Matchers.regexp
//import javax.swing.text.html.HTML.Attribute.PROMPT
//import spock.util.matcher.HamcrestSupport.expect




var loginCred : String = "meeseeks"
var passwordCred : String = "Senior_Project18"
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
        Log.d("SSH", "console started")
        try {
            var client = SSHClient()
            client.addHostKeyVerifier(PromiscuousVerifier())
            client.connect(hostName, 22)
            Log.d("SSH", "\nClient Connected")
            client.authPassword(loginCred, passwordCred)
            try {
                //var session = client.startSession()
                val session = client.startSession()
                Log.d("SESSION", "\nSession Started")
                session.allocateDefaultPTY()
                val shell = session.startShell()
                val expect = ExpectBuilder()
                    .withOutput(shell.getOutputStream())
                    .withInputs(shell.getInputStream(), shell.getErrorStream())
                    .withEchoInput(System.out)
                    .withEchoOutput(System.err)
                    .withInputFilters(removeColors(),
                            removeNonPrintable())
                            //replaceInString("show tables;", ""),
                            //replaceInString("mysql>", ""),
                            //replaceInString("-", ""),
                            //replaceInString("+", ""),
                            //replaceInString("|", ", "))
                            //replaceInString("\n", ""))
                    .withExceptionOnFailure()
                    .build()
                try {
                    expect.sendLine("mysql -h athena -u walle_user -p")
                    expect.expect(contains("Enter password:"))
                    expect.sendLine("walle_db")
                    expect.sendLine("use walle")
                    expect.expect(contains("Database changed"))
                    expect.sendLine("show tables;")
                    testString = expect.expect(contains("+")).input
                }
                /*try {
                    //var command: Session.Command = session.exec("cd html; ls")
                    var command: Session.Command = session.exec("mysql -h athena -u walle_user -p; walle_db; use walle; show tables;")
                    testString = IOUtils.readFully(command.getInputStream()).toString()
                    Log.d("SESSION","Aviv: \n" + testString)
                    command.join(5, TimeUnit.SECONDS)
                } */
                catch (e: ConnectException) {
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
