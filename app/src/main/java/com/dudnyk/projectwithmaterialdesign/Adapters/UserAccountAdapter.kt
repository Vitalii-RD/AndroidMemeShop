package com.dudnyk.projectwithmaterialdesign.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dudnyk.projectwithmaterialdesign.UserAccountFragments.CartFragment
import com.dudnyk.projectwithmaterialdesign.UserAccountFragments.OrderListFragment
import com.dudnyk.projectwithmaterialdesign.UserAccountFragments.PaymentDeliveryFragment
import com.dudnyk.projectwithmaterialdesign.UserAccountFragments.SecuritySettingsFragment

class UserAccountAdapter(private val context: Context, fm: FragmentManager, var totalTabs: Int):
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SecuritySettingsFragment.newInstance()
            1 -> CartFragment.newInstance()
            2 -> OrderListFragment.newInstance()
            3 -> PaymentDeliveryFragment.newInstance()
            else -> SecuritySettingsFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}