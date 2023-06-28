package com.nokopi.stopwatchtimer

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.nokopi.stopwatchtimer.databinding.TimePickDialogBinding

class TimePickerDialogFragment(private val chimeMode: Int, private val settingTime: SettingTime) : DialogFragment(),
    NumberPicker.OnValueChangeListener {

    private var listener: DialogListener? = null

    private var _binding: TimePickDialogBinding? = null
    private val binding get() = _binding!!

    interface DialogListener {
        fun onNumberPickerDialogPositiveClick(
            dialog: DialogFragment,
            settingTime: SettingTime,
            chimeMode: Int?
        )

        fun onNumberPickerDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DataBindingUtil.inflate(
            requireActivity().layoutInflater,
            R.layout.time_pick_dialog,
            null,
            false
        )
        val viewModel = ViewModelProvider(this)[TimePickerViewModel::class.java]
        binding.viewModel = viewModel

        binding.tenMinute.maxValue = 5
        binding.tenMinute.minValue = 0
        binding.tenMinute.value = if (isInt(settingTime.tenMinute)) settingTime.tenMinute.toInt() else 0
        binding.minute.maxValue = 9
        binding.minute.minValue = 0
        binding.minute.value = if (isInt(settingTime.minute)) settingTime.minute.toInt() else 0
        binding.tenSecond.maxValue = 5
        binding.tenSecond.minValue = 0
        binding.tenSecond.value = if (isInt(settingTime.tenSecond)) settingTime.tenSecond.toInt() else 0
        binding.second.maxValue = 9
        binding.second.minValue = 0
        binding.second.value = if (isInt(settingTime.second)) settingTime.second.toInt() else 0

        val dialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setTitle("自動チャイム設定")
            .setPositiveButton("OK") { _, _ ->
                when (chimeMode) {
                    MainActivity.FIRST_CHIME -> {
                        val settingTime = SettingTime(binding.tenMinute.value.toString(), binding.minute.value.toString(), binding.tenSecond.value.toString(), binding.second.value.toString())
                        listener?.onNumberPickerDialogPositiveClick(
                            this,
                            settingTime,
                            chimeMode
                        )
                    }

                    MainActivity.SECOND_CHIME -> {
                        val settingTime = SettingTime(binding.tenMinute.value.toString(), binding.minute.value.toString(), binding.tenSecond.value.toString(), binding.second.value.toString())
                        listener?.onNumberPickerDialogPositiveClick(
                            this,
                            settingTime,
                            chimeMode
                        )
                    }

                    MainActivity.END_CHIME -> {
                        val settingTime = SettingTime(binding.tenMinute.value.toString(), binding.minute.value.toString(), binding.tenSecond.value.toString(), binding.second.value.toString())
                        listener?.onNumberPickerDialogPositiveClick(
                            this,
                            settingTime,
                            chimeMode
                        )
                    }
                }
            }
            .setNegativeButton("キャンセル") { dialog, _ ->
                dialog.cancel()
            }

        return dialog.create()
    }

    private fun isInt(string: String): Boolean {
        return string.toIntOrNull() != null
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(("$context must implement NoticeDialogListener"))
        }
    }

    override fun onDestroy() {
        _binding = null
        listener = null
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}