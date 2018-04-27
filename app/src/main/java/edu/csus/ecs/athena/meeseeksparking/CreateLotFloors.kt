package edu.csus.ecs.athena.meeseeksparking

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_create_lot_floors.*
import java.sql.ResultSet

class CreateLotFloors : AppCompatActivity() {

    private var choice : Int = 0
    private var choiceType : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_lot_floors)

        val dropDownLots = findViewById <Spinner> (R.id.ddLotName)
        val dropDownTypes = findViewById <Spinner> (R.id.ddParkingType)
        val etLotName = findViewById <EditText> (R.id.etLotName)
        val radioGroup = findViewById<RadioGroup>(R.id.rgLotChoice)
        val etFloorNum = findViewById<EditText>(R.id.etFloorNum)
        val etNumSpots = findViewById<EditText>(R.id.etNumSpots)

        populateDropdownLots(dropDownLots)
        populateDropdownTypes(dropDownTypes)
        btnSubmit.setOnClickListener {
            submit(radioGroup, dropDownLots)
        }

        radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            if (R.id.rbNameText == checkedId) {
                dropDownLots.isEnabled = false
                dropDownLots.isClickable = false
            }
            else if (R.id.rbSpinner == checkedId) {
                etLotName.isEnabled = false
                etLotName.isClickable = false
            }
        }


    }

    private fun populateDropdownLots(dropDownLots : Spinner) {
        //Connects and retrieves data about all the lots/structures
        var sqlQueryStr = "SELECT LotName FROM parkinglot"
        var querySQL = QuerySQL()
        var results : ResultSet = querySQL.execute(sqlQueryStr)

        //Creats a List of Lists populated with table's data
        val options = ArrayList<String>()
        while(results.next()) {
            var lotName : String = results.getString("LotName")
            if (lotName != null) {
                options.add(lotName)
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropDownLots.adapter = adapter

        dropDownLots.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                choice = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        querySQL.close()
    }

    private fun populateDropdownTypes(dropDownTypes : Spinner) {
        val listType = arrayListOf("Lot", "Structure")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listType)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropDownTypes.adapter = adapter

        dropDownTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                choiceType = dropDownTypes.getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun submit(radioGroup : RadioGroup, dropDownLots : Spinner) {
        var radioID = radioGroup.checkedRadioButtonId
        if (R.id.rbNameText == radioID)
            submitNewLot()
        else if (R.id.rbSpinner == radioID)
            submitAddFloor(dropDownLots)
    }

    private fun submitNewLot() {

        val dialog = AlertDialog.Builder(this).setTitle("Insert new lot?").setMessage("Lot: " + etLotName.text.toString() +
                "\n\tFloor : " + etFloorNum.text.toString() + "\n# of Spots: " + etNumSpots.text.toString())
                .setPositiveButton("Confirm", { dialog, i ->
                    ExecuteSQL().execute("INSERT INTO parkinglot VALUES (?, ?, ?, ?, ?, ?)", etLotName.text.toString(), etFloorNum.text.toString(),
                            etNumSpots.text.toString().toInt(),choiceType, 0, etNumSpots.text.toString().toInt())
                    finish()
                    startActivity(intent)
                    "Record Inserted".toast(this)
                })
                .setNegativeButton("Cancel", { dialog, i -> })
        dialog.show()
    }

    private fun submitAddFloor(dropDownLots : Spinner) {
        val lotName = dropDownLots.getSelectedItem().toString()
        val dialog = AlertDialog.Builder(this).setTitle("Insert new floor?").setMessage("Lot: " + lotName +
                "\n\tFloor : " + etFloorNum.text.toString() + "\n# of Spots: " + etNumSpots.text.toString())
                .setPositiveButton("Confirm", { dialog, i ->
                    ExecuteSQL().execute("INSERT INTO parkinglot VALUES (?, ?, ?, ?, ?, ?)", lotName, etFloorNum.text.toString(),
                            etNumSpots.text.toString().toInt(),choiceType, 0, etNumSpots.text.toString().toInt())
                    finish()
                    startActivity(intent)
                    "Record Inserted".toast(this)
                })
                .setNegativeButton("Cancel", { dialog, i -> })
        dialog.show()
    }

    private fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }
}
