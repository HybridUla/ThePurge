package com.ula.purge;


import com.ula.purge.ParticleMenu.*;
import com.ula.purge.UserTools.userclick;
import com.ula.purge.UserTools.usermenu;
import com.ula.purge.adminutils.*;
import com.ula.purge.morphs.gui;
import com.ula.purge.players.*;
import de.slikey.effectlib.EffectManager;
import me.libraryaddict.disguise.LibsDisguises;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;


public final class Purge extends JavaPlugin implements Listener {
    private static Plugin plugin;
    private static Purge instance;
    public static Purge getInstance() {

        return instance;
    }
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;
    public static String np = "§2PHANTOM SURVIVAL >> ";
    public FileConfiguration config = getConfig();
    public EffectManager effectManager;

    LibsDisguises api = (LibsDisguises) Bukkit.getServer().getPluginManager().getPlugin("LibsDisguises");

    @Override
    public void onEnable() {

        Bukkit.getServer().getConsoleSender().sendMessage(" §2PHANTOM SURVIVAL >>  STARTING... DO NOT TOUCH ANYTHING! ");
        plugin = this;
        instance = this;
        PluginManager pm = this.getServer().getPluginManager();

        registerEvents();
        setUpCommands();
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();

        }
        effectManager = new EffectManager(this);

        new AFKCommand(this);
/*
        if (!setupEconomy() ) {
            Logger.getLogger("Minecraft").severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        */
        setupPermissions();
        setupChat();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Bukkit.getServer().getConsoleSender().sendMessage(" §4PHANTOM SURVIVAL >> LETS HOPE THE SERVER WAS STOPPED BY AN ADMIN... BUT IF NOT ALL LOGS HAVE BEEN SAVED! ");
        saveConfig();
        if (effectManager != null) {
            effectManager.dispose();
        }
    }


    private void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(this, this);
        pm.registerEvents(new maintenance(this), this);
        pm.registerEvents(new staffmenu(this), this);
        pm.registerEvents(new time(this), this);
        pm.registerEvents(new weather(this), this);
        //pm.registerEvents(new vanish(this), this);
        pm.registerEvents(new gamemode(this), this);
        pm.registerEvents(new particlechooser(this), this);
        pm.registerEvents(new AirHelix(this), this);
        pm.registerEvents(new AirLord(this), this);
        pm.registerEvents(new FireHelix(this), this);
        pm.registerEvents(new FireLord(this), this);
        pm.registerEvents(new WaterHelix(this), this);
        pm.registerEvents(new WaterLord(this), this);
        pm.registerEvents(new ParticleMenu(this), this);
        // pm.registerEvents(new CoinsMenu(this),this);
        // pm.registerEvents(new votifier(this), this);
        // pm.registerEvents(new member(this), this);
        pm.registerEvents(new stafftime(this), this);
        pm.registerEvents(new gui(this), this);
        pm.registerEvents(new playermenu(this),this);
        //  pm.registerEvents(new Cooldown(this), this);
        pm.registerEvents(new userclick(this),this);
        pm.registerEvents(new usermenu(this),this);
        pm.registerEvents(new gamerules(this),this);
        pm.registerEvents(new WitchHelix(this),this);
        pm.registerEvents(new AnvilXPModifier(this), this);
        pm.registerEvents(new playermention(this),this);
        pm.registerEvents(new DoubleXP(this),this);
        pm.registerEvents(new AFKCommand(this), this);
        pm.registerEvents(new EmoteListener(this), this);

    }

    private void setUpCommands() {
        this.getCommand("timeplayed").setExecutor(new TimePlayedCommand());
        this.getCommand("broadcast").setExecutor(new broadcast());
        this.getCommand("mm").setExecutor(new commands());
        this.getCommand("staff").setExecutor(new commands());
        this.getCommand("plugins").setExecutor(new commands());
        this.getCommand("pl").setExecutor(new commands());
        this.getCommand("plugin").setExecutor(new commands());
        this.getCommand("coinv").setExecutor(new commands());
        this.getCommand("rwvillager").setExecutor(new commands());
        this.getCommand("vote").setExecutor(new commands());
        this.getCommand("hub").setExecutor(new commands());
        this.getCommand("mtest").setExecutor(new commands());
        this.getCommand("user").setExecutor(new commands());
        this.getCommand("afk").setExecutor(new commands());

    }
/*
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

 */

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }


}
