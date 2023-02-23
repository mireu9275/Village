package kr.hjkim.village.objects

import kr.hjkim.village.enums.VillagerRole
import kr.hjkim.village.exceptions.FileLoadException
import kr.hjkim.village.managers.FileManager
import org.bukkit.entity.Player
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
        save()
        this.player = null
    }

    /**
     * $uuid.yml 파일에 현재 가지고 있는 정보를 저장합니다.
     */
    fun save() {
        try {
            val config = FileManager.loadVillagerFile(uuid).apply {
                set("uuid", uuid.toString())
                set("village", villageName)
                set("role", villagerRole.toString())
            }
            FileManager.saveVillagerFile(uuid, config)
        }
        catch (e: FileLoadException) { println("파일 읽기 오류 ( ${e.message} )") }
    }

}