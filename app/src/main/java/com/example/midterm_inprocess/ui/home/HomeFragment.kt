package com.example.midterm_inprocess.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.midterm_inprocess.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.time.observe(viewLifecycleOwner) { formattedTime ->
            binding.timerText.text = formattedTime
        }

        binding.startButton.setOnClickListener { viewModel.startTimer() }
        binding.stopPauseButton.setOnClickListener { viewModel.stopPauseTimer() }
        binding.resetButton.setOnClickListener { viewModel.resetTimer() }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}










//package com.example.midterm_inprocess.ui.home
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.midterm_inprocess.databinding.FragmentHomeBinding
//
//class HomeFragment : Fragment() {
//
//    private lateinit var viewModel: HomeViewModel
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        // Instantiate the ViewModel scoped to this Fragment
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        // Observe the time LiveData
//        viewModel.time.observe(viewLifecycleOwner) { formattedTime ->
//            binding.timerText.text = formattedTime
//        }
//
//        // Observe the counter LiveData
//        viewModel.counter.observe(viewLifecycleOwner) { count ->
//            binding.incrementBox.text = count.toString() // TextView with ID incrementBox
//        }
//
//        // Restore state if exists
//        savedInstanceState?.getInt("incrementValue")?.let { savedValue ->
//            viewModel.setCounter(savedValue)
//        }
//
//        // Set up button click listeners
//        binding.incrementButton.setOnClickListener {
//            viewModel.incrementCounter() // Button with ID incrementButton
//        }
//        binding.resetCounterButton.setOnClickListener {
//            viewModel.resetCounter()
//        }
//
//        binding.startButton.setOnClickListener { viewModel.startTimer() }
//        binding.stopPauseButton.setOnClickListener { viewModel.stopPauseTimer() }
//        binding.resetButton.setOnClickListener { viewModel.resetTimer() }
//
//
//        return root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//
//
//}
