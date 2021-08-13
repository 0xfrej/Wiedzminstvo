package dev.sharpwave.wiedzminstvo.capabilities.storedhorse

class StoredHorse : IStoredHorse {
    override var storageUUID: String? = ""
    override var ownerUUID: String? = ""
    override var horseNum = 0
    override var isOwned = false
}