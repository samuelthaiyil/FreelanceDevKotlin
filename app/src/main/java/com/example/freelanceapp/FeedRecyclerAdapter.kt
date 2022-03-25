package com.example.freelanceapp

import android.content.Intent
import android.icu.util.ULocale
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class FeedRecyclerAdapter(private val data: ArrayList<Post>): RecyclerView.Adapter<FeedRecyclerAdapter.ViewHolder>() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedRecyclerAdapter.ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.posts, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: FeedRecyclerAdapter.ViewHolder, position: Int) {
        auth = Firebase.auth
        db = Firebase.firestore


        holder.fullName.setOnClickListener() {

            val intent = Intent(holder.itemView.getContext(), ProfileActivity::class.java)
            intent.putExtra("uid", data.get(position).posterUID)
            holder.itemView.getContext().startActivity(intent)
        }

        holder.like.setOnClickListener() {
            if (holder.like.text.equals("Like")) {
                db.collection("Users").document(data.get(position).posterUID).collection("Posts")
                    .document(data.get(position).postID).update(
                    "likes", data.get(position).likes + 1
                )
                holder.like.text = "Liked"
            } else {
                db.collection("Users").document(data.get(position).posterUID).collection("Posts")
                    .document(data.get(position).postID).update(
                        "likes", data.get(position).likes - 1
                    )
                holder.like.text = "Like"
            }
        }

        holder.fullName.text = data.get(position).fullName
        holder.postContent.text = data.get(position).postContent

    }

    override fun getItemCount(): Int {
        Log.d("D", data.size.toString())
        return data.size
    }

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        var fullName: TextView
        var postContent: TextView
        var like: Button
        var comment: Button
        var share: Button

        init {
            fullName = item.findViewById(R.id.name_text)
            postContent = item.findViewById(R.id.post_content)
            like = item.findViewById(R.id.like_btn)
            comment = item.findViewById(R.id.comment_btn)
            share = item.findViewById(R.id.share_btn)

            fullName.setOnClickListener() {
                Log.d("D", "hety")
                val intent = Intent(item.getContext(), ProfileActivity::class.java);
                item.getContext().startActivity(intent)

            }
        }
    }
}