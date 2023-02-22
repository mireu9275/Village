package kr.hjkim.village.listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class VillageListener: Listener {

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        val block = event.blockPlaced

        if (block.type != Material.STONE) return

        val lore = ItemStack(block.type).itemMeta.lore ?: return

        if (lore.contains("마을 생성 블록")) {
            val villager = VillagerManager.getVillager(uuid)
            if (villager == null) { player.sendMessage("당신은 아직 마을에 속해있지 않습니다."); return }
            player.sendMessage("마을 생성 블록을 설치하였습니다.")
        }
    }
}