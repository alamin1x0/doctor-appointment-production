package com.developeralamin.appointmentdoctor.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.developeralamin.appointmentdoctor.databinding.ActivityIntoBinding

class IntoActivity : BaseActivity() {

    private lateinit var binding: ActivityIntoBinding
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        navigateToMain()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            startBtn.setOnClickListener {
                handler.removeCallbacks(runnable)
                navigateToMain()
            }
        }

        handler.postDelayed(runnable, 3000)
    }

    private fun navigateToMain() {
        startActivity(Intent(this@IntoActivity, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}