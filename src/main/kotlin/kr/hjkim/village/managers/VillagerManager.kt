package kr.hjkim.village.managers

import kr.hjkim.village.enums.VillagerRole
import kr.hjkim.village.main
import kr.hjkim.village.objects.Villager
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.util.UUID

object VillagerManager {

    private val villagerMap = HashMap<UUID,Villager>()

    fun getVillager(uuid: UUID): Villager? = villagerMap[uuid]
    fun getOfflineVillager(uuid: UUID): Villager? {
        val file: File = File(main.dataFolder,"$uuid.yml")
        val config = YamlConfiguration.loadConfiguration(file)
        return Villager(null,uuid,config.getString("")!!,VillagerRole.MEMBER)
    }

    fun createVillager(player: Player, name: String, villagerRole: VillagerRole) {
        val uuid = player.uniqueId
        if (getVillager(uuid) != null) return
        villagerMap[uuid] = Villager(player, uuid, name, villagerRole)
    }

    fun load(player: Player) {
        val uuid = player.uniqueId
        val villageName = "test"
        val village = VillageManager.getVillage(villageName)
        if(village == null) { player.sendMessage("${villageName}마을은 존재하지 않는 마을입니다!"); return }
        if(!village.isVillager(uuid)) { player.sendMessage("${player}님은 이미 다른 마을에 속해있습니다."); return }
        villagerMap[uuid] = Villager(player,uuid,"",VillagerRole.MEMBER)
    }

}