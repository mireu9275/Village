package kr.hjkim.village.managers

import kr.hjkim.village.objects.Village
import kr.hjkim.village.objects.VillagePlayer
import org.bukkit.entity.Player
import kotlin.collections.HashMap

object VillageManager {
    private val villageMap = HashMap<String, Village>()
    private val playerMap = HashMap<Player, VillagePlayer>()

    fun getVillage(name: String) = villageMap[name]

    fun createVillage(player: Player, name: String) {
        if(villageMap.containsKey(name)) { player.sendMessage("${name}마을은 이미 존재하는 마을입니다!"); return }
        val village = Village(name)
        village.addMember(player,VillagePlayer.VillagePlayerRole.OWNER)
        val villagePlayer: VillagePlayer = village.getMember(player.uniqueId) ?: return
        villageMap[name] = village
        playerMap[player] = villagePlayer
    }

    fun addPlayer(player: Player, name: String) {
        val village = villageMap[name]
        if (village == null) { player.sendMessage("${name}마을은 존재하지 않는 마을입니다!"); return }
        val villagePlayerCheck = getPlayer(player)
        if (villagePlayerCheck != null) { player.sendMessage("${player}님은 이미 다른 마을에 속해있습니다."); return }
        val villagePlayer: VillagePlayer = village.getMember(player.uniqueId) ?: return
        playerMap[player] = villagePlayer
    }

    fun getPlayer(player: Player) = playerMap[player]

    fun removeVillage(name: String) {
        if(!villageMap.containsKey(name)) return
        villageMap.remove(name)
    }
}