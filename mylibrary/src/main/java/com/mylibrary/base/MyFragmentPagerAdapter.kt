package com.mylibrary.base

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * Created by Admin on 2017/3/27.
 */
class MyFragmentPagerAdapter : FragmentStateAdapter {

    private var fragments: List<Fragment>

    constructor(fragmentActivity: FragmentActivity, fragments: List<Fragment>) : super(
        fragmentActivity
    ) {
        this.fragments = fragments
    }

    @NonNull
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}