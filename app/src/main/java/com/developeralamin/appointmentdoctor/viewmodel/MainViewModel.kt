package com.developeralamin.appointmentdoctor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developeralamin.appointmentdoctor.domain.CategoryModel
import com.developeralamin.appointmentdoctor.domain.DoctorModel

class MainViewModel : ViewModel() {

    private val _category = MutableLiveData<MutableList<CategoryModel>>()
    private val _doctors = MutableLiveData<MutableList<DoctorModel>>()

    val category: LiveData<MutableList<CategoryModel>> = _category
    val doctors: LiveData<MutableList<DoctorModel>> = _doctors

    fun loadCategory() {
        val lists = mutableListOf(
            CategoryModel(1, "Cardiology", "https://cdn-icons-png.flaticon.com/512/3004/3004458.png"),
            CategoryModel(2, "Dental", "https://cdn-icons-png.flaticon.com/512/2818/2818366.png"),
            CategoryModel(3, "Neurology", "https://cdn-icons-png.flaticon.com/512/3063/3063822.png"),
            CategoryModel(4, "Orthopedic", "https://cdn-icons-png.flaticon.com/512/3004/3004452.png"),
            CategoryModel(5, "General Surgeon", "https://cdn-icons-png.flaticon.com/512/2966/2966327.png")
        )
        _category.value = lists
    }

    fun loadDoctors() {
        val lists = mutableListOf(
            DoctorModel(
                Address = "New York, USA",
                Biography = "Dr. Alexander Fleming is a top specialist in Cardiology with over 10 years of experience in leading international hospitals.",
                Expriense = 10,
                Id = 1,
                Location = "New York Central Hospital",
                Mobile = "+1 234 567 890",
                Name = "Dr. Alexander Fleming",
                Patiens = "1500+",
                Picture = "https://img.freepik.com/free-photo/doctor-offering-medical-teleconsultation_23-2149329007.jpg",
                Rating = 4.9,
                Site = "www.alexanderdoctor.com",
                Special = "Cardiologist"
            ),
            DoctorModel(
                Address = "Los Angeles, USA",
                Biography = "Dr. Sarah Jenkins is an experienced Dental Specialist providing state-of-the-art oral healthcare services.",
                Expriense = 7,
                Id = 2,
                Location = "City Dental Care, LA",
                Mobile = "+1 987 654 321",
                Name = "Dr. Sarah Jenkins",
                Patiens = "1000+",
                Picture = "https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-arms_409857-15638.jpg",
                Rating = 4.8,
                Site = "www.sarahdental.com",
                Special = "Dental Specialist"
            ),
            DoctorModel(
                Address = "Chicago, USA",
                Biography = "Dr. Michael Brown is a renowned Neurologist specializing in complex brain, nerve, and spinal treatments.",
                Expriense = 12,
                Id = 3,
                Location = "Chicago Neuro Institute",
                Mobile = "+1 555 123 4567",
                Name = "Dr. Michael Brown",
                Patiens = "2000+",
                Picture = "https://img.freepik.com/free-photo/portrait-hansome-young-male-doctor-man_171337-5068.jpg",
                Rating = 4.9,
                Site = "www.michaelneuro.com",
                Special = "Neurologist"
            ),
            DoctorModel(
                Address = "Houston, USA",
                Biography = "Dr. Emily Davis is a dedicated Orthopedic Surgeon treating joint, bone, and sports injury conditions.",
                Expriense = 8,
                Id = 4,
                Location = "Houston Medical Center",
                Mobile = "+1 444 888 9999",
                Name = "Dr. Emily Davis",
                Patiens = "1200+",
                Picture = "https://img.freepik.com/free-photo/woman-doctor-wearing-lab-coat-with-stethoscope-isolated_1303-29791.jpg",
                Rating = 4.7,
                Site = "www.emilyortho.com",
                Special = "Orthopedic Surgeon"
            )
        )
        _doctors.value = lists
    }
}