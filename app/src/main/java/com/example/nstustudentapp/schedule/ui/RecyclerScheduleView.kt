package com.example.nstustudentapp.schedule.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nstustudentapp.R
import com.example.nstustudentapp.schedule.data.model.Lesson
import com.example.nstustudentapp.schedule.presentation.LessonAdapter
import kotlinx.android.synthetic.main.custom_schedule_view.view.*

class RecyclerScheduleView : ConstraintLayout {

    private val listOfLessons: MutableList<Lesson> = arrayListOf()
    lateinit var lessonAdapter: LessonAdapter

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun onLessonClick(lesson: Lesson) {
        Toast.makeText(context, lesson.toString(), Toast.LENGTH_LONG).show()
    }

    private fun init(context: Context) {
        View.inflate(context, R.layout.custom_schedule_view, this)
        schedule_rv_lessons.layoutManager = LinearLayoutManager(context)
        lessonAdapter = LessonAdapter { onLessonClick(it)}
        lessonAdapter.data = listOfLessons
        schedule_rv_lessons.adapter = lessonAdapter
        showProgressBar()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    fun setListOfLesson(list: List<Lesson>) {
        this.listOfLessons.clear()
        this.listOfLessons.addAll(list)
        lessonAdapter.notifyDataSetChanged()
        hideProgressBar()
    }

    private fun showProgressBar() {
        schedule_rv_lessons.visibility = View.GONE
        schedule_text_data_is_empty.visibility = View.GONE
        schedule_progress_bar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        schedule_progress_bar.visibility = View.GONE
        if (listOfLessons.size == 0)
            schedule_text_data_is_empty.visibility = View.VISIBLE
        else {
            schedule_text_data_is_empty.visibility = View.GONE
            schedule_rv_lessons.visibility = View.VISIBLE
        }

    }

}