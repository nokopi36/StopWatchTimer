package com.nokopi.stopwatchtimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.nokopi.stopwatchtimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var fragmentManager: FragmentManager

    companion object {
        const val FIRST_CHIME = 1
        const val SECOND_CHIME = 2
        const val END_CHIME = 3
        var firstSettingTime = SettingTime("-", "-", "-", "-")
        var secondSettingTime = SettingTime("-", "-", "-", "-")
        var endSettingTime = SettingTime("-", "-", "-", "-")
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
            when (it) {
                "59:59" -> {
                    viewModel.playChimeSound()
                    viewModel.disableStartBtn()
                }
                "${viewModel.firstChimeTime.value}" -> {
                    viewModel.playChimeSound()
                }
                "${viewModel.secondChimeTime.value}" -> {
                    viewModel.playChimeSound()
                }
                "${viewModel.endChimeTime.value}" -> {
                    viewModel.playEndChimeSound()
                }
            }
        }

        binding.settingBtn.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentManager.fragments.forEach { fragment ->
            fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.setFirstTime(firstSettingTime)
        viewModel.setSecondTime(secondSettingTime)
        viewModel.setEndTime(endSettingTime)
    }

}