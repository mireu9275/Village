package kr.hjkim.village

import kr.hjkim.library.KimPlugin
import kr.hjkim.library.managers.DataBaseManager
import kr.hjkim.village.commands.VillageCommands
import kr.hjkim.village.exceptions.FileLoadException
import kr.hjkim.village.listeners.VillageListener
import kr.hjkim.village.managers.FileManager

class Village: KimPlugin() {


    override fun onEnable() {
        main = this
        DataBaseManager.registerSQL()
        FileManager.createConfigFile()
        try { FileManager.loadConfigFile() }
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