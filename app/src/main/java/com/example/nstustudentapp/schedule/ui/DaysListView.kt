package com.example.nstustudentapp.schedule.ui

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.nstustudentapp.R
import com.example.nstustudentapp.schedule.data.model.Days
import java.time.LocalDate
import java.time.temporal.TemporalField
import java.time.temporal.WeekFields
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class DaysListView : LinearLayout {

    val listOfDays: MutableList<LinearLayout> = mutableListOf()
    var selectedDay: Int = 0
    val cardImageSize :Int = 40

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

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun setOnClickListeners(){
        for(item in listOfDays)
            item.setOnClickListener{ it ->
                setImage(it.tag.toString().toInt())
            }
    }

    private fun init(context: Context) {
        this.orientation = HORIZONTAL
        this.weightSum = 6F
        this.gravity = Gravity.CENTER_VERTICAL

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
            i += 1
        }

        val params = LayoutParams(64, 64)

        leftButton.setImageResource(R.drawable.ic_arrow_point_to_right)
        leftButton.rotation = 180F
        leftButton.layoutParams = params

        rightButton.setImageResource(R.drawable.ic_arrow_point_to_right)
        rightButton.layoutParams = params
        this.addView(leftButton)
        this.addView(daysContainer)
        this.addView(rightButton)
        setImage(selectedDay)
        setOnClickListeners()
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
}