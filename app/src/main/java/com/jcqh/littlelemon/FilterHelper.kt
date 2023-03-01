package com.jcqh.littlelemon

class FilterHelper {
    fun filterItems(type: FilterType, menuItems: List<MenuItemRoom>): List<MenuItemRoom>{
        return when (type){
            FilterType.All -> menuItems
            FilterType.Starters -> menuItems.filter { item -> item.category == "starters" }
            FilterType.Mains -> menuItems.filter { item -> item.category == "mains" }
            FilterType.Desserts -> menuItems.filter { item -> item.category == "desserts" }
            FilterType.Drinks -> menuItems.filter { item -> item.category == "drinks" }
        }
    }
}