package kr.hjkim.village

import kr.hjkim.village.managers.VillagerManager
import org.bukkit.entity.Player

fun Player.hasVillage(): Boolean = VillagerManager.getVillager(uniqueId) != null