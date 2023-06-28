package com.nokopi.stopwatchtimer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel: ViewModel() {

    private var _firstChimeTime: MutableLiveData<String> = MutableLiveData<String>("${MainActivity.firstSettingTime.tenMinute}${MainActivity.firstSettingTime.minute}:${MainActivity.firstSettingTime.tenSecond}${MainActivity.firstSettingTime.second}")
    val firstChimeTime: LiveData<String>
        get() = _firstChimeTime

    private var _secondChimeTime: MutableLiveData<String> = MutableLiveData<String>("${MainActivity.secondSettingTime.tenMinute}${MainActivity.secondSettingTime.minute}:${MainActivity.secondSettingTime.tenSecond}${MainActivity.secondSettingTime.second}")
    val secondChimeTime: LiveData<String>
        get() = _secondChimeTime

    private var _endChimeTime: MutableLiveData<String> = MutableLiveData<String>("${MainActivity.endSettingTime.tenMinute}${MainActivity.endSettingTime.minute}:${MainActivity.endSettingTime.tenSecond}${MainActivity.endSettingTime.second}")
    val endChimeTime: LiveData<String>
        get() = _endChimeTime

    fun setFirstTime(settingTime: SettingTime) {
        _firstChimeTime.value = "${settingTime.tenMinute}${settingTime.minute}:${settingTime.tenSecond}${settingTime.second}"
        Log.i("firstTime", _firstChimeTime.value.toString())
    }

    fun setSecondTime(settingTime: SettingTime) {
        _secondChimeTime.value = "${settingTime.tenMinute}${settingTime.minute}:${settingTime.tenSecond}${settingTime.second}"
    }

    fun setEndTime(settingTime: SettingTime) {
        _endChimeTime.value = "${settingTime.tenMinute}${settingTime.minute}:${settingTime.tenSecond}${settingTime.second}"
    }

}