package com.shubham.groceryapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shubham.groceryapp.fragment.ProductFragment
import com.shubham.groceryapp.models.CategoryResponse

class AdapterViewPager (var fm: FragmentManager): FragmentPagerAdapter(fm){
    var mFragmentList: ArrayList<Fragment> = ArrayList()
    var mTitleList: ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return mTitleList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }

    fun datachanged(){
        notifyDataSetChanged()
    }

    fun addFragment(tabTitle: String,subId:Int ){
        mFragmentList.add(ProductFragment.newInstance(tabTitle, subId))
        mTitleList.add(tabTitle)
    }

}