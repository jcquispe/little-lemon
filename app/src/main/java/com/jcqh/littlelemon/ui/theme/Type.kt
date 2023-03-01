package com.jcqh.littlelemon.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    h1 = TextStyle(
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = LittleLemonColor.charcoal
    ),
    h2 = TextStyle(
        color = LittleLemonColor.charcoal,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    ),
    h3 = TextStyle(
        color = LittleLemonColor.cloud,
        fontSize = 24.sp
    ),
    h4 = TextStyle(
        color = LittleLemonColor.green,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    h5 = TextStyle(
        color = LittleLemonColor.charcoal,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        color = LittleLemonColor.charcoal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        color = LittleLemonColor.charcoal,
        fontSize = 13.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = LittleLemonColor.green
    ),
    button = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
)
