package com.nokopi.stopwatchtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.nokopi.stopwatchtimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TimePickerDialogFragment.DialogListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var fragmentManager: FragmentManager

    companion object {
        private const val FIRST_CHIME = 1
        private const val END_CHIME = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModelFactory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        fragmentManager = supportFragmentManager

        viewModel.timerText.observe(this) {
            if (it == "59:59:9") {
                viewModel.playFirstChimeSound()
                viewModel.disableStartBtn()
            } else if (it == "${viewModel.firstChimeTime.value}:0") {
                viewModel.playFirstChimeSound()
            } else if (it == "${viewModel.endChimeTime.value}:0") {
                viewModel.playEndChimeSound()
            }
        }

        binding.settingBtn.setOnClickListener {
            val dialog = TimePickerDialogFragment()
            dialog.show(fragmentManager, "NumberPicker")
        }

    }

    override fun onNumberPickerDialogPositiveClick(
        dialog: DialogFragment,
        tenMinute: String,
        minute: String,
        tenSecond: String,
        second: String,
        chimeMode: Int?
    ) {
        if (chimeMode == FIRST_CHIME) {
            viewModel.setFirstTime(tenMinute, minute, tenSecond, second)
        } else if (chimeMode == END_CHIME) {
            viewModel.setEndTime(tenMinute, minute, tenSecond, second)
        }
    }

    override fun onNumberPickerDialogNegativeClick(dialog: DialogFragment) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentManager.fragments.forEach { fragment ->
            fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
        }
    }

}