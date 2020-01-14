package com.example.localguide

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_change_password.*
import java.util.regex.Pattern

class ChangePasswordActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private val user = mAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        buttonCancel.setOnClickListener {
            goBackEmailActivity()
        }

        buttonConfirm.setOnClickListener {

            val password = editTextNewPassword.text.toString().trim()
            val conPass = editTextConfirmPassword.text.toString().trim()

            if (password.isEmpty() || conPass.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Incompletely field filling in",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!password.equals(conPass)) {
                Toast.makeText(applicationContext, "Password doesn't match", Toast.LENGTH_SHORT)
                    .show()
            } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
                Toast.makeText(
                    this,
                    "Password should be at least 1 digit, 1 lower case, 1 upper case, 1 special character, and 8 characters",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val intent = Intent(this, User_Management::class.java)
                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Changing password...")
                progressDialog.setCancelable(false)
                progressDialog.show()

                if(user != null) {
                    user.updatePassword(password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            progressDialog.dismiss()
                            Toast.makeText(applicationContext,"Password changed Success", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }
                        else {
                            progressDialog.dismiss()
                            Toast.makeText(applicationContext,"Erorr to change", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }
    }

    fun goBackEmailActivity() {
        var intentEmail = Intent(this, SettingActivity::class.java)
        startActivity(intentEmail)
    }

    companion object {
        val PASSWORD_PATTERN =
            Pattern.compile("^" + "(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[!@#$%^&+=])" + ".{8,}" + "$")
    }
}
