package com.dudnyk.projectwithmaterialdesign.UserAccountFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dudnyk.projectwithmaterialdesign.Adapters.UserAccountAdapter
import com.dudnyk.projectwithmaterialdesign.R
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentUserAccountBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout

class UserAccountFragment : Fragment() {
    private lateinit var userBinding: FragmentUserAccountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userBinding = FragmentUserAccountBinding.inflate(inflater, container, false)
        initView()
        Log.i("Pos", userBinding.accountTabLayout.selectedTabPosition.toString())

        return userBinding.root
    }

    private fun initView() {
        setUpToolBar(R.string.user_account)
        setTabLayout()
    }

    private fun setUpToolBar(title: Int) {
        val toolbar = activity?.findViewById<MaterialToolbar>(R.id.my_toolbar)!!
        toolbar.setTitle(title)
    }

    private fun setTabLayout() {
        val tabLayout = userBinding.accountTabLayout
        val tabs = listOf("Security", "Cart", "Orders", "Payment")

        tabs.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }

        val userAccountAdapter = UserAccountAdapter(userBinding.root.context, fragmentManager!!, tabs.size)

        userBinding.accountViewPager.apply {
            adapter = userAccountAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                userBinding.accountViewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) { }
            override fun onTabReselected(tab: TabLayout.Tab) { }
        })
    }

    companion object {
        const val TAG = "USER_ACCOUNT"

        fun newInstance() =
            UserAccountFragment()
                .apply {
                arguments = Bundle().apply {}
            }
    }
}