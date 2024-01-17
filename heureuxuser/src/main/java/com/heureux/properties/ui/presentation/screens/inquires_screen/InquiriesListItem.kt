package com.heureux.properties.ui.presentation.screens.inquires_screen

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.heureux.properties.data.types.HeureuxProperty
import com.heureux.properties.data.types.InquiryItem
import java.time.LocalDateTime

@Composable
fun InquiriesListItem(
    inquiry: InquiryItem,
    property: HeureuxProperty?
) {
    val dateTime = LocalDateTime.parse(inquiry.time)

    ListItem(overlineContent = {
        Text(text = "Date: ${dateTime.dayOfMonth}/${dateTime.monthValue}/${dateTime.year}    Times: ${dateTime.hour}:${dateTime.minute}")
    }, headlineContent = {
        Text(text = property?.name ?: "")
    }, supportingContent = {
        Text(text = property?.price ?: "")
    })
}