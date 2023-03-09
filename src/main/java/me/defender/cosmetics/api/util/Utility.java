

package me.defender.cosmetics.api.util;


import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.proxy.BedWarsProxy;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hakan.core.HCore;
import com.hakan.core.ui.inventory.InventoryGui;
import com.hakan.core.ui.inventory.pagination.Pagination;
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
import org.bukkit.event.inventory.InventoryType;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
     * Creates an infinite page loop for the given GUI on the items based in the Page object of the gui.
      * @param page the pagination object for the GUI
     * @param inv the GUI
     * @param guiName Title for the GUI
     */
    public static void createPages(Pagination page, InventoryGui inv, String guiName) {
        if (page.getFirstPage() != page.getLastPage()) {
            if (page.isLastPage()) {
                inv.setItem(45, HCore.itemBuilder(Material.ARROW).name(true, "&aPrevious Page").build(), (e) -> {
                    int number = page.getPreviousPage() + 1;
                    String title;
                    if (page.getPreviousPage() != page.getFirstPage()) {
                        title = guiName + " Page " + number;
                    } else {
                        title = guiName;
                    }
                    InventoryGui inv1 = new InventoryGui("page", title, 6, InventoryType.CHEST);
                    inv1.open((Player) e.getWhoClicked());
                    page.getPage(page.getPreviousPage()).getItems().forEach(inv1::setItem);
                    page.setCurrentPage(page.getPreviousPage());
                    if (page.isFirstPage()) {
                        if (plugin().getConfig().getBoolean("BackItemInCosmeticsMenu")) {
                            inv1.setItem(49, HCore.itemBuilder(Material.ARROW).name(true, "&aBack").build(), (clickEvent) -> {
                                Utility.openMainMenu((Player) clickEvent.getWhoClicked());
                            });
                        }
                    }
                    createPages(page, inv1, guiName);
                });

            } else if (page.isFirstPage()) {
                inv.setItem(53, HCore.itemBuilder(Material.ARROW).name(true, "&aNext page").build(), (e) -> {
                    int number = page.getNextPage() + 1;
                    InventoryGui inv1 = new InventoryGui("page", guiName + " Page " + number, 6, InventoryType.CHEST);
                    inv1.open((Player) e.getWhoClicked());
                    page.getPage(page.getNextPage()).getItems().forEach(inv1::setItem);
                    page.setCurrentPage(page.getNextPage());
                    createPages(page, inv1, guiName);
                });
            }
            if (page.isFirstPage())
                return;
            if (page.isLastPage())
                return;

            inv.setItem(45, HCore.itemBuilder(Material.ARROW).name(true, "&aPrevious Page").build(), (e) -> {
                int number = page.getPreviousPage() + 1;
                String title;
                if (page.getPreviousPage() == page.getFirstPage()) {
                    title = guiName + " Page " + number;
                } else {
                    title = guiName;
                }
                InventoryGui inv1 = new InventoryGui("page", title, 6, InventoryType.CHEST);
                inv1.open((Player) e.getWhoClicked());
                page.getPage(page.getPreviousPage()).getItems().forEach(inv1::setItem);
                page.setCurrentPage(page.getPreviousPage());
                if (page.isFirstPage()) {
                    if (plugin().getConfig().getBoolean("BackItemInCosmeticsMenu")) {
                        inv1.setItem(49, HCore.itemBuilder(Material.ARROW).name(true, "&aBack").build(), (clickEvent) -> {
                            Utility.openMainMenu((Player) clickEvent.getWhoClicked());
                        });
                    }
                }
                createPages(page, inv1, guiName);
            });


            inv.setItem(53, HCore.itemBuilder(Material.ARROW).name(true, "&aNext page").build(), (e) -> {
                int number = page.getNextPage() + 1;
                InventoryGui inv1 = new InventoryGui("page", guiName + " Page " + number, 6, InventoryType.CHEST);
                inv1.whenOpened((event) -> {
                    page.getPage(page.getNextPage()).getItems().forEach(inv1::setItem);
                    page.setCurrentPage(page.getNextPage());
                    createPages(page, inv1, guiName);
                });
            });
        }
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
            return com.andrei1058.bedwars.proxy.language.Language.getMsg(p, path);
        }
        return Language.getMsg(p, path);
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
        return Language.getList(p, path);
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
        Language.saveIfNotExists(path, ob);
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
        return api.getBwAPI().getArenaUtil().getArenaByPlayer(p) != null;
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


}
