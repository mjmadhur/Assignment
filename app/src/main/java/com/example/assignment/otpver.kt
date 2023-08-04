package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_otpver.*

class otpver : BaseActivity() {
    lateinit var auth: FirebaseAuth
lateinit var db:FirebaseFirestore
lateinit var name:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpver)


        auth=FirebaseAuth.getInstance()
db= FirebaseFirestore.getInstance()
        // get storedVerificationId from the intent
        val storedVerificationId= intent.getStringExtra("storedVerificationId")

        // fill otp and call the on click on button

       btn_rre.setOnClickListener {
           showLoadingDialog()
            val otp = findViewById<EditText>(R.id.et_mpjn).text.trim().toString()
            if(otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
       startActivity(Intent(this,LoginActivity::class.java))
        super.onBackPressed()
    }
    // verifies if the code matches sent by firebase
    // if success start the new activity in our case it is main Activity
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   hideLoading()

                    val intent = Intent(this , LoginActivity::class.java)
                  //  intent.putExtra("name",intent.getStringExtra("name"))
                    intent.putExtra("mobii",intent.getStringExtra("mobil"))
Toast.makeText(this,"YOU can now login as registration is successful" +
        "Thank You!!",Toast.LENGTH_LONG).show()
                    startActivity(intent)

                } else {
                    hideLoading()
                    FirebaseAuth.getInstance().currentUser?.delete()
                    Toast.makeText(this,"Invalid otp you can try to register again",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,Register::class.java))
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}