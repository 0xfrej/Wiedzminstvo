package dev.sharpwave.wiedzminstvo.datagen.support

import com.google.common.collect.Sets
import com.google.gson.GsonBuilder
import net.minecraft.advancements.Advancement
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import net.minecraft.util.ResourceLocation
import org.apache.logging.log4j.LogManager
import java.io.IOException
import java.nio.file.Path
import java.util.function.Consumer

abstract class AbstractAdvancementProvider(private val generator: DataGenerator) : IDataProvider {
    private val tabs: MutableList<Consumer<Consumer<Advancement>>> = mutableListOf()

    protected fun pushTab(tab: Consumer<Consumer<Advancement>>) {
        tabs.add(tab)
    }

    @Throws(IOException::class)
    override fun run(dirCache: DirectoryCache) {
        val outputDir = generator.outputFolder
        val locations: MutableSet<ResourceLocation> = Sets.newHashSet()
        val consumer =
            Consumer { advancement: Advancement ->
                check(locations.add(advancement.id)) { "Duplicate advancement " + advancement.id }
                val path = createPath(outputDir, advancement)
                try {
                    IDataProvider.save(
                        GSON,
                        dirCache,
                        advancement.deconstruct().serializeToJson(),
                        path
                    )
                } catch (var6: IOException) {
                    LOGGER.error("Couldn't save advancement {}", path, var6)
                }
            }
        for (tabConsumer in tabs) {
            tabConsumer.accept(consumer)
        }
    }

    override fun getName(): String {
        return "Advancements"
    }

    companion object {
        private val LOGGER = LogManager.getLogger()
        private val GSON = GsonBuilder().setPrettyPrinting().create()
        private fun createPath(path: Path, advancement: Advancement): Path {
            return path.resolve("data/" + advancement.id.namespace + "/advancements/" + advancement.id.path + ".json")
        }
    }
}
