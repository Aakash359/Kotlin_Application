package com.example.kotline

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var reg: Button
    lateinit var forgot: TextView
    lateinit var editText: EditText
    lateinit var editpass: EditText
    lateinit var string: String
    lateinit var textView: TextView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var mindOrks: MindOrks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     sharedPreferences = this.getSharedPreferences("MyToken", Context.MODE_PRIVATE)

        button = findViewById(R.id.btn_login)
        reg = findViewById(R.id.btn_reg)
        editText= findViewById(R.id.edit_email)
        editpass = findViewById(R.id.edit_pass)
        forgot = findViewById(R.id.forgot)

        forgot.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
            finish()
        }

        button.setOnClickListener {


            if(editText.text.toString()==""){
                Toast.makeText(applicationContext,"Enter Email",Toast.LENGTH_SHORT).show()

            }
            else  if(editpass.text.toString()==""){
                Toast.makeText(applicationContext,"Enter password",Toast.LENGTH_SHORT).show()

            }
            else if(isValidPassword(editpass.text.toString())){

                Toast.makeText(applicationContext,"Login Successfully",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Home::class.java)
                intent.putExtra("keyIdentifier", editText.text.toString())
                startActivity(intent)
                finish()
                }else{
                Toast.makeText(applicationContext,"Invalid Password",Toast.LENGTH_SHORT).show()
                }




        }

          reg.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

        init()


    }

    private fun isValidPassword(password: String?): Boolean {
        password?.let {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
            val passwordMatcher = Regex(passwordPattern)

            return passwordMatcher.find(password) != null
        } ?: return false

    }

    private fun init() {
        val sharedNameValue = sharedPreferences.getString("name_key","defaultname")


        if(sharedNameValue!="defaultname"){
            val intent = Intent(this, Home::class.java)
            intent.putExtra("keyIdentifier",sharedNameValue)
            startActivity(intent)
            finish()
        }

    }
}