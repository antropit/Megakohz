package ru.antropit.megakohz.api.model

data class Details(
    var id: Int,
    var name: String,
    var img: String,
    var description: String,
    var lat: Double,
    var lon: Double,
    var www: String,
    var phone: String
)