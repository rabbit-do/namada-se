package net.namada.nebbstats.screen.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.namada.nebbstats.R
import net.namada.nebbstats.databinding.ClassifiedSubmissionItemBinding
import net.namada.nebbstats.models.ClassifiedSubmission


class ClassifiedSubmissionAdapter (val callback: ClassifiedSubmissionClick,
                                    val filterType: String): RecyclerView.Adapter<ClassifiedViewHolder>() {
    var submissionList: List<ClassifiedSubmission> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifiedViewHolder {
        val withDataBinding: ClassifiedSubmissionItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ClassifiedViewHolder.LAYOUT,
            parent,
            false
        )
        return ClassifiedViewHolder(withDataBinding)
    }

    override fun getItemCount() = submissionList.size

    override fun onBindViewHolder(holder: ClassifiedViewHolder, position: Int) {
        val item = submissionList[position]
        holder.viewDataBinding.also {
            it.classifiedSubmission = submissionList[position]
            it.classifiedSubmissionCallback = callback
        }

        holder.viewDataBinding.totalCount.text = getTotalCountByFilter(item)
        holder.viewDataBinding.totalPilotCount.text = item.categoryPilotStat.totalInt.toString()
        holder.viewDataBinding.totalCrewCount.text = item.categoryCrewStat.totalInt.toString()
        //
        holder.viewDataBinding.approvedCount.text = getApprovedCountByFilter(item)
        holder.viewDataBinding.approvedPilotCount.text = item.categoryPilotStat.yesCount.toString()
        holder.viewDataBinding.approvedCrewCount.text = item.categoryCrewStat.yesCount.toString()
        //
        holder.viewDataBinding.rejectedCount.text = getRejectedCountByFilter(item)
        holder.viewDataBinding.rejectedPilotCount.text = item.categoryPilotStat.noCount.toString()
        holder.viewDataBinding.rejectedCrewCount.text = item.categoryCrewStat.noCount.toString()
        //
        holder.viewDataBinding.spamCount.text = getSpamCountByFilter(item)
        holder.viewDataBinding.spamPilotCount.text = item.categoryPilotStat.spamCount.toString()
        holder.viewDataBinding.spamCrewCount.text = item.categoryCrewStat.spamCount.toString()
        //
        holder.viewDataBinding.pendingCount.text = getPendingCountByFilter(item)
        holder.viewDataBinding.pendingPilotCount.text = item.categoryPilotStat.pendingCount.toString()
        holder.viewDataBinding.pendingCrewCount.text = item.categoryCrewStat.pendingCount.toString()
    }

    private fun getPendingCountByFilter(item: ClassifiedSubmission) : String{
        return when(filterType){
            "Pilot"  -> item.categoryPilotStat.pendingCount.toString()
            "Crew" -> item.categoryCrewStat.pendingCount.toString()
            else -> (item.categoryPilotStat.pendingCount + item.categoryCrewStat.pendingCount).toString()
        }
    }

    private fun getSpamCountByFilter(item: ClassifiedSubmission) : String{
        return when(filterType){
            "Pilot"  -> item.categoryPilotStat.spamCount.toString()
            "Crew" -> item.categoryCrewStat.spamCount.toString()
            else -> (item.categoryPilotStat.spamCount + item.categoryCrewStat.spamCount).toString()
        }
    }

    private fun getRejectedCountByFilter(item: ClassifiedSubmission)  : String{
        return when(filterType){
            "Pilot"  -> item.categoryPilotStat.noCount.toString()
            "Crew" -> item.categoryCrewStat.noCount.toString()
            else -> (item.categoryPilotStat.noCount + item.categoryCrewStat.noCount).toString()
        }
    }

    private fun getApprovedCountByFilter(item: ClassifiedSubmission)  : String{
        return when(filterType){
            "Pilot"  -> item.categoryPilotStat.yesCount.toString()
            "Crew" -> item.categoryCrewStat.yesCount.toString()
            else -> (item.categoryPilotStat.yesCount + item.categoryCrewStat.yesCount).toString()
        }
    }

    private fun getTotalCountByFilter(item: ClassifiedSubmission)  : String{
        return when(filterType){
            "Pilot"  -> item.categoryPilotStat.totalInt.toString()
            "Crew" -> item.categoryCrewStat.totalInt.toString()
            else -> (item.categoryPilotStat.totalInt + item.categoryCrewStat.totalInt).toString()
        }
    }
}

class ClassifiedSubmissionClick(val submissionClickFun: (ClassifiedSubmission) -> Unit){
    fun onClick(classifiedSubmission: ClassifiedSubmission) = submissionClickFun(classifiedSubmission)
}

class ClassifiedViewHolder(val viewDataBinding: ClassifiedSubmissionItemBinding):
        RecyclerView.ViewHolder(viewDataBinding.root){

            companion object {
                @LayoutRes
                val LAYOUT = R.layout.classified_submission_item
            }

        }