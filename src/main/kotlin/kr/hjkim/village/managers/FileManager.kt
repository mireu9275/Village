package kr.hjkim.village.managers

import kr.hjkim.village.main
import kr.hjkim.village.objects.Village
import kr.hjkim.village.objects.Villager
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.io.File
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

    fun getAllVillage(): HashSet<String>? {
        val villages = HashSet<String>()
        val villageFiles = villageFolder.listFiles { file -> file.extension == "yml" } ?: return null
        for (file in villageFiles)  {
            val config = YamlConfiguration.loadConfiguration(file)
            val name = config.getString("name") ?: continue
            villages.add(name)
        }
        return villages
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
     * 플레이어의 UUID 파일을 불러옵니다.
     * @param uuid UUID
     */
    fun loadVillagerFile(uuid: UUID) {
        val file = File(villagerFolder, "$uuid.yml")
        if (!file.exists()) return
        val config = YamlConfiguration.loadConfiguration(file)
        val villager = VillagerManager.getVillager(uuid)
    }

    /**
     * Villge 파일을 불러옵니다.
     * @param name String
     */
    fun loadVillageFile(name: String) {
        val file = File(villageFolder,"$name.yml")
        if (!file.exists()) return
        val config = YamlConfiguration.loadConfiguration(file)
        val village = VillageManager.getVillage(name) ?: return

        val villagers = config.getStringList("villagers").map { UUID.fromString(it) }
        val areas = config.getStringList("areas").map { it.toLong() }

        village.setVillagers(villagers)
        village.setAreas(areas)
        village.setMaxExpansion(config.getInt("maxExpansion"))
        village.setMaxExpansion(config.getInt("maxPlayer"))
        village.setChest (config.getList("chest")?.map { it as ItemStack })
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
        config.set("village", villager.villageName)
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
        config.set("areas", village.getAllAreas().map { it.toString() })
        config.set("maxExpansion", village.maxExpansion)
        config.set("maxPlayer", village.maxPlayer)
        config.set("chest",village.chest.contents)
        config.save(file)
    }
}