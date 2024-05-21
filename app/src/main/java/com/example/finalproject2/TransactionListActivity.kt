package com.example.finalproject2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.Button
import android.widget.Toast
import java.util.Date

class TransactionListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    private lateinit var transactionListAdapter: TransactionListAdapter
    private lateinit var btnBack: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        btnBack = findViewById(R.id.btnBack)
        btnLogout = findViewById(R.id.btnLogout)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser

        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, BudgetActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        fetchTransactions()
    }

    private fun fetchTransactions() {
        db.collection("budgets")
            .whereEqualTo("userId", currentUser?.uid)
            .get()
            .addOnSuccessListener { documents ->
                val transactions = mutableListOf<Transaction>()
                for (document in documents) {
                    val income = document.get("income")?.toString()?.toDoubleOrNull() ?: 0.0
                    val expense = document.get("expense")?.toString()?.toDoubleOrNull() ?: 0.0
                    val date = document.getDate("date") ?: Date()
                    transactions.add(Transaction(income, expense, date))
                }
                transactionListAdapter = TransactionListAdapter(transactions)
                recyclerView.adapter = transactionListAdapter
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to fetch transactions: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
