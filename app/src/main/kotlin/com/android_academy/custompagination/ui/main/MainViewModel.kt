package com.android_academy.custompagination.ui.main

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android_academy.custompagination.Measurements
import com.android_academy.custompagination.models.Person
import com.android_academy.custompagination.models.toPerson
import com.android_academy.custompagination.repo.StarWarsRepo
import com.android_academy.custompagination.storage.entities.PersonEntity
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val dataRepo: StarWarsRepo, private val moshi: Moshi) : ViewModel() {

    private val dataLivedata: MutableLiveData<List<Person>> = MutableLiveData()
    fun observeData(lifeCycle: LifecycleOwner, observer: (List<Person>) -> Unit) {
        dataLivedata.observe(lifeCycle, observer)
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Default + SupervisorJob()) {
            Log.d(TAG, "loadData start")
            val start = System.currentTimeMillis()
            dataRepo.loadData().collect { list: List<PersonEntity> ->
                Log.d(
                    TAG,
                    "loadData: got data in viewmodel size: ${list.size} took ${System.currentTimeMillis() - start}ms"
                )
                val peoples = list
                    .map { it.toPerson(moshi) }
                    .sortedBy { it.name }
                Log.d(
                    "MainViewModel",
                    "loadData: size: ${list.size}. took ${System.currentTimeMillis() - start}ms"
                )
                Log.d("Measurements", "Number of objects serialized: ${Measurements.getNumbObjectSerialized()}")
                Log.d("Measurements", "Serialization total took: ${Measurements.getTotalSerializationCost()}")
                Log.d("Measurements", "Serialization average took: ${Measurements.getAverageSerializationCost()}")
                Log.d("Measurements", "Number of objects deserialized: ${Measurements.getNumbObjectDeserialized()}")
                Log.d("Measurements", "deserialization total took: ${Measurements.getTotalDeserializationCost()}")
                Log.d("Measurements", "deserialization average took: ${Measurements.getAverageDeserializationCost()}")
                dataLivedata.postValue(peoples)
            }
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}

class MainFragmentViewModelFactory(
    private val starWarsRepo: StarWarsRepo,
    private val moshi: Moshi
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(starWarsRepo, moshi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
