package com.example.freelanceapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewCommentActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var comments: ArrayList<Comment>
    private lateinit var posterName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_comment)

        val extras = intent.extras

        val uid = intent.extras?.getString("uid")!!
        val postID = intent.extras?.getString("postID")!!
        val posterName = intent.extras?.getString("posterName")!!

        db = Firebase.firestore
        auth = Firebase.auth
        comments = ArrayList()

        val addComment = findViewById<Button>(R.id.add_comment)

        val commentListView = findViewById<ListView>(R.id.comment_list_view)
        val commentText = findViewById<TextView>(R.id.comment_text)

        addComment.setOnClickListener() {
            db.collection("Users").document(uid).collection("Posts").document(postID).collection("Comments").document().set(
                hashMapOf(
                     "commenterUID" to auth.currentUser?.uid!!,
                     "commenterName" to posterName,
                     "comment" to commentText.text.toString()
                 )
            ).addOnSuccessListener {
                commentListView.invalidateViews()
            }
        }

        db.collection("Users").document(uid).collection("Posts").document(postID).collection("Comments").get().addOnSuccessListener { docs ->
            for (document in docs.documents) {
                comments.add(Comment(document.getString("commenterUID")!!, document.getString("comment")!!, document.getString("commenterName")!!))
            }
            val cmtAdapter = CommentAdapter(this, comments)
            commentListView.adapter = cmtAdapter
        }

    }
}