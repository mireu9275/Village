package kr.hjkim.village

import kr.hjkim.library.KimPlugin
import kr.hjkim.library.managers.DataBaseManager
import org.bukkit.plugin.java.JavaPlugin

class Village: KimPlugin() {
    override fun onEnable() {
        DataBaseManager.registerSQL()
    }

    override fun onDisable() {
        DataBaseManager.unRegisterSQL()
    }
}