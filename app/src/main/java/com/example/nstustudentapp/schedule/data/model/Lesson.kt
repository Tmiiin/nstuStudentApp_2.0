package com.example.nstustudentapp.schedule.data.model

data class Lesson(
    val disciplineName: String,
    val firstTeacherName: String,
    val name_teacher2: String,
    val startTime: String,
    val endTime: String,
    val lectureHall: String,
    val idTeacher1: Int,
    val id_teacher2: Int,
    val freq: Int,
    val nlt: Int,
    val pos: Int,
    val weeks: ArrayList<Int>
)
