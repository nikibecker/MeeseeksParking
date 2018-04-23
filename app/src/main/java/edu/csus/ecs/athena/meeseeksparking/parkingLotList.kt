package edu.csus.ecs.athena.meeseeksparking

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lot_list.*
import java.sql.ResultSet
import java.util.*

import de.codecrafters.tableview.TableView
import de.codecrafters.tableview.listeners.TableDataClickListener
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter
import android.widget.Toast






class parkingLotList : AppCompatActivity() {

    private var tableHeadersName : Array<String> = arrayOf("Lot Name", "Floor")
    private var tableRowsPop: ArrayList<Array<String>> = ArrayList<Array<String>>()
    private var lotNameSelected : String? = null
    private var lotFloorSelected : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lot_list)

        btnInsertScreen.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        btnUpdateScreen.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        btnDeleteRow.setOnClickListener {
            lotNameSelected?.toast(this)
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
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

        tb.addDataClickListener(TableDataClickListener (){
            fun onDataClicked(rowIndex: Int, clickedData: Array<Object>) {
                lotNameSelected = (clickedData as Array<String>)[0]
                lotFloorSelected = (clickedData)[1].toInt()
            }
        })

        querySQL.close()
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_LONG): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }
}