package com.example.contactbookappcompose.presentation.xml

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.databinding.FragmentContactDetailsBinding
import com.example.contactbookappcompose.presentation.compose.ContactViewModel
import org.mongodb.kbson.ObjectId

class ContactDetailFragment : Fragment() {

    private lateinit var binding: FragmentContactDetailsBinding
    private lateinit var viewModel: ContactViewModel
    private var contactId: String? = null  // ID of the selected contact
    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the passed contact ID
        arguments?.let {
            contactId = it.getString("contactId")
            contactId = contactId?.removePrefix("BsonObjectId(")?.removeSuffix(")")
            println("retrieved contactId = ${contactId}")
        }

        viewModel = ViewModelProvider(requireActivity())[ContactViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch the contact details from the ViewModel

        contact = contactId?.let { viewModel.findContactById(it) }


        contact?.let {
            binding.tvName.text = "${it.firstName} ${it.lastName}"
            binding.tvPhone.text = it.phoneNumber
            binding.tvEmail.text = it.email
            binding.tvAddress.text = it.address
            binding.tvCompany.text = it.companyName
        }

        // Edit Button Click
        binding.btnEdit.setOnClickListener {
            val intent = Intent(requireActivity(), AddContactActivity::class.java)
            intent.putExtra("contactId", contactId)
            startActivity(intent)
            println("edit contact")
        }

        // Delete Button Click
        binding.btnDelete.setOnClickListener {
            contact?.let { contact ->
                viewModel.deleteContact(contact.id)
                requireActivity().supportFragmentManager.popBackStack()
            println("deleting contact")
            }
        }
    }
}

