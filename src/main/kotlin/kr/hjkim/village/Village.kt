package kr.hjkim.village

import kr.hjkim.library.KimPlugin
import kr.hjkim.library.managers.DataBaseManager
import kr.hjkim.village.commands.VillageCommands
import kr.hjkim.village.listeners.VillageListener
import org.bukkit.plugin.java.JavaPlugin

class Village: KimPlugin() {
    override fun onEnable() {
        DataBaseManager.registerSQL()



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