package com.example.finalproject2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.*

class BudgetActivity : AppCompatActivity() {

    private lateinit var etIncome: EditText
    private lateinit var etExpense: EditText
    private lateinit var btnSave: Button
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        etIncome = findViewById(R.id.etIncome)
        etExpense = findViewById(R.id.etExpense)
        btnSave = findViewById(R.id.btnSave)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser

        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val income = etIncome.text.toString().trim()
        val expense = etExpense.text.toString().trim()

        if (income.isEmpty() || expense.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            return
        }

        val incomeDouble = income.toDoubleOrNull()
        val expenseDouble = expense.toDoubleOrNull()

        if (incomeDouble == null || expenseDouble == null) {
            Toast.makeText(this, "Lütfen geçerli sayılar girin", Toast.LENGTH_SHORT).show()
            return
        }

        val data = hashMapOf(
            "income" to incomeDouble,
            "expense" to expenseDouble,
            "date" to Date(),
            "userId" to currentUser?.uid
        )

        db.collection("budgets").add(data)
            .addOnSuccessListener { _ ->
                Toast.makeText(this, "Kayıt başarılı!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, TransactionListActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Kayıt başarısız: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
