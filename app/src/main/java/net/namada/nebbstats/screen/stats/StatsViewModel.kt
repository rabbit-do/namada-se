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
import net.namada.nebbstats.models.CategoryStat
import net.namada.nebbstats.models.ClassifiedSubmission
import net.namada.nebbstats.models.Player
import net.namada.nebbstats.models.Submission

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

    lateinit var allPilotSubmissions: List<Submission>
    lateinit var allCrewSubmissions: List<Submission>
    var subCategoryNameSet: HashSet<String> = HashSet()
    var classifiedPilotSubmissionsMap: MutableMap<String, List<Submission>> = emptyMap<String, List<Submission>>().toMutableMap()
    var classifiedCrewSubmissionsMap: MutableMap<String, List<Submission>> = emptyMap<String, List<Submission>>().toMutableMap()
    var classifiedGeneralSubmissionsList: MutableList<ClassifiedSubmission> = emptyList<ClassifiedSubmission>().toMutableList()
    private val _cgsl = MutableLiveData<List<ClassifiedSubmission>>()
    val cgsl: LiveData<List<ClassifiedSubmission>> = _cgsl

    override fun onCleared() {
        super.onCleared()
        modelJob.cancel()
    }

    fun getAllSubmission() {
        coroutineScope.launch {
            allSubmission = NetworkProvider.appService
                .getAllSubmission(NetworkProvider.YEK)
                .toListSubmissionModel()
            classifyData(allSubmission)
            println("allSubmission $allSubmission")
        }
    }

       /**
     * classify submission data into categories
     */
    private fun classifyData(allSubmission: List<Submission>) {
        classifiedGeneralSubmissionsList.clear()
        allPilotSubmissions = allSubmission.filter { it.type == "Pilot" }
        allCrewSubmissions = allSubmission.filter { it.type == "Crew" }
        println("allSubmission: ${allSubmission.size} , pilot sub: ${allPilotSubmissions.size},  crew sub: ${allCrewSubmissions.size}")
        allSubmission.forEach{
            subCategoryNameSet.add(it.subClass ?: "")
        }
        println("subCategoryNameSet $subCategoryNameSet")
        subCategoryNameSet.toMutableList().forEach { subCategoryName ->
            run {
                val filterPilotSubmissions = allPilotSubmissions.filter { it.subClass == subCategoryName }
                classifiedPilotSubmissionsMap[subCategoryName] =
                    filterPilotSubmissions
                val filterCrewSubmissions = allCrewSubmissions.filter { it.subClass == subCategoryName }
                classifiedCrewSubmissionsMap[subCategoryName] =
                    filterCrewSubmissions
                classifiedGeneralSubmissionsList.add(
                    ClassifiedSubmission(
                        subCategoryName = subCategoryName,
                        listPilotSubmissions = filterPilotSubmissions,
                        categoryPilotStat = calculateCategoryStat(filterPilotSubmissions),
                        listCrewSubmissions =  filterCrewSubmissions,
                        categoryCrewStat = calculateCategoryStat(filterCrewSubmissions)
                    )
                )
            }
        }
//        classifiedPilotSubmissionsMap.forEach { entry: Map.Entry<String, List<Submission>> ->
//            classifiedGeneralSubmissionsList.add(ClassifiedSubmission(entry.key, entry.value, calculateCategoryStat(entry.value)))
//        }
        println("classifiedGeneralSubmissionsList $classifiedGeneralSubmissionsList")
           _cgsl.postValue(classifiedGeneralSubmissionsList)
           countApprovedSubmissionByEachAddress()

    }

    private fun countApprovedSubmissionByEachAddress(){
//        Map<String,Int> pilotAddressApprovedCountMap: count in DistinceApprovedAddress
        val pilotAddressApprovedCountMap: HashMap<String, Int> = HashMap()
        val crewAddressApprovedCountMap: HashMap<String, Int> = HashMap()

        classifiedGeneralSubmissionsList.forEach {
            it.categoryPilotStat.distincApprovedAddress.forEach { address ->
                    pilotAddressApprovedCountMap[address] =
                        (pilotAddressApprovedCountMap[address] ?: 0) + 1
            }
            it.categoryCrewStat.distincApprovedAddress.forEach { address ->
                crewAddressApprovedCountMap[address] =
                    (crewAddressApprovedCountMap[address] ?: 0) + 1
            }
        }
        val pilotApprovedCountAddressMap: HashMap<Int, MutableList<String>> = HashMap()
        val crewApprovedCountAddressMap: HashMap<Int, MutableList<String>> = HashMap()
        pilotAddressApprovedCountMap.forEach { it ->
            if(pilotApprovedCountAddressMap[it.value] == null){
                val list: MutableList<String> = emptyList<String>().toMutableList()
                list.add(it.key)
                pilotApprovedCountAddressMap[it.value] = list
            }else {
                pilotApprovedCountAddressMap[it.value]!!.add(it.key)
            }
        }
        crewAddressApprovedCountMap.forEach { it ->
            if(crewApprovedCountAddressMap[it.value] == null){
                val list: MutableList<String> = emptyList<String>().toMutableList()
                list.add(it.key)
                crewApprovedCountAddressMap[it.value] = list
            }else {
                crewApprovedCountAddressMap[it.value]!!.add(it.key)
            }
        }
        println("pilotAddressApprovedCountMap $pilotAddressApprovedCountMap")
        println("crewApprovedCountAddressMap $crewApprovedCountAddressMap")
    }


    private fun calculateCategoryStat(submissions: List<Submission>): CategoryStat {
        val approvedSubmissions = submissions.filter { it.eligibleForRoids == "Yes" }
        val yesCount   = approvedSubmissions.size
        val noCount = submissions.count{it.eligibleForRoids == "No"}
        val spamCount = submissions.count { it.eligibleForRoids == "Spam" }
        val pendingCount = submissions.count { it.eligibleForRoids == "" }
        val distincApprovedAddressSet: HashSet<String> = HashSet()
        approvedSubmissions.forEach { distincApprovedAddressSet.add(it.fromAddress ?: "") }
        return CategoryStat(yesCount, noCount, spamCount, pendingCount, submissions.size, distincApprovedAddressSet)
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