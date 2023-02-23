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

    /**
     * 마을 등록 유무를 확인합니다.
     * @param uuid UUID
     * @return Boolean
     */
    fun containsVillager(uuid: UUID): Boolean = villagerMap.containsKey(uuid)

    /**
     * 마을에 등록된 유저를 가져옵니다.
     * @param uuid UUID
     * @return Villager?
     */
    fun getVillager(uuid: UUID): Villager? = villagerMap[uuid]

    /**
     * 마을에 등록된 오프라인 유저를 가져옵니다.
     * @param uuid UUID
     * @return Villager?
     */
    fun getOfflineVillager(uuid: UUID): Villager? {
        val file = File(main.dataFolder,"$uuid.yml")
        if(!file.exists()) return null
        val config = YamlConfiguration.loadConfiguration(file)
        return Villager(null,uuid,config.getString("")!!,VillagerRole.MEMBER)
    }

    /**
     * 유저를 등록합니다.
     * @param player Player
     * @param name String
     * @param role VillagerRole
     */
    fun createVillager(player: Player, name: String, role: VillagerRole) {
        val uuid = player.uniqueId
        if (getVillager(uuid) != null) return
        val villager = Villager(player, uuid, name, role)
        FileManager.createVillagerFile(uuid)
        villager.save()
        villagerMap[uuid] = villager
    }

    /**
     * 오프라인 유저를 등록합니다.
     * @param uuid UUID
     * @param name String
     * @param role VillagerRole
     */
    fun createVillager(uuid: UUID, name: String, role: VillagerRole) {
        if(getVillager(uuid) != null) return
        val villager = Villager(null, uuid, name, role)
        villager.save()
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