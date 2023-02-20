package objects

import org.bukkit.entity.Player
import java.util.UUID

class Village(val player: Player) {
    private val playerMap = HashMap<UUID,VillagePlayer>()

    fun getPlayer(uuid: UUID): VillagePlayer? = playerMap[uuid]
    fun addPlayer(player: Player) { playerMap[player.uniqueId] = VillagePlayer(player) }
    fun removePlayer(uuid: UUID) = playerMap.remove(uuid)

}