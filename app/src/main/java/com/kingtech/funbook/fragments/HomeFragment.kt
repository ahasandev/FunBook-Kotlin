package com.kingtech.funbook.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kingtech.funbook.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    val adapter = HomePagerAdapter(childFragmentManager)
        binding.vpHomeDetail.adapter = adapter

       binding.tlHomeDetailBar.setupWithViewPager(binding.vpHomeDetail)
    }




    class HomePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val tabTitles = arrayOf("Home", "Friends","Post","Profile")

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> PostFragment()
                1 -> MemesFragment()
                2 -> PostFragment()
                3 -> PostFragment()
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


