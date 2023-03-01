package kr.hjkim.village.objects

import kr.hjkim.library.coroutine.async
import kr.hjkim.village.managers.RequestManager
import kr.hjkim.village.managers.VillagerManager
import org.bukkit.entity.Player
import java.util.UUID

class VillageInviteRequest(
    val sender: Player,
    val receiver: Player,
    val village: Village
) {

    private var isExpired: Boolean = false

    private val senderUUID: UUID = sender.uniqueId
    private val receiverUUID: UUID = receiver.uniqueId

    private val senderName: String = sender.name
    private val receiverName: String = receiver.name

    fun request() {
        if(VillagerManager.containsVillager(receiverUUID)) {
            sender.sendMessage("$receiverName 님은 이미 마을이 있습니다.")
            return
        }
        val request = RequestManager.getRequest(senderUUID)
        if(request != null) {
            sender.sendMessage("당신은 이미 ${request.receiverName} 님에게 보낸 요청이 있습니다.")
            return
        }
        if(RequestManager.getRequest(receiverUUID) != null) {
            sender.sendMessage("$receiverName 님은 이미 처리중인 요청이 있습니다.")
            return
        }
        RequestManager.setRequest(sender.uniqueId,this)
        RequestManager.setRequest(receiver.uniqueId,this)
        sender.sendMessage("$receiverName 님께 초대 요청을 보냈습니다.")
        receiver.sendMessage("$senderName 님으로 부터 마을 초대 요청이 왔습니다.")
        queue()
    }

    fun accept() {
        if(VillagerManager.containsVillager(receiverUUID)) {
            sender.sendMessage("$receiverName 님은 이미 마을이 있습니다.")
            return
        }
        sender.sendMessage("$receiverName 님께 보낸 요청이 수락되었습니다.")
        receiver.sendMessage("$senderName 님의 요청을 수락했습니다.")
        RequestManager.removeRequest(senderUUID)
        RequestManager.removeRequest(receiverUUID)
        isExpired = true
        village.addVillager(receiver)
    }

    fun denied() {
        sender.sendMessage("$receiverName 님께 보낸 요청이 거절되었습니다.")
        receiver.sendMessage("$senderName 님의 요청을 거절했습니다.")
        RequestManager.removeRequest(senderUUID)
        RequestManager.removeRequest(receiverUUID)
        isExpired = true
    }

    private fun expire() {
        sender.sendMessage("$receiverName 님께 보낸 요청이 만료되었습니다.")
        receiver.sendMessage("$senderName 님의 요청이 만료되었습니다.")
        RequestManager.removeRequest(senderUUID)
        RequestManager.removeRequest(receiverUUID)
    }

    private fun queue() {
        async {
            waitFor(200)
            if(!isExpired) expire()
        }
    }

}