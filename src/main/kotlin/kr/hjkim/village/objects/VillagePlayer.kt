package kr.hjkim.village.objects

import org.bukkit.entity.Player

class VillagePlayer(
    val player: Player,
    val villagePlayerRole: VillagePlayerRole = VillagePlayerRole.MEMBER,
    village: Village
) {
    enum class VillagePlayerRole {
        OWNER,
        MEMBER,
        VISITOR
    }

}

