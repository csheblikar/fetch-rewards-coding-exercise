package com.csheblikar.fetchapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.csheblikar.fetchapp.data.model.Item
import com.csheblikar.fetchapp.data.network.RetrofitClient
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _items = mutableStateOf<Map<Int, List<Item>>>(emptyMap())
    val items: State<Map<Int, List<Item>>> = _items

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getItems()
                val processed = response
                    .filter { !it.name.isNullOrBlank() }
                    .sortedWith(compareBy({ it.listId }, { it.name }))
                    .groupBy { it.listId ?: -1 }

                _items.value = processed

            } catch (e: Exception) {
                // handle error
            }
        }
    }
}