package kr.hjkim.village.objects

import org.bukkit.entity.Player
import java.util.UUID

class Village(private val name: String) {
    private val playerMap = HashMap<UUID, VillagePlayer>()

    private var maxExpansion: Int = 5
    private var currentExpansion: Int = 0
    private var maxPlayer: Int = 10

    fun getPlayer(uuid: UUID): VillagePlayer? = playerMap[uuid]
    fun addPlayer(player: Player, villageName: String) { playerMap[player.uniqueId] = VillagePlayer(player,name) }
    fun removePlayer(uuid: UUID) = playerMap.remove(uuid)

}