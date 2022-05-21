package com.dissiapps.crypto.ui.fgindex

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dissiapps.crypto.data.Repository
import com.dissiapps.crypto.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FGIndexViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var state = mutableStateOf(UiState.LOADING)
    var index = mutableStateOf("0")
    var lastUpdated = mutableStateOf("0")
    var classification = mutableStateOf("0")

    init {
        viewModelScope.launch {
            repository.getFGIndex().collect {

                state.value = when (it) {
                    is Resource.Loading -> UiState.LOADING
                    is Resource.Success -> UiState.SUCCESS
                    else -> UiState.ERROR
                }

                val data = it.data
                if (data?.isNotEmpty() == true){
                    index.value = data[0].value
                    classification.value = data[0].valueClassification
                    lastUpdated.value = getTextTime(data[0].timestamp)
                }
            }
        }
    }

    private fun getTextTime(timestamp: Long): String {
        return SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).format(Date(timestamp)).toString()
    }

    enum class UiState{
        LOADING, SUCCESS, ERROR
    }

}