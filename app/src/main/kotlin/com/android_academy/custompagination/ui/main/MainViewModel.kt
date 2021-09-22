package com.android_academy.custompagination.ui.main

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android_academy.custompagination.models.toPeople
import com.android_academy.custompagination.repo.StarWarsRepo
import com.android_academy.custompagination.storage.entities.PeopleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val dataRepo: StarWarsRepo) : ViewModel() {

    private val dataLivedata: MutableLiveData<List<String>> = MutableLiveData()
    fun observeData(lifeCycle: LifecycleOwner, observer: (List<String>) -> Unit) {
        dataLivedata.observe(lifeCycle, observer)
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Default + SupervisorJob()) {
            Log.d(TAG, "loadData start")
            val start = System.currentTimeMillis()
            dataRepo.loadData().collect { list: List<PeopleEntity> ->
                Log.d(TAG, "loadData: got data in viewmodel size: ${list.size} took ${System.currentTimeMillis() - start}ms")
                val peoples = list.map { it.toPeople() }.sortedBy { it.name }
                Log.d(
                    "MainViewModel",
                    "loadData: size: ${list.size}. took ${System.currentTimeMillis() - start}ms"
                )
                dataLivedata.postValue(peoples.map { people -> people.name })
            }
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}

class MainFragmentViewModelFactory(private val starWarsRepo: StarWarsRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(starWarsRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
