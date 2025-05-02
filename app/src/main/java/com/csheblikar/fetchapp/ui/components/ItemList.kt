package com.csheblikar.fetchapp.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csheblikar.fetchapp.viewmodel.MainViewModel

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ItemList(viewModel: MainViewModel = viewModel()) {
    val groupedItems by viewModel.items
    var expandedListId by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp, 48.dp, 28.dp, 28.dp)
    ) {
        groupedItems.forEach { (listId, items) ->
            item {
                val isExpanded = expandedListId == listId


                Column {
                    ListGroupHeader(
                        listId = listId,
                        isExpanded = isExpanded,
                        onToggle = {
                            expandedListId = if (isExpanded) null else listId
                        }
                    )

                    AnimatedVisibility(visible = isExpanded) {
                        Column {
                            items.forEach { item ->
                                ItemCard(itemName = item.name ?: "")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ListGroupHeader(
    listId: Int,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(vertical = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "List ID: $listId",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = if (isExpanded)
                Icons.Default.KeyboardArrowUp
            else
                Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun ItemCard(itemName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Text(
            text = itemName,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


