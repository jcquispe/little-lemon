package com.jcqh.littlelemon.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jcqh.littlelemon.*
import com.jcqh.littlelemon.R
import com.jcqh.littlelemon.ui.theme.LittleLemonColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun Home(navController: NavHostController, menuItems: List<MenuItemRoom>) {
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            TopAppBar()
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .width(60.dp)
                    .align(Alignment.TopEnd)
                    .clickable {
                        navController.navigate(Profile.route)
                    }
            )
        }
        HeroSection(menuItems)
    }
}

@Composable
fun HeroSection(menuItems: List<MenuItemRoom>) {
    val itemsState: MutableStateFlow<List<MenuItemRoom>> = MutableStateFlow(menuItems)
    val items by itemsState.collectAsState()
    var selectedCategory by remember {
        mutableStateOf("All")
    }
    var searchPhrase by remember {
        mutableStateOf("")
    }
    val categories = listOf("All", "Starters", "Mains", "Desserts", "Drinks")

    Column(
        modifier = Modifier
            .background(LittleLemonColor.green)
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = LittleLemonColor.yellow
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.location),
                    fontSize = 24.sp,
                    color = LittleLemonColor.cloud
                )
                Text(
                    text = stringResource(id = R.string.description),
                    style = MaterialTheme.typography.body1,
                    color = LittleLemonColor.cloud,
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 28.dp, end = 20.dp)
                        .fillMaxWidth(0.6f)
                )
            }
            Column(
                modifier = Modifier.padding(top = 15.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hero),
                    contentDescription = "Image",
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                )
            }
        }

        OutlinedTextField(
            value = searchPhrase,
            onValueChange = {
                searchPhrase = it
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_phrase)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = LittleLemonColor.green,
                focusedLabelColor = LittleLemonColor.green,
                cursorColor = LittleLemonColor.green
            )
        )

        if (searchPhrase.isNotBlank()) {
            itemsState.update {
                items.filter { item -> item.title.contains(searchPhrase, ignoreCase = true) }
            }
        }
    }
    Column(
        modifier = Modifier
            .padding(start = 12.dp, top = 12.dp, end = 12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.category_title),
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(top = 12.dp)
        )
        LazyRow(
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        ) {
            items(
                items = categories,
                itemContent = {
                    Button(
                        onClick = {
                            searchPhrase = ""
                            selectedCategory = it
                        },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = LittleLemonColor.cloud
                        ),
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            )
        }
        Divider(color = LittleLemonColor.cloud, thickness = 2.dp)

        if (selectedCategory != "All") {
            val type = when(selectedCategory) {
                "Starters" -> FilterType.Starters
                "Desserts" -> FilterType.Desserts
                "Mains" -> FilterType.Mains
                "Drinks" -> FilterType.Drinks
                else -> FilterType.All
            }
            itemsState.update {
                FilterHelper().filterItems(type, menuItems)
            }
        }
    }
    MenuItems(items)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItems(menuItems: List<MenuItemRoom>) {
    LazyColumn {
        items(
            items = menuItems,
            itemContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 8.dp, end = 12.dp),
                ) {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Row {
                        Column(
                            modifier = Modifier
                                .width(290.dp)
                                .padding(top = 10.dp)
                        ) {
                            Text(
                                text = it.description,
                                style = MaterialTheme.typography.body2,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "$%.2f".format(it.price),
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        GlideImage(
                            model = it.image,
                            contentDescription = it.title,
                            modifier = Modifier
                                .width(110.dp)
                                .height(80.dp)
                                .padding(top = 8.dp, start = 4.dp, bottom = 8.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Divider(color = LittleLemonColor.cloud, thickness = 1.dp)
                }
            }
        )
    }
}
