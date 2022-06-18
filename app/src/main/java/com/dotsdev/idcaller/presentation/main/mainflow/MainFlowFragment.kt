package com.dotsdev.idcaller.presentation.main.mainflow

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.databinding.FragmentMainFlowBinding
import com.dotsdev.idcaller.databinding.LayoutHeaderDrawerBinding
import com.dotsdev.idcaller.presentation.MainActivity
import com.dotsdev.idcaller.presentation.main.calltab.CallTabFragment
import com.dotsdev.idcaller.presentation.main.contacttab.ContactTabFragment
import com.dotsdev.idcaller.presentation.main.messagetab.MessageTabFragment
import com.dotsdev.idcaller.utils.retrieveCallLog
import com.dotsdev.idcaller.utils.retrieveContact
import com.dotsdev.idcaller.utils.retrieveInBox
import com.dotsdev.idcaller.utils.retrieveSent
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFlowFragment :
    BaseFragment<MainFlowViewModel, FragmentMainFlowBinding>(R.layout.fragment_main_flow),
    NavigationView.OnNavigationItemSelectedListener {
    override val viewModel: MainFlowViewModel by viewModel()
    override val binding: FragmentMainFlowBinding by viewBindings {
        FragmentMainFlowBinding.bind(it)
    }

    private val contactTab = ContactTabFragment.newInstance()
    private val messageTab = MessageTabFragment.newInstance()
    private val callTab = CallTabFragment.newInstance()

    private fun mainActivity(): MainActivity? = activity as? MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
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
            navView.setOnItemSelectedListener { item ->
                return@setOnItemSelectedListener onCheckFragmentContain(item.itemId)
            }

            navView.selectedItemId
        }
    }

    private fun observer() {
        with(viewModel) {
            currentTab.observe(viewLifecycleOwner) {
                binding.navView.selectedItemId = it.menuId
                onCheckFragmentContain(it.menuId)
            }
        }
    }

    private fun onCheckFragmentContain(menu: Int): Boolean {
        return when (menu) {
            PageTabType.NAV_CALL.menuId -> loadFragment(callTab)
            PageTabType.NAV_CONTACT.menuId -> loadFragment(contactTab)
            PageTabType.NAV_MESSAGE.menuId -> loadFragment(messageTab)
            else -> false
        }
    }

    override fun onStart() {
        super.onStart()
        retrieveContact().let(viewModel::setContactMemory)
        retrieveCallLog().let(viewModel::setCallLogMemory)
        (retrieveInBox() + retrieveSent()).let(viewModel::setMessageMemory)
    }

    private fun FragmentMainFlowBinding.setupDrawer() {
        val headerView = navigationDrawer.getHeaderView(0)
        val headerBinding = LayoutHeaderDrawerBinding.bind(headerView)
        headerBinding.lifecycleOwner = this@MainFlowFragment
        headerBinding.viewModel = this@MainFlowFragment.viewModel
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

    private fun loadFragment(fragment: Fragment): Boolean {
        return when {
            fragment == mainActivity()?.activeFragment -> {
                false
            }
            childFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) != null -> {
                childFragmentManager.beginTransaction().apply {
                    show(fragment)
                    mainActivity()?.activeFragment?.let { hide(it) }
                    commit()
                }
                mainActivity()?.activeFragment = fragment
                true
            }
            else -> {
                childFragmentManager.beginTransaction().apply {
                    add(R.id.home_container, fragment, fragment.javaClass.simpleName)
                    mainActivity()?.activeFragment?.let { hide(it) }
                    commit()
                }
                mainActivity()?.activeFragment = fragment
                true
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // TODO
        return true
    }
}
