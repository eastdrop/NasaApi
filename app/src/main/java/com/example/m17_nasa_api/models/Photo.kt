package com.example.m17_nasa_api.models

import java.util.Date

data class PhotoResponse(
    val id: Int,
    val sol: Int,
    val camera: Camera,
    val img_src: String,
    val earth_date: Date,
    val rover: Rover,
)

data class Camera(
    val id: Int,
    val name: String,
    val roverId: Int,
    val full_name: String
)

data class Rover(
    val id: Int,
    val name: String,
    val landing_date: Date,
    val launch_date: Date,
    val status: String,
    val max_sol: Int,
    val max_date: Date,
    val total_photos: Int,
    val camera: List<RoverCamera>
)

data class RoverCamera(
    val name: String,
    val full_name: String
)