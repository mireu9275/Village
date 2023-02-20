package managers

import objects.Village
import objects.VillagePlayer
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

object VillageManager {
    private val villageMap = HashMap<String, Village>()

    fun getVillage(name: String) = villageMap[name]

    fun createVillage(player: Player, name: String) {
        if(villageMap.containsKey(name)) { player.sendMessage("${name}마을은 이미 존재하는 마을입니다!"); return }
        val village = Village(name)
        VillagePlayer(player,name).setOwner()
        village.addPlayer(player,name)
        villageMap[name] = village
    }

    fun removeVillage(name: String) {
        if(!villageMap.containsKey(name)) return
        villageMap.remove(name)
    }

    fun addVillage(village: Village, player: Player) {

    }


}