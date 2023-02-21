package kr.hjkim.village.objects

import kr.hjkim.village.enums.VillagerRole
import java.util.*

class Village(val name: String) {
    private val villagers = HashSet<UUID>()
    private var maxExpansion: Int = 5
    private var currentExpansion: Int = 0
    private var maxPlayer: Int = 10

    fun isVillager(uuid: UUID): Boolean = villagers.contains(uuid)

    fun addVillager(uuid: UUID, role: VillagerRole) {
        villagers.add(uuid)
        Villager(null,uuid,name,role).save()
    }
    fun removeVillager(uuid: UUID) = villagers.remove(uuid)

}