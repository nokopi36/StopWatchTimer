package com.nokopi.stopwatchtimer

import android.app.Application
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class MainViewModel(app: Application): ViewModel() {

    companion object {
        private const val TERM_MILLISECOND: Long = 100
    }

    private var timer = Timer()
    private val timerTask: TimerTask.() -> Unit = {
        viewModelScope.launch {
            time += TERM_MILLISECOND
            _text.value = time.toString()
            _timerText.value = dataFormat.format(time)
        }
    }

    private val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
    private val sound = SoundPool.Builder().setMaxStreams(1).setAudioAttributes(audioAttributes).build()
    private val firstChime = sound.load(app, R.raw.bell, 1)
    private val endChime = sound.load(app, R.raw.end_chime, 1)


    private var _timerText: MutableLiveData<String> = MutableLiveData<String>("00:00:0")
    val timerText: LiveData<String>
        get() = _timerText

    private var _startTimerBtnEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>(true)
    val startTimerBtnEnabled: LiveData<Boolean>
        get() = _startTimerBtnEnabled

    private var _stopTimerBtnEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val stopTimerBtnEnabled: LiveData<Boolean>
        get() = _stopTimerBtnEnabled

    private var _resetTimerBtnEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val resetTimerBtnEnabled: LiveData<Boolean>
        get() = _resetTimerBtnEnabled

    private var _text: MutableLiveData<String> = MutableLiveData<String>("0")
    val text: LiveData<String>
        get() = _text

    private var _firstChimeTime: MutableLiveData<String> = MutableLiveData<String>("--:--")
    val firstChimeTime: LiveData<String>
        get() = _firstChimeTime

    private var _endChimeTime: MutableLiveData<String> = MutableLiveData<String>("--:--")
    val endChimeTime: LiveData<String>
        get() = _endChimeTime

//    private var time = 3595000L // デバッグ用　59分55秒
    private var time = 0L
    private val dataFormat = SimpleDateFormat("mm:ss:S", Locale.getDefault())


    fun resetTimer() {
        time = 0L
        _text.value = time.toString()
        _timerText.value = dataFormat.format(time)
        _startTimerBtnEnabled.value = true
        _resetTimerBtnEnabled.value = false
    }

    fun startTimer() {
        _startTimerBtnEnabled.value = false
        _stopTimerBtnEnabled.value = true
        _resetTimerBtnEnabled.value = false
        timer = Timer()
        timer.scheduleAtFixedRate(TERM_MILLISECOND, TERM_MILLISECOND, timerTask)
    }

    fun stopTimer() {
        _startTimerBtnEnabled.value = true
        _stopTimerBtnEnabled.value = false
        _resetTimerBtnEnabled.value = true
        timer.cancel()
    }

    fun disableStartBtn() {
        _startTimerBtnEnabled.value = false
        _stopTimerBtnEnabled.value = false
        _resetTimerBtnEnabled.value = true
        timer.cancel()
    }

    fun playFirstChimeSound() {
        sound.play(firstChime, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playEndChimeSound() {
        sound.play(endChime, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun setFirstTime(tenMinute: String, minute: String, tenSecond: String, second: String) {
        _firstChimeTime.value = "$tenMinute$minute:$tenSecond$second"
    }

    fun setEndTime(tenMinute: String, minute: String, tenSecond: String, second: String) {
        _endChimeTime.value = "$tenMinute$minute:$tenSecond$second"
    }

}