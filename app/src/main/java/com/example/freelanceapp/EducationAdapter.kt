package com.example.freelanceapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.freelanceapp.Education

class EducationAdapter(private val context: Context,
                               private val data: ArrayList<Education>) : BaseAdapter() {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(pos: Int): Any {
        return data.get(pos)
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.education_list_view_item, parent, false)

        val educationItem = getItem(pos) as Education

        val title = rowView.findViewById<TextView>(R.id.edu_title)
        val desc = rowView.findViewById<TextView>(R.id.edu_desc)

        title.text = educationItem.school
        desc.text = educationItem.description

        return rowView
    }
}