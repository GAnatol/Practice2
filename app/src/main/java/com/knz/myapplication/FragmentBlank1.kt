package com.knz.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import com.knz.myapplication.databinding.FragmentBlank1Binding

class FragmentBlank1 : Fragment(R.layout.fragment_blank1) {
    private val viewModel: FragmentsViewModel by activityViewModels()
    private var _binding: FragmentBlank1Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBlank1Binding.bind(view)

        binding.blank1Btn.setOnClickListener {
            val text = binding.blank1Input.text.toString()
            if (text.isNotEmpty()) {
                viewModel.sendToFragment2(text)
                it.hideKeyboard()
                binding.blank1Input.clearFocus()
                binding.blank1Input.text.clear()
            }
        }

        viewModel.textFragment1.observe(viewLifecycleOwner) { text ->
            binding.blank1Output.text = text
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun View.hideKeyboard() {
        val input = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(windowToken, 0)
    }
}