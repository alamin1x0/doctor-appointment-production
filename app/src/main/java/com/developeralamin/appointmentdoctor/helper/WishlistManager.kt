package com.developeralamin.appointmentdoctor.helper

import android.content.Context
import android.content.SharedPreferences

object WishlistManager {
    private const val PREF_NAME = "doctor_wishlist_pref"
    private const val KEY_WISHLIST = "wishlist_ids"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isFavorite(context: Context, doctorId: Int): Boolean {
        val set = getPreferences(context).getStringSet(KEY_WISHLIST, emptySet()) ?: emptySet()
        return set.contains(doctorId.toString())
    }

    fun toggleFavorite(context: Context, doctorId: Int): Boolean {
        val pref = getPreferences(context)
        val currentSet = pref.getStringSet(KEY_WISHLIST, emptySet())?.toMutableSet() ?: mutableSetOf()
        val isFav: Boolean
        if (currentSet.contains(doctorId.toString())) {
            currentSet.remove(doctorId.toString())
            isFav = false
        } else {
            currentSet.add(doctorId.toString())
            isFav = true
        }
        pref.edit().putStringSet(KEY_WISHLIST, currentSet).apply()
        return isFav
    }

    fun getFavoriteIds(context: Context): Set<Int> {
        val set = getPreferences(context).getStringSet(KEY_WISHLIST, emptySet()) ?: emptySet()
        return set.mapNotNull { it.toIntOrNull() }.toSet()
    }
}