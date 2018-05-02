/*package edu.csus.ecs.athena.meeseeksparking

import android.view.ViewGroup
import android.app.Activity
import android.view.View
import android.widget.BaseAdapter
import android.widget.ImageView

class CustomAdapter(private val context: Activity, private val imageIdList: Array)
    : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.activity_lot_display,null)
        val imageView = rowView.findViewById(R.id.image_item)
        imageView.setImageResource(imageIdList[p0])
        return rowView
    }
    override fun getItem(p0: Int): Any {
        return imageIdList.get(p0)
    }
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getCount(): Int {
        return imageIdList.size
    }
}
*/