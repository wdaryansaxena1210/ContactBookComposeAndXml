package com.example.contactbookappcompose.presentation.xml

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactbookappcompose.R
import com.example.contactbookappcompose.databinding.ActivitySearchBinding
import com.example.contactbookappcompose.presentation.compose.ContactViewModel
import com.example.contactbookappcompose.presentation.xml.adapter.SearchRecyclerViewAdapter

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel : ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        //set-up activity
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel= ViewModelProvider(this).get(ContactViewModel::class.java)


        val searchRecyclerView : RecyclerView = binding.searchRecyclerView
        searchRecyclerView.layoutManager = LinearLayoutManager(this)

        val searchView = binding.searchView
        println("$searchView is the search view")
        val searchButton = binding.searchButton

        searchButton.setOnClickListener({it ->
            viewModel.searchContact(
                context = this,
                query = searchView.query.toString(),
                onResult = {listOfPersons ->
                    searchRecyclerView.adapter = SearchRecyclerViewAdapter(listOfPersons)
                }
            )
        })

    }
}