package com.cometchat.pro.androiduikit.ui.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cometchat.pro.androiduikit.db.Run
import com.cometchat.pro.androiduikit.other.SortType
import com.cometchat.pro.androiduikit.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val mainRepository: MainRepository
): ViewModel() {

    private val runsSortedByDate = mainRepository.getALLRunsSortedByDate()
    private val runsSortedByDistance = mainRepository.getALLRunsSortedByDistance()
    private val runsSortedBuCaloriesBurned = mainRepository.getALLRunsSortedByCloriesBurned()
    private val runsSortedByTimeInMillis = mainRepository.getALLRunsSortedByTimeMillis()
    private val runsSortedByAvgSpeed = mainRepository.getALLRunsSortedByAvgSpeed()

    val runs = MediatorLiveData<List<Run>>()

    var sortType = SortType.DATE

    init {
        runs.addSource(runsSortedByDate){ result ->
            if(sortType == SortType.DATE) {
                result?. let{runs.value = it}
            }
        }
        runs.addSource(runsSortedByAvgSpeed){ result ->
            if(sortType == SortType.AVG_SPEED) {
                result?. let{runs.value = it}
            }
        }
        runs.addSource(runsSortedBuCaloriesBurned){ result ->
            if(sortType == SortType.CALORIES_BURED) {
                result?. let{runs.value = it}
            }
        }
        runs.addSource(runsSortedByDistance){ result ->
            if(sortType == SortType.DISTANCE) {
                result?. let{runs.value = it}
            }
        }
        runs.addSource(runsSortedByTimeInMillis){ result ->
            if(sortType == SortType.RUNNING_TIME) {
                result?. let{runs.value = it}
            }
        }
    }

    fun sortRuns(sortType: SortType) = when(sortType){
        SortType.DATE -> runsSortedByDate.value?.let {runs.value = it}
        SortType.RUNNING_TIME -> runsSortedByTimeInMillis.value?.let {runs.value = it}
        SortType.AVG_SPEED -> runsSortedByAvgSpeed.value?.let {runs.value = it}
        SortType.DISTANCE -> runsSortedByDistance.value?.let {runs.value = it}
        SortType.CALORIES_BURED -> runsSortedBuCaloriesBurned.value?.let {runs.value = it}
    }.also {
        this.sortType = sortType
    }

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }
}