package net.namada.nebbstats.screen.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.namada.nebbstats.api.NetworkProvider
import net.namada.nebbstats.api.toListPlayerModel
import net.namada.nebbstats.api.toListSubmissionModel
import net.namada.nebbstats.models.Player
import net.namada.nebbstats.models.Submission
import net.namada.nebbstats.repository.AppRepo

class StatsViewModel(app: Application) : AndroidViewModel(app) {
    private val modelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + modelJob)
    private lateinit var allSubmission: List<Submission>
    private var allPilots: MutableList<Player> = emptyList<Player>().toMutableList() //hold all pilots from API
    private val _pilots = MutableLiveData<List<Player>>()
    val pilots : LiveData<List<Player>> = _pilots
    private var allCrews: MutableList<Player> = emptyList<Player>().toMutableList() //hold all pilots from API
    private val _crews = MutableLiveData<List<Player>>()
    val crews : LiveData<List<Player>> = _crews

    override fun onCleared() {
        super.onCleared()
        modelJob.cancel()
    }

    fun getAllSubmission() {
        coroutineScope.launch {
            allSubmission = NetworkProvider.appService
                .getAllSubmission(NetworkProvider.YEK)
                .toListSubmissionModel()
            println("allSubmission $allSubmission")
        }
    }

    fun getPilotList(page: Int) {
        coroutineScope.launch {
            try {
                val pilotList =
                    NetworkProvider.nebbService.getPilotList(page).players.toListPlayerModel()
                if (page == 0){
                    allPilots.clear() //clear all previous if page = 0
                }
                allPilots.addAll(pilotList)
                _pilots.postValue(allPilots)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

    }

    fun getCrewList(page: Int) {
        coroutineScope.launch {
            try {
                val crewList =
                    NetworkProvider.nebbService.getCrewList(page).players.toListPlayerModel()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}