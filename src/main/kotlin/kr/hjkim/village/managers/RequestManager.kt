package kr.hjkim.village.managers

import it.unimi.dsi.fastutil.Hash
import kr.hjkim.village.objects.VillageInviteRequest
import java.util.UUID

object RequestManager {

    private val requestMap: HashMap<UUID,VillageInviteRequest> = HashMap()

    fun getRequest(uuid: UUID): VillageInviteRequest? = requestMap[uuid]

    fun setRequest(uuid: UUID, request: VillageInviteRequest) { requestMap[uuid] = request }

    fun removeRequest(uuid: UUID) { requestMap.remove(uuid) }

}