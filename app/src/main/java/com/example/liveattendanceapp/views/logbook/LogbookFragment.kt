package com.example.liveattendanceapp.views.logbook

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.liveattendanceapp.R
import java.util.Calendar

class LogbookFragment : Fragment() {

    private lateinit var tv_jam_mulai: TextView
    private lateinit var tv_jam_selesai: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logbook, container, false)

        tv_jam_mulai = view.findViewById<TextView>(R.id.et_jam_mulai)

        tv_jam_mulai.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            TimePickerDialog(
                requireContext(), TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                    tv_jam_mulai.setText(formattedTime)
                }, startHour, startMinute, true
            ).show()
        }

        tv_jam_selesai = view.findViewById<TextView>(R.id.et_jam_selesai)

        tv_jam_selesai.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            tv_jam_selesai.setOnClickListener {
                TimePickerDialog(
                    requireContext(), TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                        tv_jam_selesai.setText(formattedTime)
                    }, startHour, startMinute, true
                ).show()
            }
        }
        return view
    }
}