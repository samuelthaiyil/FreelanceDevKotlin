package com.example.freelanceapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        val extras = intent.extras

        val uid = intent.extras?.getString("uid")!!

        var user: User? = null

        auth = Firebase.auth
        db = Firebase.firestore


        val fullName = findViewById<TextView>(R.id.full_name_text_2)
        val specialty = findViewById<TextView>(R.id.specialty_text)
        val educationListView = findViewById<ListView>(R.id.education_list)
        val projectsListView = findViewById<ListView>(R.id.proj_list_view)

        val education: ArrayList<Education> = ArrayList()
        val projects: ArrayList<Project> = ArrayList()
        val addEducation = findViewById<Button>(R.id.add_education)
        val addProjects = findViewById<Button>(R.id.add_projects)

        if (uid == auth.currentUser?.uid!!) {
            addEducation.visibility = View.VISIBLE
            addProjects.visibility = View.VISIBLE
        }


        education.add(Education("Harvard Law School", "Member of debate club"));
        education.add(Education("Stanford Medical School", "5.0 Gpa"));

        projects.add(Project("Harvard Law School", "Member of debate club"));
        projects.add(Project("Stanford Medical School", "5.0 Gpa"));

        val eduAdapter = EducationAdapter(this, education)
        educationListView.adapter = eduAdapter

        val projAdapter = ProjectAdapter(this, projects)
        projectsListView.adapter = projAdapter

        db.collection("Users").document(uid).get().addOnSuccessListener { doc ->
            if (doc != null) {
                fullName.text = doc.getString("fullName")!!
                specialty.text = doc.getString("specialty")!!

                db.collection("Users").document(uid).collection("Education").get().addOnSuccessListener {
                        docs ->

                    if (!docs.isEmpty()) {
                        for (doc in docs.documents) {
                            education.add(Education(doc.getString("school")!!, doc.getString("description")!!))
                        }

                        val arr = arrayOf("sd", "ds")
                        val adapter = ArrayAdapter(this,
                            android.R.layout.simple_list_item_1, arr)

                        val listView:ListView = findViewById(R.id.education_list)
                        listView.setAdapter(adapter)
                    }
                    db.collection("Users").document(uid).collection("Projects").get().addOnSuccessListener {
                            docs ->

                        if (!docs.isEmpty()) {
                            for (doc in docs.documents) {
                                projects.add(Project(doc.getString("name")!!, doc.getString("description")!!))
                            }
                        }
                    }
                }
            }
        }


    }

}