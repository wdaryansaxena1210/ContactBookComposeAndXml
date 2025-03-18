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
//            val intent = Intent(requireActivity(), EditContactActivity::class.java)
//            intent.putExtra("contactId", contactId?.toHexString())
//            startActivity(intent)
            println("edit contact")
        }

        // Delete Button Click
        binding.btnDelete.setOnClickListener {
            contactId?.let { id ->
//                viewModel.deleteContact(id)
//                requireActivity().supportFragmentManager.popBackStack()
            println("deleting contact")
            }
        }
    }
}


//class ContactDetailsFragment : Fragment() {
//
//    private lateinit var binding: FragmentContactDetailsBinding
//    private lateinit var viewModel: ContactViewModel
//    private var contactId: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            contactId = it.getString(contactId)
//        }
//        viewModel = ViewModelProvider(requireActivity())[ContactViewModel::class.java]
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        // Inflate the layout for this fragment
//        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ContactDetailsFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ContactDetailsFragment().apply {
//                arguments = Bundle().apply {
////                    putString(ARG_PARAM1, param1)
////                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}