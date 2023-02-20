package kr.hjkim.village.objects

import org.bukkit.entity.Player

class VillagePlayer(
    player: Player,
    val villagePlayerRole: VillagePlayerRole = VillagePlayerRole.MEMBER,
    village: Village
) {
    val player = player
    val village = village

    enum class VillagePlayerRole {
        OWNER,
        MEMBER,
        VISITOR
    }
}

