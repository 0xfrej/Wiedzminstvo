package dev.sharpwave.wiedzminstvo.entity.capabilities.storedhorse

interface IStoredHorse {
    var storageUUID: String
    var ownerUUID: String
    var horseNum: Int
    var isOwned: Boolean
}