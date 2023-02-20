package objects

import org.bukkit.entity.Player
import java.util.UUID

class Village(val player: Player) {
    private val playerMap = HashSet<VillagePlayer>()
}