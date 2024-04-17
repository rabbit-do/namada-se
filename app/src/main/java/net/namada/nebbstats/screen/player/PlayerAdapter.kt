package net.namada.nebbstats.screen.player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.namada.nebbstats.R
import net.namada.nebbstats.databinding.PlayerItemBinding
import net.namada.nebbstats.models.Player
import net.namada.nebbstats.models.SimplePlayer

class PlayerAdapter(val callback: PlayerClick): RecyclerView.Adapter<PlayerViewHolder>() {
    var players: List<SimplePlayer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val dataBinding: PlayerItemBinding  = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            PlayerViewHolder.LAYOUT,
            parent, false
        )
        return PlayerViewHolder(dataBinding)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.player = players[position]
            it.playerCallback = callback
        }
    }
}

/**
 * ViewHolder for Player items. All work is done by data binding.
 */
class PlayerViewHolder(val viewDataBinding: PlayerItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {


    companion object {
        @LayoutRes
        val LAYOUT = R.layout.player_item
    }
}

class PlayerClick(val player: (SimplePlayer) -> Unit) {
    /**
     * Called when a Player is clicked
     *
     * @param player the Player that was clicked
     */
    fun onClick(player: SimplePlayer) = player(player)
}