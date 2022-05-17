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
import com.dotsdev.idcaller.presentation.main.contacttab.ContactTabFragment
import com.dotsdev.idcaller.presentation.main.messagetab.MessageTabFragment.Companion.newInstance
import com.dotsdev.idcaller.utils.retrieveContact
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFlowFragment :
    BaseFragment<MainFlowViewModel, FragmentMainFlowBinding>(R.layout.fragment_main_flow),
    NavigationView.OnNavigationItemSelectedListener {
    override val viewModel: MainFlowViewModel by viewModel()
    override val binding: FragmentMainFlowBinding by viewBindings {
        FragmentMainFlowBinding.bind(it)
    }

    //private val contactTab = ContactTabFragment.newInstance()

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
            navView.setOnItemSelectedListener { item ->
                return@setOnItemSelectedListener onCheckFragmentContain(item.itemId)
            }
        }
    }

    private fun onCheckFragmentContain(menu: Int): Boolean {
        return when (menu) {
//            R.id.nav_call -> loadFragment(navHomeFragment)
//            R.id.nav_contact -> loadFragment(reservationsFragment)
//            R.id.nav_message -> loadFragment(equipmentFragment)
            else -> false
        }
    }

    override fun onStart() {
        super.onStart()
        retrieveContact().let(viewModel::setContactMemory)
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
        //TODO
        return true
    }
}