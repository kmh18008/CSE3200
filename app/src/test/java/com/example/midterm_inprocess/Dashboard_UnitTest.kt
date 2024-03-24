package com.example.midterm_inprocess
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.example.midterm_inprocess.ui.dashboard.DashboardViewModel
import com.example.midterm_inprocess.ui.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class Dashboard_UnitTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        viewModel = DashboardViewModel(SavedStateHandle(mapOf("secondsElapsed" to 0L)))
    }

    @Test
    fun `initial time is correctly formatted`() = runTest {
        assertEquals("00:00:00", viewModel.time.getOrAwaitValue())
    }

    @Test
    fun `formatTime returns correctly formatted time`() = runTest {
        val formattedTime = viewModel.formatTime(3661L)
        assertEquals("01:01:01", formattedTime)
    }

    @Test
    fun `timer is reset to 00 on resetTimer`() = runTest {
        viewModel.resetTimer()
        assertEquals("00:00:00", viewModel.time.getOrAwaitValue())
    }

    fun <T> LiveData<T>.getOrAwaitValue(): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T) {
                data = t
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)
        try {
            if (!latch.await(2, TimeUnit.SECONDS)) {
                throw TimeoutException("LiveData value was never set.")
            }
        } finally {
            this.removeObserver(observer)
        }
        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}