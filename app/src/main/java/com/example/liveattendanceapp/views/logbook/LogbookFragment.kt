package com.example.liveattendanceapp.views.logbook

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.liveattendanceapp.R
import com.example.liveattendanceapp.databinding.FragmentLogbookBinding
import com.example.liveattendanceapp.dialog.MyDialog
import com.example.liveattendanceapp.hawkstorage.HawkStorage
import com.example.liveattendanceapp.model.AttendanceResponse
import com.example.liveattendanceapp.networking.ApiServices
import com.example.liveattendanceapp.views.attendance.AttendanceFragment
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class LogbookFragment : Fragment() {
    
    private lateinit var binding: FragmentLogbookBinding
    private lateinit var startTime: String
    private lateinit var endTime: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogbookBinding.inflate(layoutInflater)


        binding.tiJamMulai.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            TimePickerDialog(
                requireContext(), { _, hourOfDay, minute ->
                    val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                    startTime = formattedTime;
                    binding.etJamMulai.text = startTime
                }, startHour, startMinute, true
            ).show()
        }
        
        binding.tiJamSelesai.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            binding.tiJamSelesai.setOnClickListener {
                TimePickerDialog(
                    requireContext(), { _, hourOfDay, minute ->
                        val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                        endTime = formattedTime
                        binding.etJamSelesai.text = endTime
                    }, startHour, startMinute, true
                ).show()
            }
        }

        binding.btnSend.setOnClickListener{
            if (binding.etIsiTugasPokok.text.toString().isEmpty()){
                context?.toast("Harap isi tugas pokok")
                return@setOnClickListener
            }

            if (startTime.isEmpty()){
                context?.toast("Harap isi jam mulai")
                return@setOnClickListener
            }
            if (endTime.isEmpty()){
                context?.toast("Harap isi jam selesai")
                return@setOnClickListener
            }

            if (binding.etKeterangan.text.toString().isEmpty()){
                context?.toast("Harap isi Keterangan")
                return@setOnClickListener
            }

            MyDialog.showProgressDialog(context)
            addLogBook(binding.etIsiTugasPokok.text.toString(), startTime.toString(), endTime.toString(),
            binding.etKeterangan.text.toString())
        }

        return binding.root
    }

    private fun addLogBook(task: String, starttime  : String, endtime: String, keterangan: String) {
        val token = HawkStorage.instance(context).getToken()
        ApiServices.getLiveAttendanceServices()
            .logbook("Bearer $token", task, starttime, endtime, keterangan)
            .enqueue(object : Callback<AttendanceResponse> {
                override fun onResponse(
                    call: Call<AttendanceResponse>,
                    response: Response<AttendanceResponse>
                ) {
                    MyDialog.hideDialog()
                    if (response.isSuccessful){
                        val attendanceResponse = response.body()

                        MyDialog.dynamicDialog(context, "Berhasil mengisi logbook", attendanceResponse?.message.toString())

                    }else{
                        MyDialog.dynamicDialog(context, getString(R.string.alert), getString(R.string.something_wrong))
                    }
                }

                override fun onFailure(call: Call<AttendanceResponse>, t: Throwable) {
                    MyDialog.hideDialog()
                    Log.e("TAG", "Error: ${t.message}")
                }

            })
    }
}