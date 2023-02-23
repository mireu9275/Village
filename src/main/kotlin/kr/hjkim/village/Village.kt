package kr.hjkim.village

import kr.hjkim.library.KimPlugin
import kr.hjkim.library.managers.DataBaseManager
import kr.hjkim.village.commands.VillageCommands
import kr.hjkim.village.exceptions.FileLoadException
import kr.hjkim.village.listeners.VillageListener
import kr.hjkim.village.managers.ConfigManager
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Village: KimPlugin() {


    override fun onEnable() {
        DataBaseManager.registerSQL()
        ConfigManager.createConfigFile()
        try { ConfigManager.loadConfigFile() }
        catch (e: FileLoadException) { println("파일 불러오기 오류 ( ${e.message} )") }

        registerEvents(
            VillageListener()
        )

        registerCommands(
            "village" to VillageCommands()
        )
    }

    override fun onDisable() {
        DataBaseManager.unRegisterSQL()
    }
}