package com.example.idcaller.presentation.main.mainflow

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import com.example.idcaller.R
import com.example.idcaller.core.base.BaseFragment
import com.example.idcaller.core.base.viewBindings
import com.example.idcaller.data.model.Contact
import com.example.idcaller.databinding.FragmentMainFlowBinding
import com.example.idcaller.presentation.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFlowFragment :
    BaseFragment<MainFlowViewModel, FragmentMainFlowBinding>(R.layout.fragment_main_flow) {
    override val viewModel: MainFlowViewModel by viewModel()
    override val binding: FragmentMainFlowBinding by viewBindings {
        FragmentMainFlowBinding.bind(it)
    }

    private fun mainActivity(): MainActivity? = activity as? MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            viewModel = this@MainFlowFragment.viewModel

        }
    }

    override fun onStart() {
        super.onStart()
        retrieveContact()
    }

    @SuppressLint("Range")
    private fun retrieveContact() {
        val cursorPhone: Cursor? = activity?.contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursorPhone?.moveToFirst()
        val contactList = mutableListOf<Contact>()
        while (cursorPhone?.isAfterLast == false) {
            val number = cursorPhone.getString(
                cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            )
            val contactId = cursorPhone.getString(
                cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            )
            contactList.add(Contact(phoneNumber = number, callerName = contactId))
            cursorPhone.moveToNext()
        }
        viewModel.setContactMemory(contactList)
        cursorPhone?.close()
    }
}