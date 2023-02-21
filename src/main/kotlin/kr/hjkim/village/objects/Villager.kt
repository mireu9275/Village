package kr.hjkim.village.objects

import kr.hjkim.village.enums.VillagerRole
import kr.hjkim.village.main
import kr.hjkim.village.managers.VillageManager
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.util.UUID

class Villager(
    player: Player?,
    val uuid: UUID,
    val villageName: String,
    val villagerRole: VillagerRole,
) {

    val isOnline: Boolean
        get() = player != null

    var player: Player? = player
        private set

    fun join(player: Player) {
        this.player = player
    }

    fun quit() {
        this.player = null
    }

    fun save() {
        val file: File = File(main.dataFolder,"$uuid.yml")
        val config = YamlConfiguration.loadConfiguration(file)
        config.save(file)
    }

}