package net.namada.nebbstats.screen.crew

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CrewViewModel(app: Application) : AndroidViewModel(app) {
    private val modelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + modelJob)

    override fun onCleared() {
        super.onCleared()
        modelJob.cancel()
    }
}