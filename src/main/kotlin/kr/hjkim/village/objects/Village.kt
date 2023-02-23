package kr.hjkim.village.objects

import kr.hjkim.village.enums.VillagerRole
import kr.hjkim.village.exceptions.FileLoadException
import kr.hjkim.village.managers.FileManager
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
    }

    /**
     * 유저를 마을에서 삭제합니다.
     * @param uuid UUID
     * @return Boolean
     */
    fun removeVillager(uuid: UUID) = villagers.remove(uuid)

    fun save() {
        try {
            val village = FileManager.loadVillageFile(name)
            village.apply {
                set("name", name.toString())
                set("villagers", villagers.toList())
            }
            FileManager.saveVillageFile(this.name,village)
        }
        catch (e: FileLoadException) { println("파일 읽기 오류 ( ${e.message} )") }

    }

}