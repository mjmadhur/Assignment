package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
       tv_signUp.setOnClickListener {
           startActivity(Intent(this,Register::class.java))
       }
        btn_signIn.setOnClickListener {
            logInRegisteredUser()
        }
        tv_forget_password.setOnClickListener {
            startActivity(Intent(this,Forgotpass::class.java))
        }
    }
    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            showLoadingDialog()

            val email =et_email_signTn.text.toString().trim { it <= ' ' }
            val password = et_password_signIn.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        hideLoading()
                        //Todo - send user_id to Main Activity
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        Toast.makeText(
                            this,
                            "You have logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra(
                            "user_id",
                            FirebaseAuth.getInstance().currentUser!!.uid
                        )
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                        finish()

                    } else {
                        hideLoading()
                        Toast.makeText(
                            this,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }
    }
    private fun validateLoginDetails(): Boolean {

        return when {
            TextUtils.isEmpty(et_email_signTn.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this,"Enter your email Id", Toast.LENGTH_LONG)
                false
            }

            else -> {
                true
            }
        }
    }
}