package com.developeralamin.appointmentdoctor.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developeralamin.appointmentdoctor.databinding.ViewholderCategoryBinding
import com.developeralamin.appointmentdoctor.domain.CategoryModel
import com.developeralamin.appointmentdoctor.ui.TopDoctorActivity

class CategoryAdapter(val items: MutableList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleTxt.text = item.Name

        Glide.with(context)
            .load(item.Picture)
            .into(holder.binding.img)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, TopDoctorActivity::class.java)
            intent.putExtra("categoryName", item.Name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}