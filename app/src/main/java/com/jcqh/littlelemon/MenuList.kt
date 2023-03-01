package com.jcqh.littlelemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuItemsList(
    val menu: List<MenuItem>
)

@Serializable
data class MenuItem(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price: Double,
    @SerialName("image")
    val image: String,
    @SerialName("category")
    val category: String
){
    fun toMenuItemRoom() = MenuItemRoom(
        id, title, description, price, image, category
    )
}