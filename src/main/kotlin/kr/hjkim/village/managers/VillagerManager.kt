package kr.hjkim.village.managers

import kr.hjkim.village.enums.VillagerRole
import kr.hjkim.village.main
import kr.hjkim.village.objects.Village
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
   fun getOfflineVillager(uuid: UUID): Villager {
        val config = FileManager.loadOfflineVillagerFile(uuid)
        return Villager(null, uuid, "", VillagerRole.MEMBER)
   }

    /**
     * 유저를 생성합니다.
     * @param player Player
     * @param name String
     * @param role VillagerRole
     */
    fun createVillager(player: Player, name: String, role: VillagerRole): Villager? {
        val uuid = player.uniqueId
        val villager = Villager(player, uuid, name, role)
        val village = VillageManager.getVillage(name) ?: return null
        village.addVillager(uuid)
        villager.save()
        villagerMap[uuid] = villager
        return villager
    }

    /**
     * 오프라인 유저를 생성합니다.
     * @param uuid UUID
     * @param name String
     * @param role VillagerRole
     */
    fun createVillager(uuid: UUID, name: String, role: VillagerRole): Villager? {
        if(getVillager(uuid) != null) return null
        val villager = Villager(null, uuid, name, role)
        val village = VillageManager.getVillage(name) ?: return null
        village.addVillager(uuid)
        villager.save()
        villagerMap[uuid] = villager
        return villager
    }

    /**
     * 유저를 villagerMap에 등록합니다.
     * @param uuid UUID
     * @param villager Villager
     */
    fun registerVillager(uuid: UUID, villager: Villager) {
        villagerMap[uuid] = villager
    }
}