package com.example.idcaller.presentation.main.mainflow

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.idcaller.R
import com.example.idcaller.core.base.BaseFragment
import com.example.idcaller.core.base.viewBindings
import com.example.idcaller.data.model.Contact
import com.example.idcaller.databinding.FragmentMainFlowBinding
import com.example.idcaller.databinding.LayoutHeaderDrawerBinding
import com.example.idcaller.presentation.MainActivity
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFlowFragment :
    BaseFragment<MainFlowViewModel, FragmentMainFlowBinding>(R.layout.fragment_main_flow),
    NavigationView.OnNavigationItemSelectedListener {
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
        setHasOptionsMenu(true)
        with(binding) {
            viewModel = this@MainFlowFragment.viewModel
            mainActivity()?.apply {
                setSupportActionBar(toolbar)
                supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(true)
                    setHomeButtonEnabled(true)
                }
            }
            setupDrawer()
        }
    }

    override fun onStart() {
        super.onStart()
        retrieveContact()
    }

    private fun FragmentMainFlowBinding.setupDrawer() {
        val headerView = navigationDrawer.getHeaderView(0)
        LayoutHeaderDrawerBinding.bind(headerView).apply {
            lifecycleOwner = this@MainFlowFragment
            viewModel = this@MainFlowFragment.viewModel
        }
        toolbar.setNavigationOnClickListener {
            drawer.open()
        }
        ActionBarDrawerToggle(
            requireActivity(),
            drawer,
            R.string.drawer_open,
            R.string.drawer_close
        ).apply {
            isDrawerIndicatorEnabled = true
            drawer.addDrawerListener(this)
            syncState()
        }
        navigationDrawer.apply {
            setNavigationItemSelectedListener(this@MainFlowFragment)
            bringToFront()
        }
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //TODO
        return true
    }
}