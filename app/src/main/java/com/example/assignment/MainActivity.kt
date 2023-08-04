package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mauth:FirebaseAuth
    var mnm:String="";
    var mohn:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mauth= FirebaseAuth.getInstance()
        tv1.text=FirebaseAuth.getInstance().currentUser?.email.toString()



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.eve,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.navigation_evvv){
            val intent=Intent(this,Profile::class.java)
            intent.putExtra("name",mnm)
            intent.putExtra("mobile",mohn)
startActivity(intent)
        }
        else if(item.itemId==R.id.navigation_logout){
mauth.signOut()

            startActivity(Intent(this,LoginActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}