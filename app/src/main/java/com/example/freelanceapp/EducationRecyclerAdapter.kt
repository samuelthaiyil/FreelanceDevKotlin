package com.example.freelanceapp

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EducationRecyclerAdapter(private val data: ArrayList<Education>): RecyclerView.Adapter<EducationRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EducationRecyclerAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: EducationRecyclerAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        var school: TextView
        var description: TextView

        init {
            school = item.findViewById(R.id.name_text)
            description = item.findViewById(R.id.post_content)

        }
    }

}