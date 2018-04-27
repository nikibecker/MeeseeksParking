package edu.csus.ecs.athena.meeseeksparking

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lot_list.*
import java.sql.ResultSet
import java.util.*

import de.codecrafters.tableview.TableView
import de.codecrafters.tableview.listeners.TableDataClickListener
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter
import android.view.View
import android.widget.*


class parkingLotList : AppCompatActivity() {

    private var tableHeadersName : Array<String> = arrayOf("Lot Name", "Floor")
    private var tableRowsPop: ArrayList<Array<String>> = ArrayList<Array<String>>()
    private var lotNameSelected : String? = null
    private var lotFloorSelected : Int = -1
    private val deleteSQL : String = "DELETE FROM parkinglot WHERE LotName = ? AND FloorNum = ?"
    private var choice : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lot_list)

        //Creates the Drop Down menu
        val dropDown = findViewById <Spinner> (R.id.ddEditChoice)
        val adapter = ArrayAdapter.createFromResource(this, R.array.edit_choice_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropDown.adapter = adapter

        dropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                choice = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        //Connects and retrieves data about all the lots/structures
        var sqlQueryStr = "SELECT LotName, FloorNum FROM parkinglot"
        var querySQL = QuerySQL()
        var results : ResultSet = querySQL.execute(sqlQueryStr)

        //Creats a List of Lists populated with table's data
        var i : Int = 0
        while(results.next()) {
            var lotName : String = results.getString("LotName")
            var floorNUm : Int = results.getInt("FloorNum")
            if (lotName != null && floorNUm != null) {
                var tempList : Array<String> = arrayOf(lotName,floorNUm.toString())
                tableRowsPop.add(i,tempList)
                i++
            }
        }

        val tb = findViewById <TableView<Array<String>>> (R.id.tblLotList)
        tb.setColumnCount(2)
        tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"))

        tb.setHeaderAdapter(SimpleTableHeaderAdapter(this, *tableHeadersName))
        tb.setDataAdapter(SimpleTableDataAdapter(this, tableRowsPop))

        tb.addDataClickListener(CarClickListener())

        querySQL.close()

        //Creates an on click listner for the Go button
        btnGo.setOnClickListener {
            if (lotNameSelected != null && lotFloorSelected != -1) {
                when(choice) {
                    0 -> deleteLot()
                    /*1 ->
                    2 ->
                    3 ->
                    4 ->
                     */
                }
            }
            else
                "No lot/floor was selected".toast(getApplicationContext())
        }
    }

    //Creates an Alert to confirm that the user wants to delete the
    //data from the server permanently
    private fun deleteLot(){
        val dialog = AlertDialog.Builder(this).setTitle("Delete Record").setMessage("permanently delete: \nParking Lot: " + lotNameSelected +
                                                                                            "\nFloor: " + lotFloorSelected)
                .setPositiveButton("Confirm", { dialog, i ->
                    ExecuteSQL().execute(deleteSQL, lotNameSelected, lotFloorSelected)
                    finish()
                    startActivity(getIntent())
                    "Record deleted".toast(this)
                })
                .setNegativeButton("Cancel", { dialog, i -> })
        dialog.show()
    }


    private fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    //Inner class that gets called when a user clicks on a row inside the tbale
    // it saves the row's 2 datafields (lotName, floorNum) into 2 fields
    private inner class CarClickListener : TableDataClickListener<Array<String>> {

        override fun onDataClicked(rowIndex: Int, clickedData: Array<String>) {
            lotNameSelected = (clickedData)[0]
            lotFloorSelected = (clickedData)[1].toInt()
            //(lotNameSelected + "\nfloor: " + lotFloorSelected.toString()).toast(getApplicationContext())
            findViewById <TextView> (R.id.tvLotName).text = lotNameSelected
            findViewById <TextView> (R.id.tvFloorNum).text = lotFloorSelected.toString()
        }
    }
}