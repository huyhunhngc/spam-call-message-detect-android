package com.dotsdev.idcaller.presentation.main.mainflow

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.data.local.CacheDataSource
import com.dotsdev.idcaller.data.model.NavigationGraphInfo
import com.dotsdev.idcaller.databinding.FragmentMainFlowBinding
import com.dotsdev.idcaller.databinding.LayoutHeaderDrawerBinding
import com.dotsdev.idcaller.presentation.MainActivity
import com.dotsdev.idcaller.presentation.main.blockingtab.BlockingTabFragment
import com.dotsdev.idcaller.presentation.main.calltab.CallTabFragment
import com.dotsdev.idcaller.presentation.main.contacttab.ContactTabFragment
import com.dotsdev.idcaller.presentation.main.messagetab.MessageTabFragment
import com.dotsdev.idcaller.utils.retrieveCallLog
import com.dotsdev.idcaller.utils.retrieveContact
import com.dotsdev.idcaller.utils.retrieveInBox
import com.dotsdev.idcaller.utils.retrieveSent
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

object TabState {
    var tabType = PageTabType.NAV_MESSAGE
}

class MainFlowFragment :
    BaseFragment<MainFlowViewModel, FragmentMainFlowBinding>(R.layout.fragment_main_flow),
    NavigationView.OnNavigationItemSelectedListener {
    override val viewModel: MainFlowViewModel by viewModel()
    override val binding: FragmentMainFlowBinding by viewBindings {
        FragmentMainFlowBinding.bind(it)
    }

    private val cacheDataSource: CacheDataSource by inject()

    private val contactTab = ContactTabFragment.newInstance()
    private val messageTab = MessageTabFragment.newInstance()
    private val callTab = CallTabFragment.newInstance()
    private val blockingTab = BlockingTabFragment.newInstance()

    private fun mainActivity(): MainActivity? = activity as? MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setHasOptionsMenu(true)
        with(binding) {
            mainActivity()?.apply {
                setSupportActionBar(toolbar)
                supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(true)
                    setHomeButtonEnabled(true)
                }
            }
            setupDrawer()
            navView.selectedItemId = TabState.tabType.menuId
            onCheckFragmentContain(TabState.tabType.menuId)
            navView.setOnItemSelectedListener { item ->
                return@setOnItemSelectedListener onCheckFragmentContain(item.itemId)
            }
        }
    }

    private fun onCheckFragmentContain(menu: Int): Boolean {
        return when (menu) {
            PageTabType.NAV_CALL.menuId -> loadFragment(callTab, PageTabType.NAV_CALL)
            PageTabType.NAV_CONTACT.menuId -> loadFragment(contactTab, PageTabType.NAV_CONTACT)
            PageTabType.NAV_MESSAGE.menuId -> loadFragment(messageTab, PageTabType.NAV_MESSAGE)
            PageTabType.NAV_BLOCKING.menuId -> loadFragment(blockingTab, PageTabType.NAV_BLOCKING)
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrieveData()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.IO) {
            retrieveContact().let(viewModel::setContactMemory)
            retrieveCallLog().let(viewModel::setCallLogMemory)
        }
    }

    private fun retrieveData() {
        lifecycleScope.launch(Dispatchers.IO) {
            retrieveContact().let(viewModel::setContactMemory)
            retrieveCallLog().let(viewModel::setCallLogMemory)
            (retrieveInBox() + retrieveSent()).let(viewModel::setMessageMemory)
            //getSIMInfo().let(viewModel::setSimInfo)
        }
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
        val menuItem = navigationDrawer.menu.findItem(R.id.action_change_theme)
        val switchId = menuItem.actionView as SwitchCompat
        switchId.isChecked = cacheDataSource.getNightMode()
        switchId.setOnCheckedChangeListener { _, isChecked ->
            this@MainFlowFragment.viewModel.setAppTheme(isChecked)
        }
        headerBinding.editIcon.setOnClickListener {
            findNavController().navigate(
                MainFlowFragmentDirections.openEditProfile(
                    NavigationGraphInfo(
                        graphId = R.navigation.edit_profile_navigation,
                        startDestinationId = R.id.edit_profile_fragment
                    )
                )
            )
        }
    }

    private fun loadFragment(fragment: Fragment, tabType: PageTabType): Boolean {
        return when {
            tabType == TabState.tabType -> {
                false
            }

            childFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) != null -> {
                childFragmentManager.beginTransaction().apply {
                    mainActivity()?.activeFragment?.let {
                        show(fragment)
                        hide(it)
                    } ?: replace(R.id.home_container, fragment, fragment.javaClass.simpleName)
                    commit()
                }
                mainActivity()?.activeFragment = fragment
                TabState.tabType = tabType
                true
            }

            else -> {
                childFragmentManager.beginTransaction().apply {
                    add(R.id.home_container, fragment, fragment.javaClass.simpleName)
                    mainActivity()?.activeFragment?.let { hide(it) }
                    commit()
                }
                mainActivity()?.activeFragment = fragment
                TabState.tabType = tabType
                true
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }
}
