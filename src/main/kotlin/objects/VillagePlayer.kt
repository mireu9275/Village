package objects

import org.bukkit.entity.Player

class VillagePlayer(val player: Player, private val villageName: String) {

    var isOwner: Boolean = false
        private set
    var hasVillageName: String = villageName
        private set

    fun setOwner() { isOwner = true }

}