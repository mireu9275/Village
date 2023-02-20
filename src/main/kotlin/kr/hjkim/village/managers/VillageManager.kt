package kr.hjkim.village.managers

import kr.hjkim.village.enums.VillagerRole
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
    private val villagerMap = HashMap<UUID, Villager>()

    fun getVillage(name: String) = villageMap[name]

    fun getVillager(uuid: UUID): Villager? = villagerMap[uuid]

    fun getOfflineVillager(uuid: UUID): Villager? {
        val file = File(main.dataFolder,"$uuid.yml")
        val config = YamlConfiguration.loadConfiguration(file)
        return Villager(null,uuid,config.getString("")!!,VillagerRole.MEMBER)
    }

    fun createVillager(player: Player, name: String) {
        if(villageMap.containsKey(name)) { player.sendMessage("${name}마을은 이미 존재하는 마을입니다!"); return }
        val village = Village(name)
        val owner = Villager(player,player.uniqueId,name,VillagerRole.OWNER)

    }

    fun createVillage(player: Player, name: String) {
        if(villageMap.containsKey(name)) { player.sendMessage("${name}마을은 이미 존재하는 마을입니다!"); return }
        val village = Village(name)
        village.addMember(player,VillagerRole.OWNER)
        val villagePlayer: Villager = village.getVillager(player.uniqueId) ?: return
        villageMap[name] = village
        playerMap[player] = villagePlayer
    }

    private fun getPlayer(player: Player) = playerMap[player]

    fun removeVillage(name: String) {
        if(!villageMap.containsKey(name)) return
        villageMap.remove(name)
    }

}