package com.knz.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentsViewModel : ViewModel() {
    val textFragment1 = MutableLiveData<String>()
    val textFragment2 = MutableLiveData<String>()

    fun sendToFragment1(text: String) {
        textFragment1.value = text
    }

    fun sendToFragment2(text: String) {
        textFragment2.value = text
    }
}