package com.dotsdev.idcaller.presentation.main.messagetab

import android.annotation.SuppressLint
import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.adapter.MessageViewPagerAdapter
import com.dotsdev.idcaller.core.base.BaseFragment
import com.dotsdev.idcaller.core.base.viewBindings
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.MessageType
import com.dotsdev.idcaller.databinding.FragmentMessageListBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MessageListFragment :
    BaseFragment<MessageListViewModel, FragmentMessageListBinding>(R.layout.fragment_message_list) {
    override val viewModel: MessageListViewModel by viewModel()
    override val binding: FragmentMessageListBinding by viewBindings {
        FragmentMessageListBinding.bind(it)
    }
    private val tabTitle = listOf(
        Pair("Inbox", R.drawable.ic_baseline_inbox_24),
        Pair("Important", R.drawable.ic_baseline_star_24),
        Pair("Spam", R.drawable.ic_baseline_app_blocking_24)
    )
    private var mediator: TabLayoutMediator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initViews() {
        with(binding) {
            viewPager.adapter =
                MessageViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
            mediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitle[position].first
                tab.icon = context?.getDrawable(tabTitle[position].second)
            }
            mediator?.attach()
        }
    }

    override fun onDestroy() {
        mediator?.detach()
        super.onDestroy()
    }

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            val roleManager = activity?.getSystemService(RoleManager::class.java)
//            if (roleManager!!.isRoleAvailable(RoleManager.ROLE_SMS)) {
//                if (roleManager.isRoleHeld(RoleManager.ROLE_SMS)) {
//                    //askPermissions()
//                } else {
//                    val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS)
//                    activity?.startActivityForResult(intent, MAKE_DEFAULT_APP_REQUEST)
//                }
//            }
//        } else {
//            if (Telephony.Sms.getDefaultSmsPackage(activity) == activity?.packageName) {
//                //askPermissions()
//            } else {
//                val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
//                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, activity?.packageName)
//                activity?.startActivityForResult(intent, MAKE_DEFAULT_APP_REQUEST)
//            }
//        }
//    }
    companion object {
        const val MAKE_DEFAULT_APP_REQUEST = 101
    }
}