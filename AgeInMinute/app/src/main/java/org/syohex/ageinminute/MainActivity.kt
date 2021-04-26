package org.syohex.ageinminute

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(
                this,
                "The chosen year is $selectedYear, the month is $selectedMonth and the day is $selectedDayOfMonth",
                Toast.LENGTH_LONG
            ).show()

            val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            tvSelectedDate.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate = sdf.parse(selectedDate)
            val selectedDateInMinutes = theDate!!.time / (60 * 1000)
            val selectedDateInDays = theDate.time / (60 * 60 * 24 * 1000)
            val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
            val currentDateInMinutes = currentDate!!.time / (60 * 1000)
            val currentDateInDays = currentDate.time / (60 * 60 * 24 * 1000)
            val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
            val differenceInDays = currentDateInDays - selectedDateInDays

            tvSelectedDateInMinutes.text = differenceInMinutes.toString()
            tvSelectedDateInDays.text = differenceInDays.toString()
        }, year, month, day)

        dpd.datePicker.maxDate = Date().time - (24 * 60 * 60 * 1000)
        dpd.show()
    }
}