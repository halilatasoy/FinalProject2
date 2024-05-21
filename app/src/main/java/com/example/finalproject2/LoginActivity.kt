package com.example.finalproject2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent


// LoginActivity örneği
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        btnLogin.setOnClickListener {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(etUsername.text.toString(), etPassword.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Giriş başarılı, BudgetActivity'ye yönlendir
                        startActivity(Intent(this, BudgetActivity::class.java))
                        finish()
                    } else {
                        // Giriş başarısız, hata mesajı göster
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
