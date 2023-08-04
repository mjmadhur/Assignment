package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgotpass.*

class Forgotpass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpass)
        btn_reset.setOnClickListener {
            val email :String =et_email_forget_password.text.toString().trim{ it<= ' '}

            if (email.isEmpty())
            {
                Toast.makeText(this,"PLease Enter email",Toast.LENGTH_LONG).show()
            }
            else
            {
               // showLoadingDialog()
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task->
                    //hideLoading()

                    if(task.isSuccessful)
                    {
                        Toast.makeText(this,"Email sent successfully to reset your password !",
                            Toast.LENGTH_LONG).show()
                        this.onBackPressed()
                    }
                    else
                    {
                        Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_LONG)
                    }
                }
            }
        }


        }
    }
