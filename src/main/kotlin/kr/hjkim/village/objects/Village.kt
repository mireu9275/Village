package kr.hjkim.village.objects

import kr.hjkim.village.enums.VillagerRole
import org.bukkit.entity.Player
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

    fun test(player: Player) {
        val villager = Villager(player,player.uniqueId,name,VillagerRole.MEMBER)
        addVillager(villager,VillagerRole.MEMBER)
    }

    fun addMember(player: Player, villagerRole: VillagerRole) {
        villagerMap[player.uniqueId] = VillagePlayer(player, villagerRole,this)
    }
    fun removeMember(uuid: UUID) = villagerMap.remove(uuid)

}