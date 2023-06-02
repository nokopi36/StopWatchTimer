package com.nokopi.stopwatchtimer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimePickerViewModel: ViewModel() {

    companion object {
        private const val CHIME_MODE = 0
        private const val FIRST_CHIME = 1
        private const val END_CHIME = 2
    }

    private var _chimeMode: MutableLiveData<Int> = MutableLiveData<Int>(0)
    val chimeMode: LiveData<Int>
        get() = _chimeMode

    private var _chimeModeText: MutableLiveData<String> = MutableLiveData<String>()
    val chimeModeText: LiveData<String>
        get() = _chimeModeText

    fun changeFirstTimeMode() {
        if (_chimeMode.value == CHIME_MODE || _chimeMode.value == END_CHIME) {
            _chimeMode.value = FIRST_CHIME
        } else {
            _chimeMode.value = CHIME_MODE
        }
        Log.i("chimeMode", _chimeMode.value.toString())
    }

    fun changeEndTimeMode() {
        if (_chimeMode.value == CHIME_MODE || _chimeMode.value == FIRST_CHIME) {
            _chimeMode.value = END_CHIME
        } else {
            _chimeMode.value = CHIME_MODE
        }
        Log.i("chimeMode", _chimeMode.value.toString())
    }
}