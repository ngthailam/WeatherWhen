package com.thailam.weatherwhen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thailam.weatherwhen.data.model.Response
import com.thailam.weatherwhen.data.model.Schedule
import com.thailam.weatherwhen.data.repository.ScheduleRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ScheduleViewModel(val scheduleRepository: ScheduleRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _schedulesLiveData: MutableLiveData<List<Schedule>> by lazy {
        MutableLiveData<List<Schedule>>()
    }

    private val _scheduleScheduleOperationsLiveData: MutableLiveData<Response<ScheduleOperations>> by lazy {
        MutableLiveData<Response<ScheduleOperations>>()
    }

    val scheduleScheduleOperationsLiveData: LiveData<Response<ScheduleOperations>>
        get() = _scheduleScheduleOperationsLiveData

    val schedulesLiveData: LiveData<List<Schedule>>
        get() = _schedulesLiveData

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun addSchedule(epochDate: Long) {
        compositeDisposable.add(
            scheduleRepository.addSchedule(Schedule(epochDate = epochDate))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { _scheduleScheduleOperationsLiveData.postValue(Response.loading()) }
                .subscribe({
                    _scheduleScheduleOperationsLiveData.postValue(Response.success(ScheduleOperations.ADD))
                }, {
                    _scheduleScheduleOperationsLiveData.postValue(Response.error(it.message))
                })
        )
    }
}

enum class ScheduleOperations{
    ADD,
    UPDATE,
    DELETE
}
