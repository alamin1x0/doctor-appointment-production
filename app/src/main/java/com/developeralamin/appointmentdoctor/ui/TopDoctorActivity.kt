package com.developeralamin.appointmentdoctor.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.developeralamin.appointmentdoctor.adapter.TopDoctorAdapter2
import com.developeralamin.appointmentdoctor.databinding.ActivityTopDoctorBinding
import com.developeralamin.appointmentdoctor.viewmodel.MainViewModel

class TopDoctorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopDoctorBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDoctors()
    }

    private fun initDoctors() {
        val selectedCategory = intent.getStringExtra("categoryName")

        binding.apply {
            topDoctorProgressBar.visibility = View.VISIBLE
            viewModel.doctors.observe(this@TopDoctorActivity, Observer { doctorsList ->
                val filteredList = if (!selectedCategory.isNullOrEmpty()) {
                    doctorsList.filter { doctor ->
                        doctor.Special.contains(selectedCategory, ignoreCase = true) ||
                                selectedCategory.contains(doctor.Special, ignoreCase = true)
                    }.toMutableList()
                } else {
                    doctorsList
                }

                val listToShow = if (filteredList.isNotEmpty()) filteredList else doctorsList

                viewTopDoctor.layoutManager =
                    LinearLayoutManager(
                        this@TopDoctorActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                viewTopDoctor.adapter = TopDoctorAdapter2(listToShow)
                topDoctorProgressBar.visibility = View.GONE
            })
        }

        viewModel.loadDoctors()

        binding.backBtn.setOnClickListener { finish() }
    }
}