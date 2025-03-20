package com.example.contactbookappcompose.presentation.xml

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactbookappcompose.R
import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.databinding.FragmentContactDetailsBinding
import com.example.contactbookappcompose.databinding.FragmentContactListBinding
import com.example.contactbookappcompose.presentation.compose.ContactViewModel
import com.example.contactbookappcompose.presentation.xml.adapter.ContactListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ContactListFragment(private var contacts: List<Contact>) : Fragment() {

    private lateinit var viewModel: ContactViewModel
    private lateinit var binding: FragmentContactListBinding
    private lateinit var addContactButton: FloatingActionButton
    private lateinit var contactRecyclerView: RecyclerView
    private lateinit var dummyData: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //retrieve any info from bundle.arguments .... i.e. "arguments = Bundle().apply { putString("name", value) }"
        //did we put any arguments into the bundle when calling ContactListFragment? no
        arguments?.let {
            dummyData = it.getString("hello").toString()
            println(it)
            println(dummyData)
        }
        viewModel = ViewModelProvider(requireActivity())[ContactViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    //manually override onViewCreated to actually populate data into blank canvas layout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //floating action add contact button
        addContactButton = binding.btnAddContact
        addContactButton.setOnClickListener {
            val intent = Intent(context, AddContactActivity::class.java)
            startActivity(intent)
        }


        val searchButton : FloatingActionButton = binding.btnSearch
        searchButton.setOnClickListener(fun(v :View){
            val searchActivityIntent = Intent(context, SearchActivity::class.java)
            startActivity(searchActivityIntent)
        })


        contactRecyclerView = binding.contactRecyclerView
        contactRecyclerView.layoutManager = LinearLayoutManager(context)

        contactRecyclerView.adapter = ContactListAdapter(
            contacts = contacts,
            onClick = { contact ->
                val fragment = ContactDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString("contactId", contact.id.toString())
                        println("putting ${contact.id.toString()} into bundle")
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.FragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
                println("onClick Detail contact")
            }
        )
    }
}


////floating action add contact button
//addContactButton = findViewById(R.id.btnAddContact)
//addContactButton.setOnClickListener {
//    val intent = Intent(this, AddContactActivity::class.java)
//    startActivity(intent)
//}
//
//
//
//
//contactRecyclerView = findViewById(R.id.contactRecyclerView)
//contactRecyclerView.layoutManager = LinearLayoutManager(this@ContactListActivity)

//okay so i want to remodel my app such that I my ContactListActivity has a fragmentContainer. now this fragment container loads something called the ContactListFragment (yet to be created) which shows all the contacts in a recycler view list. then if you click onto one of the items in this recycler view list, you go to another fragment which shows you the details of this contact. Can you step by sstep help me do this?