package com.android_academy.custompagination.ui.main

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val dataRepo = DataRepo()

    private val dataLivedata: MutableLiveData<List<String>> = MutableLiveData()
    fun observeData(lifeCycle: LifecycleOwner, observer: (List<String>) -> Unit) {
        dataLivedata.observe(lifeCycle, observer)
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Default + SupervisorJob()) {
            dataRepo.loadData().collect { list ->
                Log.d("MainViewModel", "loadData: $list")
                dataLivedata.postValue(list.map { it.name })
            }
        }
    }
}