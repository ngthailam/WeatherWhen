package com.thailam.weatherwhen.ui.schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.thailam.weatherwhen.R
import com.thailam.weatherwhen.data.model.Response
import com.thailam.weatherwhen.data.model.Status
import com.thailam.weatherwhen.viewmodel.ScheduleOperations
import com.thailam.weatherwhen.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.activity_schedule.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder
import java.util.*


class ScheduleActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private val scheduleViewModel: ScheduleViewModel by viewModel()
    private var datePickerDialog: DatePickerDialog? = null
    private var timePickerDialog: TimePickerDialog? = null
    private var year: Int? = null
    private var month: Int? = null
    private var dayOfMonth: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        initToolbar()
        initViews()
        initDatePicker()
        initTimePicker()
        initViewModel()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.year = year
        this.month = month
        this.dayOfMonth = dayOfMonth
        timePickerDialog?.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        // TODO: save date + time to db
        if (year == null || month == null || dayOfMonth == null) return
        val calendar = Calendar.getInstance()
        calendar.set(year!!, month!!, dayOfMonth!!, hourOfDay, minute, 0)
        scheduleViewModel.addSchedule(calendar.timeInMillis)
    }

    private fun initViews() {
        fabAddSchedule.setOnClickListener { datePickerDialog?.show() }
    }

    private fun initDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(this, this, year, month, dayOfMonth)
    }

    private fun initTimePicker() {
        timePickerDialog = TimePickerDialog(this, this, 0, 0, false)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbarSchedule)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        toolbarSchedule.setNavigationOnClickListener { finish() }
    }

    private fun initViewModel() {
        scheduleViewModel.scheduleScheduleOperationsLiveData.observe(this, Observer {
            handleScheduleOperations(it)
        })
    }

    private fun handleScheduleOperations(response: Response<ScheduleOperations>) {
        when (response.status) {
            Status.SUCCESS -> onScheduleOperationsSuccess(response.data)
            Status.ERROR -> onScheduleOperationsError(response.message)
        }
    }

    private fun onScheduleOperationsSuccess(scheduleOperations: ScheduleOperations?) {
        val msg = StringBuilder().append(scheduleOperations).append(" ").append(resources.getString(R.string.success))
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun onScheduleOperationsError(message: String?) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
