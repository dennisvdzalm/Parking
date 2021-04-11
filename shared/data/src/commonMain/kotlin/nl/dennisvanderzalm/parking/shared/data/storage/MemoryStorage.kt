package nl.dennisvanderzalm.parking.shared.data.storage

class MemoryStorage {

    private val storage by lazy { mutableMapOf<String, Any>() }

    fun <T : Any> storeOrUpdate(key: String, obj: T) {
        storage[key] = obj
    }

    fun <T : Any> get(key: String): T {
        return storage[key] as? T ?: error("Object doesn't exist")
    }

    fun remove(key: String) {
        storage.remove(key)
    }
}
