package com.example.freelanceapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CommentAdapter(private val context: Context,
                     private val data: ArrayList<Comment>) : BaseAdapter() {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        Log.d("HEY", data.size.toString())

        return data.size
    }

    override fun getItem(pos: Int): Any {
        return data.get(pos)
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.profile_list_view_item, parent, false)

        val commentItem = getItem(pos) as Comment

        val title = rowView.findViewById<TextView>(R.id.item_title)
        val desc = rowView.findViewById<TextView>(R.id.item_desc)

        title.text = commentItem.commenterName.plus(" says")
        desc.text = commentItem.comment


        return rowView
    }
}