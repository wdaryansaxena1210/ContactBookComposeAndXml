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
import kotlinx.coroutines.launch

class ContactListActivity : AppCompatActivity() {

    private val viewModel : ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {



        //create activity
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //activity created (think of an outer layout design created, children are yet to be populated



        lifecycleScope.launch {

            viewModel.state.collect{state->
                //collect the state. Whenever state changed, go back to the fragment which shows the List of contacts
                //not the best impl for when state changed cuz you changed contactDetails using EditContact button

                if(state.errorMessage != null){
                    println("error occurred : ${state.errorMessage}")
                }

                val contactListFragment = ContactListFragment(state.contacts).apply {
                    arguments=Bundle().apply {
                        putString("hello", "Hello, World!")
                    }
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentContainer, contactListFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}