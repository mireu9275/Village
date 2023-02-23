package kr.hjkim.village.managers

import kr.hjkim.village.exceptions.FileLoadException
import kr.hjkim.village.main
import org.bukkit.configuration.file.YamlConfiguration
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.util.UUID

object FileManager {

    /**
     * Village 폴더내에 config.yml 파일을 생성합니다.
     */
    fun createConfigFile() {
        val configFile = File(main.dataFolder,"config.yml")
        if (!configFile.exists()) main.saveDefaultConfig()
    }

    /**
     * uuids 폴더내에 플레이어의 UUID 파일을 생성합니다.
     * @param uuid UUID
     */
    fun createVillagerFile(uuid: UUID) {
        val uuidPath = "uuids/$uuid.yml"
        val uuidFile = File(main.dataFolder,uuidPath)
        uuidFile.apply {
            if (!exists()) {
                parentFile.mkdirs()
                createNewFile()
            }
        }
    }

    /**
     * Village 폴더내에 village.yml 파일을 생성합니다.
     * @param name String
     */
    fun createVillageFile(name: String) {
        val villagePath = "villages/$name.yml"
        val villageFile = File(main.dataFolder, villagePath)
        villageFile.apply {
            if (exists()) {
                parentFile.mkdirs()
                createNewFile()
            }
        }
    }

    /**
     * Config 파일을 불러옵니다.
     * @return YamlConfiguration
     * @throws FileLoadException 불러올 파일이 없을 떄 발생합니다.
     */
    fun loadConfigFile(): YamlConfiguration {
        val configFile = File(main.dataFolder,"config.yml") ?: throw FileLoadException("config.yml 파일이 존재하지 않습니다.")
        return YamlConfiguration.loadConfiguration(configFile)
    }

    /**
     * 플레이어의 UUID 파일을 불러옵니다.
     * @param uuid UUID
     * @return YamlConfiguration
     * @throws FileLoadException 불러올 파일이 없을 떄 발생합니다'
     */
    fun loadVillagerFile(uuid: UUID): YamlConfiguration {
        val uuidPath = "uuids/$uuid.yml"
        val uuidFile = File(main.dataFolder, uuidPath) ?: throw FileLoadException("$uuid.yml 파일이 존재하지 않습니다.")
        return YamlConfiguration.loadConfiguration(uuidFile)
    }

    /**
     * Villge 파일을 불러옵니다.
     * @param name String
     * @return YamlConfiguration
     * @throws FileLoadException 불러올 파일이 없을 떄 발생합니다.
     */
    fun loadVillageFile(name: String): YamlConfiguration {
        val villagePath = "villages/$name.yml"
        val villageFile = File(main.dataFolder, villagePath) ?: throw FileLoadException("$name.yml 파일이 존재하지 않습니다.")
        return YamlConfiguration.loadConfiguration(villageFile)
    }

    /**
     * Config 변수를 Config 파일에 저장합니다.
     * @throws FileLoadException 불러올 파일이 없을 떄 발생합니다.
     */
    fun saveConfigFile() {
        val configFile = File(main.dataFolder, "config.yml") ?: throw FileLoadException("config.yml 파일이 존재하지 않습니다.")
        val config = YamlConfiguration.loadConfiguration(configFile)
        config.save(configFile)
    }

    /**
     * 플레이어의 정보를 UUID 파일에 저장합니다.
     * @param uuid UUID
     * @throws FileLoadException 불러올 파일이 없을 떄 발생합니다.
     */
    fun saveVillagerFile(uuid: UUID, yamlConfig: YamlConfiguration) {
        val uuidPath = "uuids/$uuid.yml"
        val uuidFile = File(main.dataFolder, uuidPath) ?: throw FileLoadException("$uuid.yml 파일이 존재하지 않습니다.")
        yamlConfig.save(uuidFile)
    }

    /**
     * 마을의 정보를 village 파일에 저장합니다.
     * @param name String
     * @throws FileLoadException 불러올 파일이 없을 떄 발생합니다.
     */
    fun saveVillageFile(name: String, yamlConfig: YamlConfiguration) {
        val villagePath = "villages/$name.yml"
        val villageFile = File(main.dataFolder, villagePath) ?: throw FileLoadException("$name.yml 파일이 존재하지 않습니다.")
        yamlConfig.save(villageFile)
    }

}