package com.example.nstustudentapp.schedule.presentation

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.nstustudentapp.schedule.data.model.Lesson
import com.example.nstustudentapp.schedule.ui.LessonsFragment
import com.example.nstustudentapp.schedule.ui.ScheduleFragment

class ViewPagerAdapter(@NonNull val fragmentActivity: ScheduleFragment, private val itemCounts: Int) :
    FragmentStateAdapter(fragmentActivity) {

    @NonNull
    override fun createFragment(position: Int): Fragment {
         return LessonsFragment()
    }

    override fun getItemCount(): Int {
        return itemCounts
    }

}
