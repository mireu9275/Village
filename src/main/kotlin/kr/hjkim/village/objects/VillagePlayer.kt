package kr.hjkim.village.objects

import org.bukkit.entity.Player

class VillagePlayer(val player: Player, village: Village) {

    var isOwner: Boolean = false
        private set

    fun setOwner() { isOwner = true }

}