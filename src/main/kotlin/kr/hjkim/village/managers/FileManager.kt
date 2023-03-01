package kr.hjkim.village.managers

import kr.hjkim.village.enums.VillagerRole
import kr.hjkim.village.exceptions.SerializeException
import kr.hjkim.village.exceptions.VillageCreateException
import kr.hjkim.village.main
import kr.hjkim.village.objects.Village
import kr.hjkim.village.objects.Villager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.util.ArrayList
import java.util.UUID

object FileManager {
    private val villageFolder: File = File("${main.dataFolder}\\villages")
    private val villagerFolder: File = File("${main.dataFolder}\\villagers")

    /**
     * Village 폴더내에 config.yml 파일을 생성합니다.
     */
    fun createConfigFile() {
        val file = File(main.dataFolder,"config.yml")
        if (!file.exists()) main.saveDefaultConfig()
    }

    private fun loadVillage(villageName: String)

    class VillageBuilder(val villageName: String) {

        private lateinit var file: File
        private lateinit var config: YamlConfiguration

        fun build(): Village? = try {
            Village(villageName,buildVillagers(),buildAreas(),buildChest(),buildMaxExpansion(),buildMaxPlayer())
        } catch (e: VillageCreateException) {
            main.logger.warning(e.message!!)
            null
        } catch (e: SerializeException) {
            main.logger.warning(e.message!!)
            null
        }

        private fun setup() {
            try {
                file = File(villagerFolder,"$villageName.yml")
                config = YamlConfiguration.loadConfiguration(file)
            } catch (e: Exception) {
                throw VillageCreateException("$villageName 마을 데이터 파일을 가져오는데 실패하였습니다.")
            }
        }

        private fun buildVillagers(): List<UUID> {
            val villagers = ArrayList<UUID>()
            for(uuidString in config.getStringList("villagers")) {
                try {
                    villagers.add(UUID.fromString(uuidString))
                } catch (e: IllegalArgumentException) {
                    throw VillageCreateException("$villageName 마을 데이터 파일을 가져오는데 실패하였습니다. ( $uuidString 을 UUID 로 변환하는데 실패하였습니다. )")
                }
            }
            return villagers
        }

        private fun buildAreas(): List<Long> {
            val areas = ArrayList<Long>()
            for(areaString in config.getStringList("areas")) {
                try {
                    areas.add(areaString.toLong())
                } catch (e: NumberFormatException) {
                    throw VillageCreateException("$villageName 마을 데이터 파일을 가져오는데 실패하였습니다. ( $areaString 을 Long 으로 변환하는데 실패하였습니다. )")
                }
            }
            return areas
        }

        private fun buildChest(): Inventory {
            val inventoryString = config.getString("chest") ?: throw VillageCreateException("$villageName 마을 데이터 파일을 가져오는데 실패하였습니다. ( chest 값이 존재하지 않습니다. )")
            return inventoryString.toInventory()
        }

        private fun buildMaxExpansion(): Int = config.getInt("maxExpansion")

        private fun buildMaxPlayer(): Int = config.getInt("maxPlayer")

    }

    /**
     * Villge 파일을 불러옵니다.
     * @param village Village
     */
    fun loadVillageFile(village: Village) {
        val file = File(villageFolder,"${village.name}.yml")
        if (!file.exists()) return
        val config = YamlConfiguration.loadConfiguration(file)
        println (village.name)
        val villagers = config.getStringList("villagers").map { UUID.fromString(it) }
        val areas = config.getStringList("areas").map { it.toLong() }
        val contents = config.getList("chest")?.map { it as? ItemStack ?: ItemStack(Material.AIR) }
        village.setVillagers(villagers)
        village.setAreas(areas)
        village.setMaxExpansion(config.getInt("maxExpansion"))
        village.setMaxPlayer(config.getInt("maxPlayer"))
        if (contents != null ) village.setChest(contents)
    }

    class VillagerBuilder() {

        fun build(): Villager {
            TODO()
        }

    }

    fun getAllVillages() {
        val villageFiles = villageFolder.listFiles { file -> file.extension == "yml" } ?: return
        for (file in villageFiles)  {
            val config = YamlConfiguration.loadConfiguration(file)
            val name = config.getString("name") ?: continue
            VillageManager.createVillage(name)
        }
    }

    /**
     * Config 파일을 불러옵니다.
     * @return YamlConfiguration
     */
    fun loadConfigFile(): YamlConfiguration {
        val file = File(main.dataFolder,"config.yml")
        return YamlConfiguration.loadConfiguration(file)
    }

    /**
     * Villager 파일을 불러옵니다.
     * @param player Player
     */
    fun loadVillagerFile(player: Player) {
        val uuid = player.uniqueId
        val file = File(villagerFolder, "$uuid.yml")
        if (!file.exists()) return
        val config = YamlConfiguration.loadConfiguration(file)
        val villageName = config.getString("villageName") ?: return
        val role = config.getString("role") ?: return
        val villager = VillagerManager.getVillager(uuid)
        if (villager != null) { return }
        if (role == "OWNER") VillagerManager.createVillager(player,villageName,VillagerRole.OWNER)
        if (role == "MEMBER") VillagerManager.createVillager(player,villageName,VillagerRole.MEMBER)
    }

    fun loadOfflineVillagerFile(uuid: UUID): Boolean {
        val file = File(villagerFolder, "$uuid.yml")
        if (!file.exists()) return false
        val config = YamlConfiguration.loadConfiguration(file)
        val villageName = config.getString("villageName") ?: return false
        val role = config.getString("role") ?: return false
        return true
    }

    /**
     * Config 변수를 Config 파일에 저장합니다.
     */
    fun saveConfigFile() {
        val configFile = File(main.dataFolder, "config.yml")
        val config = YamlConfiguration.loadConfiguration(configFile)
        config.save(configFile)
    }

    /**
     * 플레이어의 정보를 UUID 파일에 저장합니다.
     * @param villager Villager
     */
    fun saveVillagerFile(villager: Villager) {
        val file = File(villagerFolder, "${villager.uuid}.yml")
        val config = YamlConfiguration.loadConfiguration(file)
        config.set("uuid", villager.uuid.toString())
        config.set("villageName", villager.villageName)
        config.set("role", villager.villagerRole.toString())
        config.save(file)
    }

    /**
     * 마을의 정보를 village 파일에 저장합니다.
     * @param village Village
     */
    fun saveVillageFile(village: Village) {
        val file = File(villageFolder,"${village.name}.yml")
        val config = YamlConfiguration.loadConfiguration(file)
        config.set("name",village.name)
        config.set("villagers", village.getAllVillagers().map { it.toString() })
        config.set("areas", village.getAllAreas().map { it.toLong()})
        config.set("maxExpansion", village.maxExpansion)
        config.set("maxPlayer", village.maxPlayer)
        config.set("chest", village.chest.contents.asList())
        config.save(file)
    }

    private fun Inventory.toBase64(title): String {
        return try {
            ByteArrayOutputStream().use { byteStream ->
                BukkitObjectOutputStream(byteStream).use { bukkitStream ->
                    for(item in contents) bukkitStream.writeObject(item)
                    Base64Coder.encodeLines(byteStream.toByteArray())
                }
            }
        } catch (e: Exception) {
            throw SerializeException("Inventory 을 String 으로 변환하는데 실패하였습니다.")
        }
    }

    private fun String.toInventory(): Inventory {
        return try {
            val inventory = Bukkit.createInventory(null,54,"마을 창고")
            ByteArrayInputStream(Base64Coder.decodeLines(this)).use { byteStream ->
                BukkitObjectInputStream(byteStream).use { bukkitStream ->
                    for(slot in 0 until 54) inventory.setItem(slot,bukkitStream.readObject() as ItemStack?)
                    inventory
                }
            }
        } catch (e: Exception) {
            throw SerializeException("String 을 Inventory 로 변환하는데 실패하였습니다.")
        }
    }

    private fun ItemStack.toBase64(): String {
        return try {
            ByteArrayOutputStream().use { byteStream ->
                BukkitObjectOutputStream(byteStream).use { bukkitStream ->
                    bukkitStream.writeObject(this)
                    Base64Coder.encodeLines(byteStream.toByteArray())
                }
            }
        } catch (e: Exception) {
            throw SerializeException("ItemStack 을 문자열로 변환하는데 실패하였습니다.")
        }
    }

    private fun String.toItemStack(): ItemStack {
        return try {
            ByteArrayInputStream(Base64Coder.decodeLines(this)).use { byteStream ->
                BukkitObjectInputStream(byteStream).use { bukkitStream ->
                    bukkitStream.readObject() as ItemStack
                }
            }
        } catch (e: Exception) {
            throw SerializeException("String 을 ItemStack 으로 변환하는데 실패하였습니다.")
        }
    }

}