package BetterTeleports;

import BetterTeleports.commands.betterteleports;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public final class Main extends JavaPlugin{

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("(BetterTeleports) Loading...");
        File config = new File(getDataFolder(),"config.yml");
        if (! (config.exists())) {
            Bukkit.getConsoleSender().sendMessage("Config file not found, Creating one for you!");

        }
        saveDefaultConfig();
        LanguageUtil();
        new betterteleports(this);
        Bukkit.getConsoleSender().sendMessage("(BetterTeleports) Has loaded, Welcome!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void LanguageUtil() {

        switch (Objects.requireNonNull(this.getConfig().getString("Language"))) {
            case "en" -> {
                Bukkit.getConsoleSender().sendMessage("§e§lThe plugin language is set to §a§lEnglish§e§l.");
                saveResource("messages.properties",false);

            }
            case "es" -> {
                Bukkit.getConsoleSender().sendMessage("§e§lEl idioma del plugin esta configurado en §a§lEspanol§e§l.");
                saveResource("messages_es.properties",false);

            }
            default -> {
                Bukkit.getConsoleSender().sendMessage("§4§lAn error occurred, the config.yml did not have a language set.");
                Bukkit.getConsoleSender().sendMessage("§4§lIt will be defaulted as english.");

            }
        }

    }

    public String getMessage(String e) {
        switch (Objects.requireNonNull(this.getConfig().getString("Language"))) {
            case "en" -> {
                InputStreamReader langfile;
                File loc = new File(getDataFolder(),"messages.properties");
                try {
                    langfile = new FileReader(loc);
                } catch (FileNotFoundException v) {
                    saveResource("messages.properties",true);
                    return "An error occurred! Please try that command again later!";
                }
                Properties lang = new Properties();
                try {
                    lang.load(langfile);
                } catch (IOException ignored) {
                }
                String translate = lang.get(e) + "";
                if (translate.equals("null")) {
                    InputStream langfilenull;
                    langfilenull = getResource("messages.properties");
                    Properties langnull = new Properties();
                    try {
                        langnull.load(langfilenull);
                    } catch (IOException ignored) {
                    }
                    translate = langnull.getProperty(e);
                }
                return translate.replace('&','§');
            }

            case "es" -> {
                InputStreamReader langfile;
                File loc = new File(getDataFolder(),"messages_es.properties");

                try {
                    langfile = new FileReader(loc);
                } catch (FileNotFoundException v) {
                    saveResource("messages_es.properties",true);
                    return "An error occurred! Please try that command again later!";
                }
                Properties lang = new Properties();
                try {
                    lang.load(langfile);
                } catch (IOException ignored) {
                }
                String translate = lang.get(e) + "";
                if (translate.equals("null")) {
                    InputStream langfilenull;
                    langfilenull = getResource("messages_es.properties");
                    Properties langnull = new Properties();
                    try {
                        langnull.load(langfilenull);
                    } catch (IOException ignored) {
                    }
                    translate = langnull.getProperty(e);
                }
                return translate.replace('&','§');
            }
            default -> {
                return null;
            }
        }
    }
}
