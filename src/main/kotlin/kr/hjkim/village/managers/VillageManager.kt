package kr.hjkim.village.managers

import kr.hjkim.village.objects.Village
import kr.hjkim.village.objects.VillagePlayer
import org.bukkit.entity.Player
import kotlin.collections.HashMap

object VillageManager {
    private val villageMap = HashMap<String, Village>()

    fun getVillage(name: String) = villageMap[name]

    fun createVillage(player: Player, name: String) {
        if(villageMap.containsKey(name)) { player.sendMessage("${name}마을은 이미 존재하는 마을입니다!"); return }
        val village = Village(name)
        village.addMember(player,VillagePlayer.VillagePlayerRole.OWNER)
        villageMap[name] = village
    }

    fun addPlayer(player: Player, name: String) {
        if (!villageMap.containsKey(name)) { player.sendMessage("${name}마을은 존재하지 않는 마을입니다!"); return }
        val village = Village(name)
        VillagePlayer.getVillage() {}
    }


    fun removeVillage(name: String) {
        if(!villageMap.containsKey(name)) return
        villageMap.remove(name)
    }
}