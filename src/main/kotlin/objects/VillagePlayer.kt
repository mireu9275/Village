package objects

import org.bukkit.entity.Player

class VillagePlayer(val player: Player, val villageName: String) {
    var isOwner = false
        private set

    fun setOwner() { isOwner = true }

}