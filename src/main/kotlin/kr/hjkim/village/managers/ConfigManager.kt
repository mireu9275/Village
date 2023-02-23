package kr.hjkim.village.managers

import kr.hjkim.village.exceptions.FileLoadException
import kr.hjkim.village.main
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.UUID

object ConfigManager {

    /**
     * uuids 폴더내에 플레이어의 UUiD 파일을 생성합니다.
     * @param uuid UUID
     */
    fun createVillagerFile(uuid: UUID) {
        val uuidPath = "uuids/$uuid.yml"
        val uuidFile = File(main.dataFolder,uuidPath)
        if (!uuidFile.exists()) main.saveResource(uuidPath,false)
    }

    /**
     * Village 폴더내에 config.yml 파일을 생성합니다.
     */
    fun createConfigFile() {
        val configFile = File(main.dataFolder,"config.yml")
        if (!configFile.exists()) main.saveDefaultConfig()
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
     * Config 파일을 불러옵니다.
     * @return YamlConfiguration
     * @throws FileLoadException 불러올 파일이 없을 떄 발생합니다.
     */
    fun loadConfigFile(): YamlConfiguration {
        val configFile = File(main.dataFolder,"config.yml") ?: throw FileLoadException("config.yml 파일이 존재하지 않습니다.")
        return YamlConfiguration.loadConfiguration(configFile)
    }

    /**
     * 플레이어의 정보를 UUID 파일에 저장합니다.
     * @param uuid UUID
     */
    fun saveVillagerFile(uuid: UUID) {
        val uuidPath = "uuids/$uuid.yml"
        val uuidFile = File(main.dataFolder, uuidPath) ?: throw FileLoadException("$uuid.yml 파일이 존재하지 않습니다.")
        YamlConfiguration.loadConfiguration(uuidFile).save(uuidFile)
    }

}