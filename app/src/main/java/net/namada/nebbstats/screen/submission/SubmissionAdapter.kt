package net.namada.nebbstats.screen.submission

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.namada.nebbstats.R
import net.namada.nebbstats.databinding.PlayerItemBinding
import net.namada.nebbstats.databinding.SubmissionItemBinding
import net.namada.nebbstats.models.SimplePlayer
import net.namada.nebbstats.models.Submission
import net.namada.nebbstats.screen.player.PlayerViewHolder

class SubmissionAdapter(): RecyclerView.Adapter<SubmissionViewHolder>() {

    var submissions: List<Submission> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmissionViewHolder {
        val dataBinding: SubmissionItemBinding  = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            SubmissionViewHolder.LAYOUT,
            parent, false
        )
        return SubmissionViewHolder(dataBinding)
    }

    override fun getItemCount()= submissions.size

    override fun onBindViewHolder(holder: SubmissionViewHolder, position: Int) {
        val item = submissions[position]
        holder.viewDataBinding.also {
            it.submission = submissions[position]
        }
        holder.viewDataBinding.eligible.text = buildString {
            append("Eligible for ROIDs:")
            append(item.eligibleForRoids)
        }
        holder.viewDataBinding.comment.text = buildString {
            append("Comment: ")
            append(item.comment)
        }
    }
}


class SubmissionViewHolder(val viewDataBinding: SubmissionItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {


    companion object {
        @LayoutRes
        val LAYOUT = R.layout.submission_item
    }
}