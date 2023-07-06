package com.nokopi.stopwatchtimer

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.nokopi.stopwatchtimer.databinding.ActivitySettingBinding

class SettingActivity: AppCompatActivity(), TimePickerDialogFragment.DialogListener {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingViewModel
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        viewModel = ViewModelProvider(this)[SettingViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        fragmentManager = supportFragmentManager

        setSupportActionBar(binding.settingToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.firstTimeSettingBtn.setOnClickListener {
            val dialog = TimePickerDialogFragment(MainActivity.FIRST_CHIME, MainActivity.firstSettingTime)
            dialog.show(fragmentManager, "FirstNumberPicker")
        }

        binding.secondTimeSettingBtn.setOnClickListener {
            val dialog = TimePickerDialogFragment(MainActivity.SECOND_CHIME, MainActivity.secondSettingTime)
            dialog.show(fragmentManager, "SecondNumberPicker")
        }

        binding.endTimeSettingBtn.setOnClickListener {
            val dialog = TimePickerDialogFragment(MainActivity.END_CHIME, MainActivity.endSettingTime)
            dialog.show(fragmentManager, "EndNumberPicker")
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNumberPickerDialogPositiveClick(
        dialog: DialogFragment,
        settingTime: SettingTime,
        chimeMode: Int?
    ) {
        when (chimeMode) {
            MainActivity.FIRST_CHIME -> {
                MainActivity.firstSettingTime = settingTime
                viewModel.setFirstTime(settingTime)
            }
            MainActivity.SECOND_CHIME -> {
                MainActivity.secondSettingTime = settingTime
                viewModel.setSecondTime(settingTime)
            }
            MainActivity.END_CHIME -> {
                MainActivity.endSettingTime = settingTime
                viewModel.setEndTime(settingTime)
            }
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