package com.smarttoolfactory.postdynamichilt.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.smarttoolfactory.core.ui.adapter.NavigableFragmentStateAdapter
import com.smarttoolfactory.core.ui.fragment.navhost.NavHostContainerFragment
import com.smarttoolfactory.postdynamichilt.R
import com.smarttoolfactory.postdynamichilt.home.HomeFragment

/**
 * FragmentStateAdapter to contain ViewPager2 fragments inside another fragment.
 *
 * * 🔥 Create FragmentStateAdapter with viewLifeCycleOwner instead of Fragment to make sure
 * that it lives between [Fragment.onCreateView] and [Fragment.onDestroyView] while [View] is alive
 *
 * * https://stackoverflow.com/questions/61779776/leak-canary-detects-memory-leaks-for-tablayout-with-viewpager2
 */
class ChildFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    NavigableFragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {

            0 -> HomeFragment()

            // Vertical NavHost Post Fragment Container
            1 -> NavHostContainerFragment.createNavHostContainerFragment(
                R.layout.fragment_navhost_dashboard,
                R.id.nested_nav_host_fragment_dashboard
            )

            // Horizontal NavHost Post Fragment Container
            2 -> NavHostContainerFragment.createNavHostContainerFragment(
                R.layout.fragment_navhost_notification,
                R.id.nested_nav_host_fragment_notification
            )

            else -> NavHostContainerFragment.createNavHostContainerFragment(
                R.layout.fragment_navhost_account,
                R.id.nested_nav_host_fragment_account
            )
        }
    }
}
