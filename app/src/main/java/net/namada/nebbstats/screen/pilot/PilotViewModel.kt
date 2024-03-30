package net.namada.nebbstats.screen.pilot

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.namada.nebbstats.api.NetworkProvider
import net.namada.nebbstats.api.toListPlayerModel
import net.namada.nebbstats.repository.AppRepo

class PilotViewModel(app: Application) : AndroidViewModel(app) {
    private val modelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + modelJob)
    private val appRepo = AppRepo


    override fun onCleared() {
        super.onCleared()
        modelJob.cancel()
    }


}