package com.example.miwok

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CategoryAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Numbers"
            1 -> "Family"
            2 -> "Colors"
            else -> "Phrases"
        }
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> NumbersFragment()
            1 -> FamilyFragment()
            2 -> ColorsFragment()
            else -> PhrasesFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }
}