package com.example.localguide

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_forgot_password.*

class UserForgotPasswordActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_forgot_password)

        buttonSend.setOnClickListener {
            sendEmail()
        }
    }

    fun sendEmail() {
        val email = editTextSendEmail.text.toString()
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Sending...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext,"Email sent", Toast.LENGTH_SHORT).show()
                    val intentSigninActivity = Intent(this, SigninActivity::class.java)
                    startActivity(intentSigninActivity)
                }
                else {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext,"Sorry, email failed to send", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
