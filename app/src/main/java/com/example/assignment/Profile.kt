package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.view.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.HashMap

class Profile : BaseActivity() {
    private val mFirestore = FirebaseFirestore.getInstance()
    lateinit var mphn:String
    lateinit var mname: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)



showLoadingDialog()
        tv_email.setText(FirebaseAuth.getInstance().currentUser?.email.toString())
        mFirestore.collection("User")
            .get().addOnSuccessListener {document->
                hideLoading()
                for(documents in document){
                    if(documents.get("email")!!.equals(FirebaseAuth.getInstance().currentUser?.email)){
                        mphn=documents.get("mobilenumber").toString()
                        mname=documents.get("name").toString()
                    }
                }
                tv_role.setText(mphn)
                et_nm.setText(mname)
                Log.e("mj",mphn)
            }
        btn_update.setOnClickListener {

            val mp = HashMap<String, String>()
            mp["name"] = et_nm.text.toString()
            mp["email"] = tv_email.text.toString()
            mp["mobilenumber"]=tv_role.text.toString()
            mp["id"] = FirebaseAuth.getInstance().uid.toString()
            showLoadingDialog()
            mFirestore.collection("User").document(FirebaseAuth.getInstance().uid.toString()).set(mp).addOnSuccessListener {
           hideLoading()
            Toast.makeText(this,"Update Successful",Toast.LENGTH_LONG).show()
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
        }



    }
