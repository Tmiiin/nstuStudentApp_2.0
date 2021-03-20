package com.example.nstustudentapp.schedule.ui

import android.content.Context
import android.graphics.ColorFilter
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nstustudentapp.R
import com.example.nstustudentapp.schedule.data.model.Days
import com.example.nstustudentapp.schedule.data.model.Lesson
import com.example.nstustudentapp.schedule.presentation.LessonAdapter
import kotlinx.android.synthetic.main.custom_schedule_view.view.*
import java.time.LocalDate
import java.time.temporal.TemporalField
import java.time.temporal.WeekFields
import java.util.*


class DaysListView : LinearLayout {

    private val listOfDays: MutableList<LinearLayout> = mutableListOf()
    private var selectedDay: Int = 0
    private val cardImageSize: Int = 40
    private var mapOfLessons: MutableMap<String, List<Lesson>> =
        hashMapOf()
    val TAG = "DaysListView"

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    fun setData(map: MutableMap<String, List<Lesson>>){
        this.mapOfLessons = map
        val day = Days.values()[selectedDay].day
        setListOfLesson(mapOfLessons[day]!!)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun setOnClickListeners() {
        listOfDays.forEachIndexed { index, item ->
            item.setOnClickListener {
                val selectedDay = it.tag.toString().toInt()
                setImage(selectedDay)
                val day = Days.values()[selectedDay].day
                if(mapOfLessons[day].isNullOrEmpty())
                    setListOfLesson(listOf())
                else setListOfLesson(mapOfLessons[day]!!)
            }
        }
    }

    private fun init(context: Context) {
        initDayListView(context)
        initRecyclerView()
        this.orientation = VERTICAL
        this.addView(dayListLayout)
        this.addView(scheduleRecyclerView)
    }

    val dayListLayout: LinearLayout = LinearLayout(context)

    private fun initDayListView(context: Context) {
        dayListLayout.orientation = HORIZONTAL
        dayListLayout.weightSum = 6F
        dayListLayout.gravity = Gravity.CENTER_VERTICAL
        val daysContainer = LinearLayout(context)
        val leftButton = ImageView(context)
        val rightButton = ImageView(context)

        val date = LocalDate.now()
        val field: TemporalField = WeekFields.of(Locale("ru")).dayOfWeek()

        daysContainer.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 6F)
        daysContainer.orientation = HORIZONTAL
        daysContainer.weightSum = 6F

        var i = 1L
        for (day in Days.values()) {
            val dateOf = date.with(field, i)
            val dayCard = initDayCard(day.day, dateOf.dayOfMonth.toString(), i.toInt() - 1)
            listOfDays.add(dayCard)
            daysContainer.addView(dayCard)
            i++
        }

        val params = LayoutParams(64, 64)

        leftButton.setImageResource(R.drawable.ic_arrow_point_to_right)
        leftButton.rotation = 180F
        leftButton.layoutParams = params
        leftButton.setColorFilter(ContextCompat.getColor(context,
            R.color.hint_text), android.graphics.PorterDuff.Mode.SRC_IN)
        leftButton.setOnClickListener{ onLeftArrowClick() }

        rightButton.setImageResource(R.drawable.ic_arrow_point_to_right)
        rightButton.layoutParams = params
        rightButton.setColorFilter(ContextCompat.getColor(context,
            R.color.hint_text), android.graphics.PorterDuff.Mode.SRC_IN)
        rightButton.setOnClickListener{ onRightArrowClick() }

        dayListLayout.addView(leftButton)
        dayListLayout.addView(daysContainer)
        dayListLayout.addView(rightButton)
        setImage(selectedDay)
        setOnClickListeners()
    }

    fun onLeftArrowClick() {

    }

    fun onRightArrowClick() {

    }

    private fun initDayCard(day: String, dateOf: String, position: Int): LinearLayout {
        val card = LinearLayout(context)
        val date = TextView(context)
        val text = TextView(context)
        val image = ImageView(context)
        card.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1F)
        card.orientation = VERTICAL
        card.tag = position //to identify view set tag with number in array
        card.gravity = Gravity.CENTER

        date.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        date.isAllCaps = true
        date.text = dateOf

        text.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        text.isAllCaps = true
        text.text = day

        image.setImageResource(R.drawable.like_heart)
        image.visibility = GONE
        image.layoutParams = LayoutParams(cardImageSize, cardImageSize)

        card.addView(date)
        card.addView(text)
        card.addView(image)
        return card
    }

    private fun setImage(day: Int) {
        val selectedViewImage: ImageView = listOfDays[selectedDay].getChildAt(2) as ImageView
        val nowSelectedViewImage = listOfDays[day].getChildAt(2) as ImageView
        selectedDay = day
        selectedViewImage.visibility = GONE
        nowSelectedViewImage.visibility = VISIBLE
    }

    private val listOfLessons: MutableList<Lesson> = arrayListOf()
    lateinit var lessonAdapter: LessonAdapter
    private var scheduleRecyclerView: ConstraintLayout = ConstraintLayout(context)
    private lateinit var recyclerView: RecyclerView

    private fun initRecyclerView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_schedule_view, scheduleRecyclerView)
        scheduleRecyclerView.schedule_rv_lessons.layoutManager = LinearLayoutManager(context)
        recyclerView = scheduleRecyclerView.schedule_rv_lessons
        lessonAdapter = LessonAdapter { onLessonClick(it) }
        lessonAdapter.data = listOfLessons
        scheduleRecyclerView.schedule_rv_lessons.adapter = lessonAdapter
        showProgressBar()
    }

    private fun setListOfLesson(list: List<Lesson>) {
        this.listOfLessons.clear()
        this.listOfLessons.addAll(list)
        lessonAdapter.notifyDataSetChanged()
        hideProgressBar()
    }

    fun showProgressBar() {
        recyclerView.visibility = View.GONE
        scheduleRecyclerView.schedule_text_data_is_empty.visibility = View.GONE
        scheduleRecyclerView.schedule_progress_bar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        scheduleRecyclerView.schedule_progress_bar.visibility = View.GONE
        if (listOfLessons.size == 0)
            scheduleRecyclerView.schedule_text_data_is_empty.visibility = View.VISIBLE
        else {
            scheduleRecyclerView.schedule_text_data_is_empty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }


    private fun onLessonClick(lesson: Lesson) {
        Toast.makeText(context, lesson.toString(), Toast.LENGTH_LONG).show()
    }
}