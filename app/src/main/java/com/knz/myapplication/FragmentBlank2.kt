package com.knz.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import com.knz.myapplication.databinding.FragmentBlank2Binding

class FragmentBlank2 : Fragment(R.layout.fragment_blank2) {
    private val viewModel: FragmentsViewModel by activityViewModels()
    private var _binding: FragmentBlank2Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBlank2Binding.bind(view)

        binding.blank2Btn.setOnClickListener {
            val text = binding.blank2Input.text.toString()
            if (text.isNotEmpty()) {
                viewModel.sendToFragment1(text)
                it.hideKeyboard()
                binding.blank2Input.clearFocus()
                binding.blank2Input.text.clear()
            }
        }

        viewModel.textFragment2.observe(viewLifecycleOwner) { text ->
            binding.blank2Output.text = text
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