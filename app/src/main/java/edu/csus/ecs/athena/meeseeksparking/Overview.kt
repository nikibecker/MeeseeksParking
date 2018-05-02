package edu.csus.ecs.athena.meeseeksparking

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import de.codecrafters.tableview.TableView
import de.codecrafters.tableview.listeners.TableDataClickListener
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter
import kotlinx.android.synthetic.main.activity_overview.*
import java.sql.ResultSet
import java.util.ArrayList


class Overview : AppCompatActivity() {

    private var tableHeadersName : Array<String> = arrayOf("Lot Name", "Floor", "Free Spots")
    private var tableRowsPop: ArrayList<Array<String>> = ArrayList<Array<String>>()
    private var lotNameSelected : String? = null
    private var lotFloorSelected : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        populateTable()

    }

    //Retreives data from database and populates the tables
    private fun populateTable () {
        //Connects and retrieves data about all the lots/structures
        var sqlQueryStr = "SELECT LotName, FloorNum, SpotAvail FROM parkinglot"
        var querySQL = QuerySQL()
        var results : ResultSet = querySQL.execute(sqlQueryStr)

        //Creats a List of Lists populated with table's data
        var i : Int = 0
        while(results.next()) {
            var lotName : String = results.getString("LotName")
            var floorNUm : Int = results.getInt("FloorNum")
            var available : Int = results.getInt("SpotAvail")
            if (lotName != null && floorNUm != null) {
                var tempList : Array<String> = arrayOf(lotName,floorNUm.toString(),available.toString())
                tableRowsPop.add(i,tempList)
                i++
            }
        }

        val tb = findViewById <TableView<Array<String>>> (R.id.tblLotAvaillableList)
        tb.setColumnCount(3)
        tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"))

        tb.setHeaderAdapter(SimpleTableHeaderAdapter(this, *tableHeadersName))
        tb.setDataAdapter(SimpleTableDataAdapter(this, tableRowsPop))

        tb.addDataClickListener(rowClickListener())

        querySQL.close()
    }

    //Creates a row click listner for the table
    private inner class rowClickListener : TableDataClickListener<Array<String>> {

        override fun onDataClicked(rowIndex: Int, clickedData: Array<String>) {
            lotNameSelected = (clickedData)[0]
            lotFloorSelected = (clickedData)[1].toInt()
            //val intentDelete = Intent(this, CreateLotFloors::class.java)
            //startActivity(intentDelete)
        }
    }
}