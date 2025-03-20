package com.example.contactbookappcompose.presentation.xml

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactbookappcompose.R
import com.example.contactbookappcompose.data.local.Contact
import com.example.contactbookappcompose.domain.contact.ContactData
import com.example.contactbookappcompose.presentation.compose.ContactViewModel

class AddContactActivity : AppCompatActivity() {

    private  val viewModel : ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_contact)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var contact :Contact? = null
        val contactId = intent.getStringExtra("contactId")
//        println(contactId + " is the extraString")



        val firstNameEditText = findViewById<EditText>(R.id.etFirstName)
        val lastNameEditText = findViewById<EditText>(R.id.etLastName)
        val phoneNumberEditText = findViewById<EditText>(R.id.etPhoneNumber)
        val emailEditText = findViewById<EditText>(R.id.etEmail)
        val addressEditText = findViewById<EditText>(R.id.etAddress)
        val companyNameEditText = findViewById<EditText>(R.id.etCompanyName)
        val saveButton = findViewById<Button>(R.id.btnSaveContact)


        //if a contact Id was supplied, i.e. we got to this page by clicking the "edit"
        if(contactId!=null){
            contact = viewModel.findContactById(contactId)

            contact?.let {
                firstNameEditText.setText(it.firstName)
                lastNameEditText.setText(it.lastName)
                phoneNumberEditText.setText(it.phoneNumber)
                emailEditText.setText(it.email)
                addressEditText.setText(it.address)
                companyNameEditText.setText(it.companyName)
            }

        }


        //if firstName is passed, i.e. we came to this page/activity after clicking on a search result
        if(intent.getStringExtra("firstName") != null){
            firstNameEditText.setText(intent.getStringExtra("firstName"))
            lastNameEditText.setText(intent.getStringExtra("lastName"))
            emailEditText.setText(intent.getStringExtra("email"))
        }

        saveButton.setOnClickListener{
            val contact = ContactData(
                firstName = firstNameEditText.text.toString(),
                lastName = lastNameEditText.text.toString(),
                companyName = companyNameEditText.text.toString(),
                email = emailEditText.text.toString(),
                address = addressEditText.text.toString(),
                phoneNumber = phoneNumberEditText.text.toString()
            )
            println("calling viewModel,addContact on $contact")
            viewModel.addContact(contact)

            finish()
        }

    }
}