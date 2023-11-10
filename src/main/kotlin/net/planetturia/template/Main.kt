package net.planetturia.template

import de.miraculixx.kpaper.extensions.bukkit.cmp
import de.miraculixx.kpaper.main.KPaper
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig

class Main : KPaper() {

    companion object {
        lateinit var INSTANCE: KPaper
    }

    override fun load() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true))
        server.consoleSender.sendMessage(cmp("» Template geladen."))

        // Commands
    }

    override fun startup() {
        INSTANCE = this

        CommandAPI.onEnable()
        config.options().copyDefaults(true)
        saveDefaultConfig()

        // Constructors

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
    }
}

val PluginManager by lazy { Main.INSTANCE }