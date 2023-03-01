package com.jcqh.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.jcqh.littlelemon.screens.*
import com.jcqh.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LittleLemonTheme {
                val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())

                var orderMenuItems by remember {
                    mutableStateOf(false)
                }

                var menuItems = if (orderMenuItems) {
                    databaseMenuItems.sortedBy { it.title }
                } else {
                    databaseMenuItems
                }

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = if (isUserLoggedIn()) Home.route else Onboarding.route) {
                    composable(Onboarding.route) {
                        Onboarding(navController, ::saveUserData)
                    }
                    composable(Home.route) {
                        Home(navController, menuItems)
                    }
                    composable(Profile.route) {
                        Profile(navController, ::getUserData, ::clearUserData)
                    }
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                val menuItems = fetchMenu()
                saveMenuToDatabase(menuItems)
            }
        }
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItem>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }

    private fun saveUserData(userData: UserData) {
        val sharedPreferences = getSharedPreferences(LITTLE_LEMON, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(FIRST_NAME, userData.firstName)
        editor.putString(LAST_NAME, userData.lastName)
        editor.putString(EMAIL, userData.email)
        editor.apply()
        editor.commit()
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences(LITTLE_LEMON, Context.MODE_PRIVATE)
        return !sharedPreferences.getString(FIRST_NAME, "").equals("")
    }

    private fun getUserData(key: String): String {
        val sharedPreferences = getSharedPreferences(LITTLE_LEMON, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key,"")!!
    }

    private fun clearUserData() {
        val sharedPreferences = getSharedPreferences(LITTLE_LEMON, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private suspend fun fetchMenu(): List<MenuItem> {
        val response: MenuItemsList =
            httpClient.get(getString(R.string.api_url)).body()
        return response.menu
    }

    companion object {
        val LITTLE_LEMON = "littleLemon"
        val FIRST_NAME = "firstName"
        val LAST_NAME = "lastName"
        val EMAIL = "email"
    }
}
