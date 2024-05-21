package com.example.finalproject2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class TransactionListAdapter(private val transactions: List<Transaction>) : RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int = transactions.size

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val incomeTextView: TextView = itemView.findViewById(R.id.incomeTextView)
        private val expenseTextView: TextView = itemView.findViewById(R.id.expenseTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val totalTextView: TextView = itemView.findViewById(R.id.totalTextView)

        fun bind(transaction: Transaction) {
            incomeTextView.text = transaction.income.toString()
            expenseTextView.text = transaction.expense.toString()
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateTextView.text = sdf.format(transaction.date)

            val total = transaction.income - transaction.expense
            totalTextView.text = "Total: $total"
        }
    }
}
