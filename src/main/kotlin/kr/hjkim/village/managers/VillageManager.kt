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

    fun getVillage(name: String) = villageMap[name]

    fun createVillage(player: Player, name: String) {
        if(villageMap.containsKey(name)) { player.sendMessage("${name}마을은 이미 존재하는 마을입니다!"); return }
        val village = Village(name)
        VillagerManager.createVillager(player, name, VillagerRole.OWNER)
    }

    fun removeVillage(name: String) {
        if(!villageMap.containsKey(name)) return
        villageMap.remove(name)
    }

}