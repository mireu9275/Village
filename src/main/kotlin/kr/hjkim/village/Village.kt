package kr.hjkim.village

import kr.hjkim.library.KimPlugin
import kr.hjkim.library.managers.DataBaseManager
import kr.hjkim.village.commands.VillageCommands
import kr.hjkim.village.listeners.VillageListener
import kr.hjkim.village.managers.FileManager
import kr.hjkim.village.managers.VillageManager

class Village: KimPlugin() {


    override fun onEnable() {
        main = this
        DataBaseManager.registerSQL()
        FileManager.createConfigFile()
        FileManager.loadConfigFile()
        FileManager.getAllVillages()

        registerEvents(
            VillageListener()
        )
        registerCommands(
            "village" to VillageCommands()
        )
    }

    override fun onDisable() {
        DataBaseManager.unRegisterSQL()
        VillageManager.saveAllVillages()
    }
}