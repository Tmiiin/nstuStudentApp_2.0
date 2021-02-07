package com.example.nstustudentapp.schedule.data.model

data class Lesson(
    val teacherInfo: List<TeacherInfo>,
    val parity: String = "",
    val time: String = "",
    val auditory: String = "",
    val lessonName: String = ""
)
