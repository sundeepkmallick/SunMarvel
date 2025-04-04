package com.sunmarvel.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sunmarvel.theme.primaryLight

@Composable
fun SearchBox(
    inputText: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(16.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp),
            )
            .border(
                width = 2.dp,
                color = primaryLight,
                shape = RoundedCornerShape(6.dp),
            ),
        value = inputText,
        onValueChange = onValueChange,
    )
}