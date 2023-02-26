package kr.hjkim.village.managers

import kr.hjkim.village.enums.VillagerRole
import kr.hjkim.village.exceptions.VillageCreateException
import kr.hjkim.village.main
import kr.hjkim.village.objects.Village
import kr.hjkim.village.objects.Villager
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.util.UUID
import kotlin.collections.HashMap

object VillageManager {

    private val villageMap = HashMap<String, Village>()

    /**
     * 같은 이름의 마을이 있는지 확인합니다.
     * @param name String
     * @return Boolean
     */
    private fun containsVillage(name: String): Boolean = villageMap.containsKey(name)

    /**
     * 현재 등록 돼있는 마을 중 선택한 마을을 가져옵니다.
     * @param name String
     * @return Village?
     */
    fun getVillage(name: String) = villageMap[name]

    /**
     * 새로운 마을을 생성합니다. (OP전용)
     * @param uuid UUID
     * @param name String
     * @return Boolean
     */
    fun createVillage(uuid: UUID, name: String): Boolean {
        if(villageMap.containsKey(name)) return false
        if(VillagerManager.getVillager(uuid) != null || VillagerManager.getOfflineVillager(uuid) != null) return false
        VillagerManager.createVillager(uuid,name,VillagerRole.OWNER)
        Village(name).save()
        return true
    }

    /**
     * 새로운 마을을 생성합니다.
     * @param player Player
     * @param name String
     * @throws VillageCreateException 마을이 이미 생성 되어있는 경우, 혹은 [uuid] 의 유저가 마을을 보유중인 경우에 발생합니다.
     */
    fun createVillage(player: Player, name: String) {
        if(containsVillage(name)) throw VillageCreateException("\"$name\" 마을은 이미 존재합니다.")
        if(VillagerManager.containsVillager(player.uniqueId)) throw VillageCreateException("이미 마을을 보유중입니다.")
        villageMap[name] = Village(name,player)
    }

    /**
     * 마을을 삭제합니다.
     * @param name String
     */
    fun removeVillage(name: String) {
        if(!villageMap.containsKey(name)) return
        villageMap.remove(name)
    }
}