package managers

import objects.Village
import objects.VillagePlayer
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

object VillageManager {
    private val villageMap = HashMap<String, Village>()

    fun getVillage(name: String) = villageMap[name]

    /**
     * 마을 생성 ( 동일 마을명 없을때만)
     */
    fun createVillage(player: Player, name: String) {
        if(villageMap.containsKey(name)) { player.sendMessage("${name}마을은 이미 존재하는 마을입니다!"); return }
        val village = Village(name)
        VillagePlayer(player,name).setOwner()
        village.addPlayer(player,name)
        villageMap[name] = village
    }

    /**
     *  마을 삭제 ( 없는건 삭제 불가 )
     */
    fun removeVillage(name: String) {
        if(!villageMap.containsKey(name)) return
        villageMap.remove(name)
    }



}