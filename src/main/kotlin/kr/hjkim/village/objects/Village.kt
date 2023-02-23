package kr.hjkim.village.objects

import kr.hjkim.village.enums.VillagerRole
import java.util.*

class Village(val name: String) {
    private val villagers = HashSet<UUID>()
    private var maxExpansion: Int = 5
    private var currentExpansion: Int = 0
    private var maxPlayer: Int = 10

    /**
     * 마을에 등록된 유저인지 확인합니다.
     * @param uuid UUID
     * @return Boolean
     */
    fun isVillager(uuid: UUID): Boolean = villagers.contains(uuid)

    /**
     * 유저를 마을에 등록합니다.
     * @param uuid UUID
     * @param role VillagerRole
     */
    fun addVillager(uuid: UUID, role: VillagerRole) {
        villagers.add(uuid)
        Villager(null,uuid,name,role).save()
    }

    /**
     * 유저를 마을에서 삭제합니다.
     * @param uuid UUID
     * @return Boolean
     */
    fun removeVillager(uuid: UUID) = villagers.remove(uuid)

    fun save() {}

}