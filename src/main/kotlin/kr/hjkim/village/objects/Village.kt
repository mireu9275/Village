package kr.hjkim.village.objects

import org.bukkit.entity.Player
import java.util.UUID

class Village(name: String) {
    private val memberMap = HashMap<UUID, VillagePlayer>()
    val name = name
    private var maxExpansion: Int = 5
    private var currentExpansion: Int = 0
    private var maxPlayer: Int = 10

    fun getMember(uuid: UUID): VillagePlayer? = memberMap[uuid]
    fun addMember(player: Player, villagePlayerRole: VillagePlayer.VillagePlayerRole): VillagePlayer {
        memberMap[player.uniqueId] = VillagePlayer(player, villagePlayerRole,this)
    }
    fun removeMember(uuid: UUID) = memberMap.remove(uuid)

}