package com.example.midterm_inprocess.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _counter = MutableLiveData<Int>().apply {
        value = savedStateHandle.get<Int>("counter") ?: 0 // Initialize with saved state or 0
    }
    val counter: LiveData<Int> = _counter

    private val _time = MutableLiveData<String>().apply {
        value = formatTime(0)
    }
    val time: LiveData<String> = _time

    private var secondsElapsed = 0L
    private var timerJob: Job? = null

    fun startTimer() {
        if (timerJob?.isActive != true) {
            timerJob = viewModelScope.launch {
                while (true) {
                    delay(1000)
                    secondsElapsed++
                    _time.postValue(formatTime(secondsElapsed))
                    // Log.d("HomeViewModel", "Timer ticking: ${formatTime(secondsElapsed)}")
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
        stopPauseTimer()
        secondsElapsed = 0
        _counter.value = 0 // Reset the counter to 0
        savedStateHandle.set("counter", 0)
        _time.value = formatTime(secondsElapsed)
    }

    private fun formatTime(seconds: Long): String {
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











//package com.example.midterm_inprocess.ui.home
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//class HomeViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
//
//    // Define a MutableLiveData to hold the incrementing value
//    private val _counter = MutableLiveData<Int>().apply {
//        value = savedStateHandle.get<Int>("counter") ?: 0 // Initialize with saved state or 0
//    }
//    // Define LiveData to observe the counter value changes
//    val counter: LiveData<Int> = _counter
//
//    // Timer related properties
//    private val _time = MutableLiveData<String>().apply {
//        value = formatTime(0)
//    }
//    val time: LiveData<String> = _time
//
//    private var secondsElapsed = 0L
//    private var counterEffectOnTimer = 0L
//    private var timerJob: Job? = null
//
//    // Increment function which increments the counter by 1
//    fun incrementCounter() {
//        val currentCount = _counter.value ?: 0
//        val updatedCount = currentCount + 1
//        _counter.value = updatedCount
//        savedStateHandle.set("counter", updatedCount) // Save the updated count to the saved state
//        //Log.d("HomeViewModel", "Counter incremented to: ${_counter.value}")
//    }
//
//    fun setCounter(value: Int) {
//        _counter.value = value
//    }
//
//    // Function to reset the counter
//    fun resetCounter() {
//        _counter.value = 0 // Reset the counter to 0
//        savedStateHandle.set("counter", 0) // Save the reset count to the saved state
//    }
//    // Function to start the timer
//    fun startTimer() {
//
//        if (timerJob?.isActive != true) {
//            timerJob = viewModelScope.launch {
//                while (true) {
//                    delay(1000)
//                    secondsElapsed++
//                    _time.postValue(formatTime(secondsElapsed))
//                    //Log.d("HomeViewModel", "Timer ticking: ${formatTime(secondsElapsed)}")
//                }
//            }
//        }
//        //Log.d("HomeViewModel", "Timer started")
//    }
//
//    // Function to stop/pause the timer
//    fun stopPauseTimer() {
//        if (timerJob?.isActive == true) {
//            timerJob?.cancel()
//            //Log.d("HomeViewModel", "Timer paused at: ${formatTime(secondsElapsed)}")
//        } else {
//            startTimer()
//        }
//    }
//
//    // Function to reset the timer
//    fun resetTimer() {
//        stopPauseTimer()
//        secondsElapsed = 0
//        _counter.value = 0 // Reset the counter to 0
//        savedStateHandle.set("counter", 0)
//        _time.value = formatTime(secondsElapsed)
//        //Log.d("HomeViewModel", "Timer reset")
//    }
//
//
//    private fun formatTime(seconds: Long): String {
//        val incrementedSeconds = seconds + (_counter.value?:0)
//        val hours = incrementedSeconds / 3600
//        val minutes = (incrementedSeconds % 3600) / 60
//        val secs = incrementedSeconds % 60
//        return String.format("%02d:%02d:%02d", hours, minutes, secs)
//    }
//
//
//    override fun onCleared() {
//        super.onCleared()
//        timerJob?.cancel() // Ensure the coroutine is cancelled to avoid memory leaks
//    }
//
//
//}
