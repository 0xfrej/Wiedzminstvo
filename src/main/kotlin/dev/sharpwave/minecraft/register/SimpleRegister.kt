package dev.sharpwave.minecraft.register

import java.util.function.Supplier
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class SimpleRegister<K, V> {
    private val entries = LinkedHashMap<K, ValueHolderDelegate<in K, out V>>()

    open class ValueHolderDelegate<K, V>(private var key: K, private var value: V) : ReadOnlyProperty<Any?, V>,
        Supplier<V>, () -> V {
        override fun getValue(thisRef: Any?, property: KProperty<*>): V {
            return value
        }

        override fun get(): V {
            return value
        }

        override fun invoke(): V {
            return value
        }

        fun getRegistryKey(): K {
            return key
        }
    }

    protected fun register(key: K, value: V): ValueHolderDelegate<K, V> {
        val delegate = ValueHolderDelegate(key, value)
        entries[key] = delegate
        return delegate
    }

    fun getEntryKeys(): Set<K> {
        return entries.keys
    }

    fun getEntryForKey(key: K): ValueHolderDelegate<in K, out V> {
        return entries[key]!!
    }
}