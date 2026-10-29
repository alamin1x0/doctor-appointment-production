package com.developeralamin.appointmentdoctor.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.developeralamin.appointmentdoctor.R
import com.developeralamin.appointmentdoctor.adapter.CategoryAdapter
import com.developeralamin.appointmentdoctor.adapter.TopDoctorAdapter
import com.developeralamin.appointmentdoctor.databinding.ActivityMainBinding
import com.developeralamin.appointmentdoctor.domain.CategoryModel
import com.developeralamin.appointmentdoctor.domain.DoctorModel
import com.developeralamin.appointmentdoctor.helper.WishlistManager
import com.developeralamin.appointmentdoctor.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    private var allDoctors = mutableListOf<DoctorModel>()
    private var allCategories = mutableListOf<CategoryModel>()
    private var isWishlistTabSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCategory()
        initDoctors()
        initSearch()
        initSeeAll()
        initBottomNavigation()
        initSwipeRefresh()
    }

    override fun onResume() {
        super.onResume()
        if (allDoctors.isNotEmpty()) {
            updateDoctorList()
        }
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadCategory()
            viewModel.loadDoctors()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initBottomNavigation() {
        binding.apply {
            btnExplorer.setOnClickListener {
                selectTab(isExplorer = true)
            }

            btnWishlist.setOnClickListener {
                selectTab(isExplorer = false)
            }
        }
    }

    private fun selectTab(isExplorer: Boolean) {
        isWishlistTabSelected = !isExplorer
        binding.apply {
            if (isExplorer) {
                imgExplorer.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.darkPurple))
                txtExplorer.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.darkPurple))
                txtExplorer.setTypeface(null, Typeface.BOLD)

                imgWishlist.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.grey))
                txtWishlist.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.grey))
                txtWishlist.setTypeface(null, Typeface.NORMAL)
            } else {
                imgWishlist.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.darkPurple))
                txtWishlist.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.darkPurple))
                txtWishlist.setTypeface(null, Typeface.BOLD)

                imgExplorer.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.grey))
                txtExplorer.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.grey))
                txtExplorer.setTypeface(null, Typeface.NORMAL)
            }
        }
        updateDoctorList()
    }

    private fun updateDoctorList() {
        val listToShow = if (isWishlistTabSelected) {
            val favIds = WishlistManager.getFavoriteIds(this)
            allDoctors.filter { favIds.contains(it.Id) }.toMutableList()
        } else {
            allDoctors
        }
        binding.viewTopDoctors.adapter = TopDoctorAdapter(listToShow)
    }

    private fun initSeeAll() {
        binding.apply {
            // "See All" for Doctor Speciality
            textView5.setOnClickListener {
                startActivity(Intent(this@MainActivity, TopDoctorActivity::class.java))
            }

            // "See All" for Top Doctors
            doctorListsAll.setOnClickListener {
                startActivity(Intent(this@MainActivity, TopDoctorActivity::class.java))
            }
        }
    }

    private fun initSearch() {
        binding.editTextText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterData(s.toString().trim())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterData(query: String) {
        val filteredDoctors = if (query.isEmpty()) {
            if (isWishlistTabSelected) {
                val favIds = WishlistManager.getFavoriteIds(this)
                allDoctors.filter { favIds.contains(it.Id) }.toMutableList()
            } else {
                allDoctors
            }
        } else {
            val currentList = if (isWishlistTabSelected) {
                val favIds = WishlistManager.getFavoriteIds(this)
                allDoctors.filter { favIds.contains(it.Id) }
            } else {
                allDoctors
            }
            currentList.filter { doctor ->
                doctor.Name.contains(query, ignoreCase = true) ||
                        doctor.Special.contains(query, ignoreCase = true) ||
                        doctor.Location.contains(query, ignoreCase = true)
            }.toMutableList()
        }

        val filteredCategories = if (query.isEmpty()) {
            allCategories
        } else {
            allCategories.filter { category ->
                category.Name.contains(query, ignoreCase = true)
            }.toMutableList()
        }

        binding.viewTopDoctors.adapter = TopDoctorAdapter(filteredDoctors)
        binding.viewCategory.adapter = CategoryAdapter(filteredCategories)
    }

    private fun initDoctors() {
        binding.apply {
            viewTopDoctorsProgressbar.visibility = View.VISIBLE
            viewModel.doctors.observe(this@MainActivity, Observer { doctorsList ->
                allDoctors = doctorsList
                viewTopDoctors.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                updateDoctorList()
                viewTopDoctorsProgressbar.visibility = View.GONE
            })

            viewModel.loadDoctors()
        }
    }

    private fun initCategory() {
        binding.apply {
            viewCategoryProgressbar.visibility = View.VISIBLE
            viewModel.category.observe(this@MainActivity, Observer { categoryList ->
                allCategories = categoryList
                viewCategory.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                viewCategory.adapter = CategoryAdapter(categoryList)
                viewCategoryProgressbar.visibility = View.GONE
            })

            viewModel.loadCategory()
        }
    }
}