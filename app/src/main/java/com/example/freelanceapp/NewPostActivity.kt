package com.example.freelanceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewPostActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_post)

        auth = Firebase.auth
        db = Firebase.firestore

        val post = findViewById<Button>(R.id.post_btn)
        val postContent = findViewById<TextView>(R.id.post_content_text)

        post.setOnClickListener() {
            if (postContent.text.isNotEmpty()) {
                var fullName: String
                db.collection("Users").document(auth.currentUser?.uid!!).get().addOnSuccessListener { doc ->
                    if (doc != null) {
                        fullName = doc.getString("fullName")!!
                        db.collection("Users").document(auth.currentUser?.uid!!).collection("Posts").document().set(
                            hashMapOf(
                                "fullName" to fullName,
                                "posterUID" to auth.currentUser?.uid!!,
                                "postContent" to postContent.text.toString(),
                                "likes" to 0,
                                "comments" to 0,
                                "shares" to 0
                            )
                        ).addOnSuccessListener { result ->
                            val intent = Intent(this, FeedActivity::class.java)
                            startActivity(intent)

                            Toast.makeText(
                                baseContext, "Post uploaded. ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    baseContext, "Posts cannot be empty. ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}