package com.example.assignment



import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.assignment.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit


class Register :BaseActivity() {
    companion object {
        const val NAME = "Anonymous"
    }

    lateinit var mUserId: String
    lateinit var mauth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    lateinit var mFirestore: FirebaseFirestore

    var uidd: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        mFirestore = FirebaseFirestore.getInstance()

mauth=FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
                Log.d("GFG" , "onVerificationCompleted Success")
            }

            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("GFG" , "onVerificationFailed  $e")
            }

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("GFG","onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token

                // Start a new activity using intent
                // also send the storedVerificationId using intent
                // we will use this id to send the otp back to firebase
                val intent = Intent(applicationContext,otpver::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
                finish()
            }
        }
btn_signUp.setOnClickListener {

    registerUser()

}
    }
    private fun registerUser() {



          // showLoadingDialog()
            val email: String = et_email_signup.text.toString().trim { it <= ' ' }
            val password: String = et_password_signup.text.toString().trim { it <= ' ' }

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->


                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            val user=User(
                               et_name_signup.text.toString(),
                                et_email_signup.text.toString(),
                                firebaseUser.uid,
                                et_mobile.text.toString(),
                                "0"


                            )
                            FirestoreClass().registerUser(this,user)
                            login()
                            //FirebaseAuth.getInstance().signOut()

                        } else {

                            // If the registering is not successful then show error message.
                            Toast.makeText(this,task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                        }
                    })
        }



    fun userRegistrationSuccess(){


        Toast.makeText(this,"You Are Registered Successfully",Toast.LENGTH_LONG).show()
        val intent=Intent(this,otpver::class.java)
        intent.putExtra("name",et_name_signup.text.toString())
        startActivity(intent)
        //startActivity(Intent(this,otpver::class.java))
    }
    private fun login() {
       var  numbe:String = et_mobile.text.trim().toString()

        // get the phone number from edit text and append the country cde with it
        if (numbe.isNotEmpty()){
            numbe = "+91$numbe"
            sendVerificationCode(numbe)
        }else{
            Toast.makeText(this,"Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    // this method sends the verification code
    // and starts the callback of verification
    // which is implemented above in onCreate
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(mauth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
}




