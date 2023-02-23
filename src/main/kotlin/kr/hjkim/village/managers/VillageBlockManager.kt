package kr.hjkim.village.managers

import kr.hjkim.library.extensions.editCustomNBT
import kr.hjkim.library.extensions.hasNBT
import kr.hjkim.library.extensions.setInt
import kr.hjkim.library.managers.ItemStackBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object VillageBlockManager {

    /**
     * 마을생성블럭을 생성합니다.
     * @return ItemStack
     */
    fun getVillageBlock(): ItemStack {
        return ItemStackBuilder.build(Material.STONE) { meta ->
            meta.editCustomNBT { it.setInt("village_block",1) }
            meta.lore = listOf("§e마을 생성 블럭","§e500 X 500")
        }
    }

    /**
     * 해당 블럭이 마을생성블럭인지 확인합니다.
     * @param item ItemStack
     * @return Boolean
     */
    fun isVillageBlock(item: ItemStack): Boolean = item.hasNBT("village_block")

}