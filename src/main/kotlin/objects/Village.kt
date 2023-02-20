package objects

import org.bukkit.entity.Player

class Village(val player: Player) {
    private val playerMap = HashSet<VillagePlayer>()
}