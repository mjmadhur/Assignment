package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Splash : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    var bool:String="0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        db= FirebaseFirestore.getInstance()
db.collection("User").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).get().addOnSuccessListener {documents->
    bool=documents.get("otp").toString()
}

                Handler(Looper.getMainLooper()).postDelayed({
                    var Currentuserid=FirestoreClass().getCurrentUserId()

                    if (Currentuserid=="") {
                        // Start the Main Activity
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        // Start the Intro Activity
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                }, 1500)

            }
    }


