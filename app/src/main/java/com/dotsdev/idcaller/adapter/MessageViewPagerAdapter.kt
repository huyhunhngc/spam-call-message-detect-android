package com.dotsdev.idcaller.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dotsdev.idcaller.presentation.main.messagetab.tablayout.ImportantMessageFragment
import com.dotsdev.idcaller.presentation.main.messagetab.tablayout.InboxMessageFragment
import com.dotsdev.idcaller.presentation.main.messagetab.tablayout.SpamMessageFragment

class MessageViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val inboxTab = InboxMessageFragment.newInstance()
    private val importantTab = ImportantMessageFragment.newInstance()
    private val spamTab = SpamMessageFragment.newInstance()

    private val fragments = listOf(
        inboxTab,
        importantTab,
        spamTab
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
