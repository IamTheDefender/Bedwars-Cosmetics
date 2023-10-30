

package me.defender.cosmetics.api.util;


import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.proxy.BedWarsProxy;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.defender.cosmetics.Cosmetics;
import me.defender.cosmetics.api.BwcAPI;
import me.defender.cosmetics.menu.MainMenu;
import me.defender.cosmetics.database.PlayerData;
import me.defender.cosmetics.database.PlayerOwnedData;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;


public class Utility {
    public static Map<UUID, PlayerData> playerDataList;
    public static Map<UUID, PlayerOwnedData> playerOwnedDataList;


    /**
     * @param s the main string for example "Click me"
     * @param st the message when they hover.
     * @return a hover able message.
     */
    public static TextComponent hoverablemsg(String s, String st) {
        final TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', s));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', st)).create()));
        return message;
    }

    /**
     * @return JavaPlugin class of Cosmetics.class
     */
    public static Cosmetics plugin() {
        return Cosmetics.getPlugin(Cosmetics.class);
    }

    /**
     * Open's the main menu for the given player
     * @param p player object
     */
    public static void openMainMenu(Player p){
            new MainMenu(p).open(p);
        }

    /**
     * Get Message from Language file of bedwars1058 for player object, works with Bedwars1058 and BedwarsProxy
     * @param p player object
     * @param path Path where the string is located.
     * @return the result
     */
    public static String getMSGLang(Player p, String path) {
        BwcAPI api = new BwcAPI();
        if (api.isProxy()) {
            if (!plugin().isBw2023()){
                return com.andrei1058.bedwars.proxy.language.Language.getMsg(p, path);
            } else {
                return com.tomkeuper.bedwars.api.language.Language.getMsg(p, path);
            }
        }
        if (!plugin().isBw2023()){
            return Language.getMsg(p, path);
        } else {
            return com.tomkeuper.bedwars.api.language.Language.getMsg(p, path);
        }
    }

    /**
     * Get a list from language file according to the player selected language iso. Works with Bedwars1058 and BedwarsProxy
     * @param p player object
     * @param path path for the list in the YAML file.
     * @return the result
     */
    public static List<String> getListLang(Player p, String path) {
        BwcAPI api = new BwcAPI();
        if (api.isProxy()) {
            return BedWarsProxy.getAPI().getLanguageUtil().getList(p, path);
        }
        if (!plugin().isBw2023()){
            return Language.getList(p, path);
        } else {
            return com.tomkeuper.bedwars.api.language.Language.getList(p, path);
        }
    }

    /**
     * Save the object to the path in language file, works with BedWars1058 and BedWarsProxy
     * @param path Path where the object will be saved.
     * @param ob object
     */
    public static void saveIfNotExistsLang(String path, Object ob) {
        BwcAPI api = new BwcAPI();
        if (api.isProxy()) {
            BedWarsProxy.getAPI().getLanguageUtil().saveIfNotExists(path, ob);
            return;
        }
        if (!plugin().isBw2023()){
            Language.saveIfNotExists(path, ob);
        } else {
            com.tomkeuper.bedwars.api.language.Language.saveIfNotExists(path, ob);
        }
    }

    /**
     * Check's if the player is in an Arena.
     * @param p player to check for.
     * @return true if player is in arena, false otherwise.
     */
    public static boolean isInArena(Player p) {
        BwcAPI api = new BwcAPI();
        if (api.isProxy())
            return false;
        if (!plugin().isBw2023()){
            return plugin().getBedWars1058API().getArenaUtil().getArenaByPlayer(p) != null;
        } else {
            return plugin().getBedWars2023API().getArenaUtil().getArenaByPlayer(p) != null;
        }
    }


    /**
     * Get skin of a player from their name
     * @param name name of the player
     * @return String[]{texture, signature}
     */
    public static String[] getFromName(String name) {
        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();

            return new String[]{texture, signature};
        } catch (IOException e) {
            System.err.println("Could not get skin data from session servers!");
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Save a file from plugin JAR to a folder
     * @param inputStream inputStream of the file
     * @param fileName name of the file
     * @param folder folder where the file will be saved
     */
    public static void saveFileFromInputStream(InputStream inputStream, String fileName, File folder)  {
        File file = new File(folder, fileName);
        if(file.exists()) return;
        try (OutputStream outputStream = Files.newOutputStream(file.toPath())) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}