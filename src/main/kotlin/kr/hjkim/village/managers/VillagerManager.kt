package kr.hjkim.village.managers

import kr.hjkim.village.enums.VillagerRole
import kr.hjkim.village.objects.Villager
import org.bukkit.entity.Player
import java.util.UUID

object VillagerManager {

    private val villagerMap = HashMap<UUID,Villager>()

    fun getVillager(uuid: UUID): Villager? = villagerMap[uuid]

    fun load(player: Player) {
        val uuid = player.uniqueId
        val villageName = "test"
        val village = VillageManager.getVillage(villageName)
        if(village == null) { player.sendMessage("${villageName}마을은 존재하지 않는 마을입니다!"); return }
        if(!village.isVillager(uuid)) { player.sendMessage("${player}님은 이미 다른 마을에 속해있습니다."); return }
        villagerMap[uuid] = Villager(player,uuid,"",VillagerRole.MEMBER)
    }

}