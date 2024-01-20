package com.kingtech.funbook.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kingtech.funbook.R
import com.kingtech.funbook.SreachFragment
import com.kingtech.funbook.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    val adapter = HomePagerAdapter(childFragmentManager)
        binding.vpHomeDetail.adapter = adapter

       binding.tlHomeDetailBar.setupWithViewPager(binding.vpHomeDetail)
        binding.apply {

            tlHomeDetailBar.getTabAt(0)!!.setIcon(R.drawable.round_home_24)
            tlHomeDetailBar.getTabAt(1)!!.setIcon(R.drawable.baseline_diversity_1_24)
            tlHomeDetailBar.getTabAt(2)!!.setIcon(R.drawable.round_desktop_mac_24)
            tlHomeDetailBar.getTabAt(3)!!.setIcon(R.drawable.rounded_content_paste_search_24)
            tlHomeDetailBar.getTabAt(4)!!.setIcon(R.drawable.baseline_emoji_emotions_24)
            tlHomeDetailBar.getTabAt(5)!!.setIcon(R.drawable.round_account_circle_24)
        }
    }




    class HomePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragmentlist = ArrayList<Fragment>()
        private val tabTitles = arrayOf("", "","","","","")

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> PostFragment()
                1 -> UserFragment()
                2 -> MemesFragment()
                3 -> SreachFragment()
                4 -> PostFragment()
                else -> PostFragment()
//                throw IllegalArgumentException("Invalid position: $position")
            }
        }

        override fun getCount(): Int {
            return tabTitles.size // Number of tabs
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }

    }


}


