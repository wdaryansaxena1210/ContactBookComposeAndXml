package com.example.contactbookappcompose.presentation.xml

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactbookappcompose.R
import com.example.contactbookappcompose.presentation.compose.ContactViewModel
import com.example.contactbookappcompose.presentation.xml.adapter.ContactListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactListActivity : AppCompatActivity() {

    lateinit var contactRecyclerView: RecyclerView
    private val viewModel : ContactViewModel by viewModels()
    lateinit var addContactButton : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addContactButton = findViewById(R.id.btnAddContact)
        addContactButton.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }


        contactRecyclerView = findViewById(R.id.contactRecyclerView)
        contactRecyclerView.layoutManager = LinearLayoutManager(this@ContactListActivity)

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect{state->
                if(state.errorMessage != null){
                    println("error occurred : ${state.errorMessage}")
                }
                contactRecyclerView.adapter = ContactListAdapter(contacts = state.contacts, onClick = { println(it.id) })
            }
        }
    }
}