package kr.hjkim.village.managers

import kr.hjkim.library.extensions.editCustomNBT
import kr.hjkim.library.extensions.hasNBT
import kr.hjkim.library.extensions.setInt
import kr.hjkim.library.managers.ItemStackBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object VillageBlockManager {

    fun getVillageBlock(): ItemStack {
        return ItemStackBuilder.build(Material.STONE) { meta ->
            meta.editCustomNBT { it.setInt("village_block",1) }
            meta.lore = listOf("§e마을 생성 블럭","§e500 X 500")
        }
    }

    fun isVillageBlock(item: ItemStack): Boolean = item.hasNBT("village_block")

}