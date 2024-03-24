package com.example.midterm_inprocess.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class DashboardViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time

    private var secondsElapsed = savedStateHandle.get<Long>("secondsElapsed") ?: 0L

    init {
        _time.value = formatTime(secondsElapsed)
    }

    fun saveState() {
        savedStateHandle.set("secondsElapsed", secondsElapsed)
    }
    private var timerJob: Job? = null

    fun startTimer() {
        if (timerJob == null || !timerJob!!.isActive) {
            timerJob = viewModelScope.launch {
                while (isActive) {
                    delay(1000)
                    secondsElapsed++
                    _time.postValue(formatTime(secondsElapsed))
                }
            }
        }
    }

    fun stopPauseTimer() {
        if (timerJob?.isActive == true) {
            timerJob?.cancel()
        } else {
            startTimer()
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        secondsElapsed = 0
        _time.postValue(formatTime(secondsElapsed))
    }

    internal fun formatTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}