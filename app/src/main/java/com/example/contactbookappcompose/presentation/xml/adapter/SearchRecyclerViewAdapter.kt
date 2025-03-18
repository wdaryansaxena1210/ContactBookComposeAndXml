package com.example.contactbookappcompose.presentation.xml.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactbookappcompose.data.remote.Person
import com.example.contactbookappcompose.databinding.SearchItemBinding
import com.example.contactbookappcompose.presentation.xml.AddContactActivity
import com.example.contactbookappcompose.presentation.xml.SearchActivity

class SearchRecyclerViewAdapter(private val persons: List<Person>) : RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    // Define ViewHolder using ViewBinding
    inner class ViewHolder(private val binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // Bind the data to the views in the ViewHolder
        fun bind(person: Person) {
            // Access views via ViewBinding
            binding.tvFullName.setText("${person.firstName} ${person.lastName}")
            binding.tvNetId.setText("NetID: ${person.netid}")
            binding.tvEmail.setText("Email: ${person.email}")
            binding.tvMajor.setText("Major: ${person.major}")

            itemView.setOnClickListener {
                println(binding.tvFullName.text.toString() + " add to contact")
                val context = itemView.context
                val intent = Intent(context, AddContactActivity::class.java )
                intent.putExtra("firstName", person.firstName)
                intent.putExtra("lastName", person.lastName)
                intent.putExtra("email", person.email)
                intent.putExtra("major", person.major)

                context.startActivity(intent)

            }
        }
    }

    // Inflate the layout using ViewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = persons[position]
        holder.bind(person)
    }

    override fun getItemCount(): Int {
        return persons.size
    }
}