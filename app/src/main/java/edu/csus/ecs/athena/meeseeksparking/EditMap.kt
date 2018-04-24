package edu.csus.ecs.athena.meeseeksparking

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_edit_map.*
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_overview.*

class EditMap : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_map)

        btnInsert.setOnClickListener{
            val intent = Intent(this, lot5::class.java)
            startActivity(intent)
        }
        btnUpdate.setOnClickListener{
            val intent = Intent(this, lot5::class.java)
            startActivity(intent)
        }
        btnDelete.setOnClickListener{
            val intent = Intent(this, lot5::class.java)
            startActivity(intent)
        }

        /*I need a way to display the lots and structures in the leftmost column
          in a way that makes each one SELECTABLE. When one is clicked, its spots
          show up, along with their floors (only 1 for lots). Everything has to
          be SELECTABLE in order for update and delete buttons to work as planned.
          Update and Delete will be grayed out at first or if simpler we can just
          make a popup that says it won't do anything unless something is selected.*/
    }


}
