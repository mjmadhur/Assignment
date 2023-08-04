package com.example.assignment

import android.util.Log
import com.example.assignment.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity:Register, userInfo: User) {

        //the "users" is a collection name.
        mFirestore.collection("User")
            //Document Id for users fields.
            .document(userInfo.id!!)
            //here the userInfo are field and the setOption is set to merge.
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                //Here call a function of base activity for transferring the result to it.
activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
               activity.hideLoading()
                Log.e(
                  activity.javaClass.simpleName,
                    "Error while registering the user_id.",
                    e
                )
            }

    }


  fun getCurrentUserId(): String {
        //An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        //A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }

        return currentUserId
    }

}