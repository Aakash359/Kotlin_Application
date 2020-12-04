package com.example.kotline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ForgotPassword : AppCompatActivity() {
    lateinit var reset: Button
    lateinit var cur_pas:EditText
    lateinit var new_pas : EditText
    lateinit var  con_pas: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        reset = findViewById(R.id.btn_reset)
        cur_pas = findViewById( R.id.current_pass)
        new_pas = findViewById(R.id.new_pass)
        con_pas = findViewById(R.id.con_pass)

        reset.setOnClickListener {
            Toast.makeText(applicationContext,"test",Toast.LENGTH_SHORT).show()
        }
    }
}