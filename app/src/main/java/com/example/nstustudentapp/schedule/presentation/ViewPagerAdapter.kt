package com.example.nstustudentapp.schedule.presentation

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nstustudentapp.schedule.ui.LessonsFragment
import com.example.nstustudentapp.schedule.ui.ScheduleFragment

class ViewPagerAdapter(@NonNull val fragmentActivity: ScheduleFragment?) :
    FragmentStateAdapter(fragmentActivity!!) {

    public val lessonFragment = LessonsFragment()

    @NonNull
    override fun createFragment(position: Int): Fragment {
         return lessonFragment
    }

    override fun getItemCount(): Int {
        return CARD_ITEM_SIZE
    }

    companion object {
        private const val CARD_ITEM_SIZE = 6
    }
}
