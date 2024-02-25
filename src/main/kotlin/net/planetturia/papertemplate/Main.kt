package net.planetturia.papertemplate

import de.miraculixx.kpaper.extensions.bukkit.cmp
import de.miraculixx.kpaper.main.KPaper
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.planetturia.cirrofy.Cirrofy

class Main : KPaper() {

    companion object {
        lateinit var INSTANCE: KPaper

        lateinit var prefix: Component
        lateinit var branding: Component
        lateinit var boldBranding: Component
    }

    override fun load() {
        INSTANCE = this
        redisAuth()

        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true))

        server.consoleSender.sendMessage(cmp("» Template geladen."))
    }

    override fun startup() {

        CommandAPI.onEnable()
        config.options().copyDefaults(true)
        saveDefaultConfig()

        loadMeta()

        // Events
    }

    override fun shutdown() {
        CommandAPI.onDisable()
        server.consoleSender.sendMessage(cmp("» Template entladen."))
    }

    override fun reloadConfig() {
        super.reloadConfig()

        saveDefaultConfig()
        config.options().copyDefaults(true)
        saveConfig()

        loadMeta()
    }

    private fun loadMeta() {
        prefix = MiniMessage.miniMessage().deserialize(config.getString("prefix").toString())
        branding = MiniMessage.miniMessage().deserialize(config.getString("branding").toString())
        boldBranding = MiniMessage.miniMessage().deserialize(config.getString("bold-branding").toString())
    }

    private fun redisAuth() {
        // Primary
        val hostAndPort = config.getString("RedisURL")
        val host = hostAndPort?.split(":")?.get(0) ?: "127.0.0.1"
        val port = hostAndPort?.split(":")?.get(1)?.toInt() ?: 6379
        val pass = config.getString("RedisPass") ?: "" // Leer lassen, wenn kein Passwort gesetzt ist

        // Cache
        val cacheHostAndPort = config.getString("RedisCacheURL")
        val cacheHost = cacheHostAndPort?.split(":")?.get(0) ?: "127.0.0.1"
        val cachePort = cacheHostAndPort?.split(":")?.get(1)?.toInt() ?: 6370
        val cachePass = config.getString("RedisCachePass") ?: "" // Leer lassen, wenn kein Passwort gesetzt ist

        Cirrofy.breds().setAuth(host, port, pass)
        Cirrofy.breds().setCacheAuth(cacheHost, cachePort, cachePass)
    }
}
val PluginManager by lazy { Main.INSTANCE }