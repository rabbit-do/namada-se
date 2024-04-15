package net.namada.nebbstats.screen.pilot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import net.namada.nebbstats.R
import net.namada.nebbstats.databinding.PlayerStatItemBinding
import net.namada.nebbstats.models.PlayerStat
import net.namada.nebbstats.screen.player.PlayerViewHolder

class PlayerStatAdapter(val callback: PlayerStatClick): RecyclerView.Adapter<PlayerStatViewHolder>() {
    var playerStats: List<PlayerStat> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerStatViewHolder {
        val dataBinding: PlayerStatItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            PlayerStatViewHolder.LAYOUT,
            parent, false
        )
        return PlayerStatViewHolder(dataBinding)
    }

    override fun getItemCount(): Int = playerStats.size

    override fun onBindViewHolder(holder: PlayerStatViewHolder, position: Int) {
        val item = playerStats[position]
        if(item.sClassCategoryCount == 1){
            holder.viewDataBinding.title.text =
                "Number of players completed S Class in ${item.sClassCategoryCount} category"
        }else {
            holder.viewDataBinding.title.text =
                "Number of players completed S-class in ${item.sClassCategoryCount} categories"
        }
        holder.viewDataBinding.count.text = item.playerCompletedCount.toString()
        holder.viewDataBinding.viewButton.setOnClickListener { callback.actionClick(item) }
    }


}

class PlayerStatViewHolder(val viewDataBinding: PlayerStatItemBinding): RecyclerView.ViewHolder(viewDataBinding.root){
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.player_stat_item
    }
}

class PlayerStatClick(val actionClick: (PlayerStat) -> Unit ){
    fun onClick(playerStat: PlayerStat) = actionClick(playerStat)
}