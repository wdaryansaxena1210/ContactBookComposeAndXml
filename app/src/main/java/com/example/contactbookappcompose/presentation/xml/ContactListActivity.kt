package com.example.contactbookappcompose.presentation.xml

import android.os.Bundle
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

class ContactListActivity : AppCompatActivity() {

    lateinit var contactRecyclerView: RecyclerView
    private val viewModel : ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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