package com.example.finalproject2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot

class TransactionAdapter(private val transactions: List<DocumentSnapshot>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        val income = transaction.getDouble("income")
        val expense = transaction.getDouble("expense")

        holder.incomeTextView.text = income?.toString() ?: "N/A"
        holder.expenseTextView.text = expense?.toString() ?: "N/A"
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val incomeTextView: TextView = itemView.findViewById(R.id.incomeTextView)
        val expenseTextView: TextView = itemView.findViewById(R.id.expenseTextView)
    }
}
