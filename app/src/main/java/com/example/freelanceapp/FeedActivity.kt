package com.example.freelanceapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FeedActivity: AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<FeedRecyclerAdapter.ViewHolder>? = null
    private lateinit var posts: ArrayList<Post>
    private lateinit var likedPosts: ArrayList<String>
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed)

        db = Firebase.firestore
        auth = Firebase.auth
        posts = ArrayList()
        likedPosts = ArrayList()

        db.collection("Users").document(auth.currentUser?.uid!!).collection("Posts").get().addOnSuccessListener { result ->
            for (doc in result.documents) {
                posts.add(Post(doc.getString("fullName")!!, doc.getString("posterUID")!!, doc.getString("postContent")!!, doc.getDouble("likes")!!, doc.getDouble("comments")!!, doc.getDouble("shares")!!, doc.id))
                Log.d("D", doc.id)
            }

            db.collection("Users").document(auth.currentUser?.uid!!).collection("LikedPosts").get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("test", document.id)
                    likedPosts.add(document.id)
                }
                layoutManager = LinearLayoutManager(this)
                val postsView = findViewById<RecyclerView>(R.id.posts_view)

                posts.reverse()

                postsView.layoutManager = layoutManager
                adapter = FeedRecyclerAdapter(posts, likedPosts)
                postsView.adapter = adapter
            }
        }

        val createPost = findViewById<FloatingActionButton>(R.id.create_post_btn)

        createPost.setOnClickListener {
            val intent = Intent(this, NewPostActivity::class.java)
            startActivity(intent)
        }
    }
}