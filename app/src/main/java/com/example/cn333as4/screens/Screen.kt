package com.example.cn333as4.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.cn333as4.R

sealed class Screen(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    companion object{
        val screens = listOf(Temperature, Distances, Length)
    }

    private object Temperature: Screen(
        route = "temperature",
        R.string.temperature,
        R.drawable.outline_thermostat_24
    )

    private object Distances: Screen(
        route = "distances",
        R.string.distances,
        R.drawable.outline_square_foot_24
    )

    private object Length: Screen(
        route = "length",
        R.string.length,
        R.drawable.outline_speed_24
    )
}