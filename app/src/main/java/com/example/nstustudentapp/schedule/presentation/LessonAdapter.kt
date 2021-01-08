package com.example.nstustudentapp.schedule.presentation

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nstustudentapp.R
import com.example.nstustudentapp.schedule.data.model.Lesson

class LessonAdapter(
    private var items: List<Lesson>,
    private val onLessonListener: OnLessonListener,
    val context: Context
) :
    RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.schedule_material_card,
            parent,
            false
        ) as View
        return LessonViewHolder(v, onLessonListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(items[position])
        /*holder.itemView.setOnClickListener{}*/
    }

    fun setItems(lessons: List<Lesson>) {
        items = lessons
        notifyDataSetChanged()
    }

    class LessonViewHolder(view: View, private val mOnLessonListener: OnLessonListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        var disciplineName: TextView = view.findViewById(R.id.lesson_name)
        var teacherName: TextView = view.findViewById(R.id.teacher_name)
        var lectureHall: TextView = view.findViewById(R.id.audience_number)

        // var typeOfLesson: TextView = view.findViewById(R.id.typeOfLesson)
        var lessonTime: TextView = view.findViewById(R.id.lesson_time)

        fun bind(lesson: Lesson) {
            disciplineName.text = lesson.disciplineName
            teacherName.text = lesson.firstTeacherName
            val lessonTime: String = lesson.startTime + "-" + lesson.endTime;
            this.lessonTime.text = lessonTime
            lectureHall.text = lesson.lectureHall
            if (lesson.nlt != 1) { //if it is lection
                // typeOfLesson.text = "лекция"
                lectureHall.setBackgroundColor(Color.parseColor("#9f3b35"))
            } else {
                // typeOfLesson.text = "семинар"
                lectureHall.setBackgroundColor(Color.parseColor("#015f47"))
            }
        }

        override fun onClick(p0: View?) {
            mOnLessonListener.onLessonClick(p0, adapterPosition)
        }

    }

    interface OnLessonListener {
        fun onLessonClick(v: View?, position: Int)
    }
}
