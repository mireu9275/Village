package kr.hjkim.village.objects

import kr.hjkim.village.enums.VillagerRole
import kr.hjkim.village.main
import kr.hjkim.village.managers.AreaManager
import kr.hjkim.village.managers.FileManager
import kr.hjkim.village.managers.VillagerManager
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class Village(val name: String, val chest: Inventory = main.server.createInventory(null,54)) {

    constructor(name: String, owner: UUID, inventory: Inventory = main.server.createInventory(null,54)): this(name, inventory) {
        Villager(null, owner, name,VillagerRole.OWNER).save()
        save()
    }

    constructor(name: String, owner: Player, inventory: Inventory = main.server.createInventory(null,54)): this(name, inventory) {
        val uuid = owner.uniqueId
        val villager = Villager(owner, uuid, name, VillagerRole.OWNER)
        VillagerManager.registerVillager(uuid, villager)
        villagers.add(uuid)
        villager.save()
        save()
    }

    private val villagers = HashSet<UUID>()
    private val areas = HashSet<Long>()
    var maxExpansion: Int = 1
        private set
    var maxPlayer: Int = 10
        private set

    /**
     * 마을에 등록된 유저인지 확인합니다.
     * @param uuid UUID
     * @return Boolean
     */
    fun isVillager(uuid: UUID): Boolean = villagers.contains(uuid)

    fun getAllVillagers(): HashSet<UUID> = villagers

    fun getAllAreas(): HashSet<Long> = areas

    /**
     * 유저를 마을에 등록합니다.
     * @param uuid UUID
     */
    fun addVillager(uuid: UUID) {
        val villager = Villager(null, uuid, name, VillagerRole.MEMBER)
        VillagerManager.registerVillager(uuid, villager)
        villagers.add(uuid)
    }

    /**
     * 유저를 마을에 등록합니다.
     * @param player Player
     */
    fun addVillager(player: Player) {
        val uuid = player.uniqueId
        val villager = Villager(player, uuid, name, VillagerRole.MEMBER)
        VillagerManager.registerVillager(uuid, villager)
        villagers.add(uuid)
    }

    /**
     * 유저를 마을에서 삭제합니다.
     * @param uuid UUID
     * @return Boolean
     */
    fun removeVillager(uuid: UUID) = villagers.remove(uuid)

    /**
     * 새로운 마을 범위를 추가합니다.
     * @param chunkKey Long
     * @return Boolean
     */
    fun addArea(chunkKey: Long): Boolean {
        if(areas.size >= maxExpansion) return false
        AreaManager.registerArea(chunkKey,name)
        areas.add(chunkKey)
        return true
    }

    fun setVillagers(villagers: List<UUID>) {
        this.villagers.clear()
        this.villagers.addAll(villagers)
    }

    fun setAreas(areas: List<Long>) {
        this.areas.clear()
        this.areas.addAll(areas)
    }

    fun setChest(chest: List<ItemStack>?) {
        this.chest.clear()
        chest?.forEachIndexed { index, item ->
            this.chest.setItem(index, item)
        }
    }

    fun setMaxExpansion(size: Int) { maxExpansion = size }

    fun load() { FileManager.loadVillageFile(name) }

    /**
     * 마을의 정보를 Village 폴더 내에 파일로 저장합니다.
     */
    fun save() {
        try { FileManager.saveVillageFile(this) }
        catch (e: Exception) { println("파일 읽기 오류 ( ${e.message} )") }
    }
}