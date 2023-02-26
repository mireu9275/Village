package kr.hjkim.village.objects

import kr.hjkim.village.enums.VillagerRole
import kr.hjkim.village.managers.FileManager
import kr.hjkim.village.managers.VillageManager
import org.bukkit.entity.Player
import java.io.IOException
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

    fun getVillage(): Village = VillageManager.getVillage(villageName)!!

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
        try { FileManager.saveVillagerFile(this) }
        catch (e: Exception) { println("파일 읽기 오류 ( ${e.message} )") }
    }

}