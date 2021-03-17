package com.example.nstustudentapp.schedule.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nstustudentapp.R
import com.example.nstustudentapp.schedule.data.model.Lesson


class LessonAdapter(
    private val mOnLessonClick: (Lesson) -> Unit
) :
    RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    var data: List<Lesson> = ArrayList(0)
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(
            R.layout.schedule_material_card,
            parent,
            false
        ) as View
        return LessonViewHolder(viewHolder) { mOnLessonClick(data[it])}
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) = holder.bind(data[position])

     inner class LessonViewHolder(private val view: View, onItemClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {

         init {
             itemView.setOnClickListener {
                 onItemClicked(adapterPosition)
             }
         }
        var disciplineName: TextView = view.findViewById(R.id.lesson_name)
        var teacherName: TextView = view.findViewById(R.id.teacher_name)
        var lectureHall: TextView = view.findViewById(R.id.audience_number)
        var lessonTime: TextView = view.findViewById(R.id.lesson_time)

        fun bind(lesson: Lesson) {
            disciplineName.text = lesson.lessonName
            val teachers = StringBuilder()
            for(teacher in lesson.teacherInfo){
                teachers.append(teacher.teacherName).append(" ")
            }
            teacherName.text = teachers
            this.lessonTime.text = lesson.time
            lectureHall.text = lesson.auditory
        }
    }

}
