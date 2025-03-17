package com.example.contactbookappcompose.presentation.xml.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.databinding.ListItemContactBinding

class ContactListAdapter(private val contacts : List<Contact>, private val onClick: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    
    inner class ContactViewHolder(private val viewCardPerContact: ListItemContactBinding) : RecyclerView.ViewHolder(viewCardPerContact.root) {
        fun bind(contact: Contact, onClick: (Contact) -> Unit) {
            viewCardPerContact.contactName.text = contact.firstName + " " + contact.lastName
            viewCardPerContact.root.setOnClickListener { onClick(contact) }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = ListItemContactBinding.inflate( LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val contact = contacts[position]
        holder.bind(contact, onClick)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = contacts.size

}