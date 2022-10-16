// 
// @author IamTheDefender
// 

package me.defender.api.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.proxy.BedWarsProxy;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hakan.core.HCore;
import com.hakan.core.npc.HNPC;
import com.hakan.core.skin.Skin;
import com.hakan.core.ui.inventory.HInventory;
import com.hakan.core.ui.inventory.pagination.Pagination;
import com.hakan.core.utils.ColorUtil;
import me.defender.api.BwcAPI;
import me.defender.api.events.CosmeticPurchaseEvent;
import me.defender.menus.mainMenu;
import me.defender.support.sounds.GSound;
import me.defender.api.enums.Cosmetics;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import me.defender.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;



public class util
{
	public static Sound villager_yes = GSound.ENTITY_VILLAGER_YES.parseSound();
	public static Sound villager_no = GSound.ENTITY_VILLAGER_NO.parseSound();
	public static Sound Note_Pling = GSound.BLOCK_NOTE_BLOCK_PLING.parseSound();
	public static Sound Explode = GSound.ENTITY_GENERIC_EXPLODE.parseSound();
	public static Sound egg_pop = GSound.ENTITY_CHICKEN_EGG.parseSound();
	public static Sound enderman_teleport = GSound.ENTITY_ENDERMAN_TELEPORT.parseSound();
    public static void createEntityNPC(final EntityType ent, final Location loc, final String name) {
        final NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());
        final NPC npc = registry.createNPC(ent, "");
        npc.setBukkitEntityType(ent);
        npc.setName("&r");
        npc.data().setPersistent(NPC.NAMEPLATE_VISIBLE_METADATA, false);
        npc.spawn(loc);

    }

    public static void createShopKeeperNPC(Player p, Location loc, Location loc1, String value, String sign, Boolean mirror) {

        Skin skin = null;
        if (mirror) {
            List<String> values = Arrays.asList(getFromName(p.getName()));
            skin = new Skin(values.get(0), values.get(1));
        }else{
             skin = new Skin(value, sign);
        }


        HCore.npcBuilder(ThreadLocalRandom.current().nextInt() + "")
                .skin(skin)
                .showEveryone(true)
                .target(HNPC.LookTarget.NEAREST)
                .location(loc)
                .lines(ChatColor.RESET + "").whenClicked(((player, action) -> {
                    ShopManager.shop.open(p, PlayerQuickBuyCache.getQuickBuyCache(p.getUniqueId()), true);
                }))
                .build();
        HCore.npcBuilder(ThreadLocalRandom.current().nextInt() + "")
                .skin(skin)
                .showEveryone(true)
                .target(HNPC.LookTarget.NEAREST)
                .location(loc1)
                .lines(ChatColor.RESET + "").whenClicked(((player, action) -> {
                    UpgradesManager.getMenuForArena(BedWars.getAPI().getArenaUtil().getArenaByPlayer(p)).open(p);
                }))
                .build();
    }
    public static void spawnShopKeeperNPC(Player p, Location loc, Location loc1) {
        Main plugin = plugin();
        String skin = new BwcAPI().getSelectedCosmetic(p, Cosmetics.ShopKeeperSkin);
        String skinvalue = plugin.shopkeeperdata.getConfig().getString("ShopKeeperSkins." + skin + ".Skin-value");
        String skinsign = plugin.shopkeeperdata.getConfig().getString("ShopKeeperSkins." + skin + ".Skin-sign");
        String etype = plugin.shopkeeperdata.getConfig().getString("ShopKeeperSkins." + skin + ".EntityType");
        Boolean mirror = plugin.shopkeeperdata.getConfig().getBoolean("ShopKeeperSkins." + skin + ".Mirror");

        if(mirror){
            createShopKeeperNPC(p, loc, loc1, skinvalue, skinsign, true);
            return;
        }
        if(etype != null) {
        	createEntityNPC(EntityType.valueOf(etype), loc1, skin);
        	createEntityNPC(EntityType.valueOf(etype), loc, skin);
        }else if(skinvalue != null && skinsign != null) {
        	createShopKeeperNPC(p, loc, loc1, skinvalue, skinsign, false);
        }
    }


    public static String color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static TextComponent hoverablemsg(String s, String st) {
        final TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', s));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', st)).create()));
        return message;
    }

    public static Main plugin() {
        final Main plugin = Main.getPlugin(Main.class);
        return plugin;
    }

    public static String getUUID(Player p){
        if(util.plugin().getConfig().getBoolean("Use-Name")){
            return p.getName();
        }else {
            return p.getUniqueId().toString();
        }
    }

    public static void updateOwned(Player p){
        int vd = 0;
        int ws = 0;
        int shopkeeper = 0;
        int pt = 0;
        int km = 0;
        int gly = 0;
        int finalkill = 0;
        int dc = 0;
        int bbe = 0;
        int spray = 0;
        int islandtopper = 0;

        // Victory Dances
        for(String name : plugin().vdconf.getConfig().getConfigurationSection("VictoryDances").getKeys(false)){
            if(p.hasPermission("VictoryDances." + name)){
                vd = vd + 1;
            }
        }
        plugin().playerData.getConfig().set(getUUID(p) + ".VD-Owned", vd);

        // Wood Skins
        for(String name : plugin().woodskindata.getConfig().getConfigurationSection("WoodSkins").getKeys(false)){
            if(p.hasPermission("woodskin." + name.replace("-", ""))){
                ws = ws + 1;
            }
        }
        plugin().playerData.getConfig().set(getUUID(p) + ".WS-Owned", ws);

        // Shop Keeper Skins
        for(String name : plugin().shopkeeperdata.getConfig().getConfigurationSection("ShopKeeperSkins").getKeys(false)){
            if(p.hasPermission("ShopKeeperSkins." + name)){
                shopkeeper = shopkeeper + 1;
            }
        }
        plugin().playerData.getConfig().set(getUUID(p) + ".ShopKeeper-Owned", shopkeeper);

        // Projectile Trails
        for(String name : plugin().pedata.getConfig().getConfigurationSection("ProjectileTrails").getKeys(false)){
            if(p.hasPermission("pt." + name)){
                pt = pt + 1;
            }
        }
        plugin().playerData.getConfig().set(getUUID(p) + ".PT-Owned", pt);

        // Kill Messages
        for(String name : plugin().killmessagecfg.getConfig().getConfigurationSection("KillMessages").getKeys(false)){
            if(p.hasPermission("KillMessages." + name)){
                km = km + 1;
            }
        }
        plugin().playerData.getConfig().set(getUUID(p) + ".KM-Owned", km);

        // Glyphs
        for(String name : plugin().glyconf.getConfig().getConfigurationSection("Glyphs").getKeys(false)){
            if(p.hasPermission("Glyphs." + name)){
                gly = gly + 1;
            }
        }
        plugin().playerData.getConfig().set(getUUID(p) + ".GLY-Owned", gly);

        // Final Kill Effects
        for(String name : plugin().finalkilldata.getConfig().getConfigurationSection("FinalKillEffects").getKeys(false)){
            if(p.hasPermission("FinalKillEffects." + name.replace("-", ""))){
                finalkill = finalkill + 1;
            }
        }
        plugin().playerData.getConfig().set(getUUID(p) + ".FinalKill-Owned", finalkill);


        // Death Cries
        for(String name : plugin().dcdata.getConfig().getConfigurationSection("DeathCries").getKeys(false)){
            if(p.hasPermission("DeathCries." + name)){
                dc = dc + 1;
            }
        }
        plugin().playerData.getConfig().set(getUUID(p) + ".DC-Owned", dc);

        // bedbreakeffect
        for(String name : plugin().bbedata.getConfig().getConfigurationSection("BedBreakEffects").getKeys(false)){
            if(p.hasPermission("bedbreakeffect." + name.replace("-", ""))){
                bbe = bbe + 1;
            }
        }
        plugin().playerData.getConfig().set(getUUID(p) + ".BBE-Owned",  bbe);

        // Sprays

        for(String name : plugin().spraysdata.getConfig().getConfigurationSection("Sprays").getKeys(false)){
            if(p.hasPermission("Sprays." + name)){
                spray = spray + 1;
            }
        }
        plugin().playerData.getConfig().set(getUUID(p) + ".Spray-Owned", spray);

        plugin().playerData.getConfig().options().copyDefaults(true);
        plugin().playerData.savecfg();

        // IslandTopper

        for(String name : plugin().spraysdata.getConfig().getConfigurationSection("Sprays").getKeys(false)){
            if(p.hasPermission("islandtopper." + name)){
                 islandtopper = islandtopper + 1;
            }
        }
        savePlayerData(p , "IslandTopper-Owned", islandtopper);

    }

    public static String getOwned(Player p, String category){
        if(category.equals("VD")){
         return plugin().playerData.getConfig().getString(getUUID(p) + ".VD-Owned");
        } else if (category.equals("WS")) {
            return  plugin().playerData.getConfig().getString(getUUID(p) + ".WS-Owned");
        }else if(category.equals("ShopKeeper")){
            return plugin().playerData.getConfig().getString(getUUID(p) + ".ShopKeeper-Owned");
        }else if(category.equals("PT")){
            return plugin().playerData.getConfig().getString(getUUID(p) + ".PT-Owned");
        }else if(category.equals("KM")){
            return plugin().playerData.getConfig().getString(getUUID(p) + ".KM-Owned");
        }else if(category.equals("GLY")){
            return plugin().playerData.getConfig().getString(getUUID(p) + ".GLY-Owned");
        }else if(category.equals("FinalKill")){
            return plugin().playerData.getConfig().getString(getUUID(p) + ".FinalKill-Owned");
        } else if (category.equals("DC")) {
            return plugin().playerData.getConfig().getString(getUUID(p) + ".DC-Owned");
        } else if (category.equals("BBE")) {
            return plugin().playerData.getConfig().getString(getUUID(p) + ".BBE-Owned");
        }else if (category.equals("Spray")) {
            return plugin().playerData.getConfig().getString(getUUID(p) + ".Spray-Owned");
        }else if(category.equals("Island")){
            return plugin().playerData.getConfig().getString(getUUID(p) + ".IslandTopper-Owned");
        }
        return null;
    }
    public static List<String> bbe = new ArrayList<>();
    public static List<String> dc = new ArrayList<>();
    public static List<String> finalkill = new ArrayList<>();
    public static List<String> gly = new ArrayList<>();
    public static List<String> km = new ArrayList<>();
    public static List<String> pt = new ArrayList<>();
    public static List<String> shopkeeper = new ArrayList<>();
    public static List<String> spray = new ArrayList<>();
    public static List<String> vd = new ArrayList<>();
    public static List<String> ws = new ArrayList<>();
    public static List<String> island = new ArrayList<>();

    public static void addToList(){
        for(String name : util.plugin().bbedata.getConfig().getConfigurationSection("BedBreakEffects").getKeys(false)) {
            bbe.add(name);
            }

        for(String name : util.plugin().dcdata.getConfig().getConfigurationSection("DeathCries").getKeys(false)) {
            dc.add(name);
            }

        for(String name : util.plugin().finalkilldata.getConfig().getConfigurationSection("FinalKillEffects").getKeys(false)) {
            finalkill.add(name);
        }

        for(String name : util.plugin().glyconf.getConfig().getConfigurationSection("Glyphs").getKeys(false)) {
            gly.add(name);
        }

        for(String name : util.plugin().killmessagecfg.getConfig().getConfigurationSection("KillMessages").getKeys(false)) {
            km.add(name);
        }

        for(String name : util.plugin().pedata.getConfig().getConfigurationSection("ProjectileTrails").getKeys(false)) {
            pt.add(name);
        }

        for(String name : util.plugin().shopkeeperdata.getConfig().getConfigurationSection("ShopKeeperSkins").getKeys(false)) {
            shopkeeper.add(name);
        }

        for(String name : util.plugin().spraysdata.getConfig().getConfigurationSection("Sprays").getKeys(false)) {
            spray.add(name);
        }

        for(String name : util.plugin().vdconf.getConfig().getConfigurationSection("VictoryDances").getKeys(false)) {
            vd.add(name);
        }

        for(String name : util.plugin().woodskindata.getConfig().getConfigurationSection("WoodSkins").getKeys(false)) {
            ws.add(name);
        }
        for(String name : util.plugin().islandToppersData.getConfig().getConfigurationSection("IslandTopper").getKeys(false)) {
            island.add(name);
        }
        }


        public static void savePlayerData(Player p, String path,Object data){
            plugin().playerData.getConfig().set(getUUID(p) + "." + path, data);
            plugin().playerData.getConfig().options().copyDefaults(true);
            plugin().playerData.savecfg();
        }

        public static List<String> formatLore(List<String> lores, String name, String status, int price){
        List<String> lore = new ArrayList<>();
            for(int i = 0; i < lores.size(); i++){
                if(lores.get(i).contains("{status}")){
                    lores.set(i, lores.get(i).replace("{status}", status));
                }
                if(lores.get(i).contains("{name}")){
                    lores.set(i, lores.get(i).replace("{name}", name.replace("-", " ")));
                }
                if(lores.get(i).contains("{cost}")){
                    lores.set(i, lores.get(i).replace("{cost}", new DecimalFormat().format(price)));
                }
            }
            for(String lored : lores){
                lore.add(lored);
            }
            return lore;
        }

        public static String getItemStatus(Player p, String dataPath, String perm, String name, int price){
            String selected = util.plugin().playerData.getConfig().getString(util.getUUID(p)  + "." + dataPath);

            if(selected.equals(name)){
                return ColorUtil.colored(util.getMSGLang(p, "cosmetics.selected"));
            }
            if(p.hasPermission(perm)){
                return ColorUtil.colored(util.getMSGLang(p, "cosmetics.click-to-select"));
            }
            if(new BwcAPI().getEco().getBalance(p) >= price){
                return ColorUtil.colored(util.getMSGLang(p, "cosmetics.click-to-purchase"));
            }
            return ColorUtil.colored(util.getMSGLang(p, "cosmetics.no-coins"));
        }

        public static void openMainMenu(Player p){
            new mainMenu().open(p);
        }



        public static void onClick(Player p, ItemStack item, String dataPath, HInventory inv, String perm, int price) {
            List<String> lore = item.getItemMeta().getLore();
            String name = ChatColor.stripColor(item.getItemMeta().getDisplayName()).replace(" ", "-");
            for (String str : lore) {
                if (str.contains("select.")) {
                    // if player needs to select the cosmetic
                    savePlayerData(p, dataPath, name);
                    p.playSound(p.getLocation(), util.villager_yes, 1.0f, 1.0f);
                    inv.open(p);
                } else if (str.contains("purchase.")) {
                    // if player needs to purchase the given cosmetic
                    // we will call the event to update owned
                    CosmeticPurchaseEvent event = new CosmeticPurchaseEvent(p, Cosmetics.valueOf(dataPath));
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled())
                        return;
                    // they bought the cosmetic and re-opened the GUI
                    String permission = perm + "." + ChatColor.stripColor(name.replace("-", "").replaceAll("\\s+", ""));
                    util.plugin().perms.playerAdd(p, permission);
                    savePlayerData(p, dataPath, name);
                    Economy econ = new BwcAPI().getEco();
                    econ.withdrawPlayer(p, price);
                    p.playSound(p.getLocation(), util.villager_yes, 1.0f, 1.0f);
                    HCore.getInventoryByPlayer(p).open(p);
                } else if (str.contains("don't")) {
                    // they don't have enough coins to buy the cosmetic
                    p.playSound(p.getLocation(), util.enderman_teleport, 1.0f, 1.0f);
                }
            }
        }


        public static void createPages(Pagination page, HInventory inv, String guiName) {
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
                        HInventory inv1 = new HInventory("page", title, 6, InventoryType.CHEST);
                        inv1.open((Player) e.getWhoClicked());
                            page.getPage(page.getPreviousPage()).getItems().forEach((slot, item) -> {
                                inv1.setItem(slot, item);
                        });
                        page.setCurrentPage(page.getPreviousPage());
                        if(page.isFirstPage()){
                            inv1.setItem(49, HCore.itemBuilder(Material.ARROW).name(true, "&aBack").build(), (clickEvent) -> {
                                util.openMainMenu((Player) clickEvent.getWhoClicked());
                            });
                        }
                        createPages(page, inv1, guiName);
                    });

                } else if (page.isFirstPage()) {
                    inv.setItem(53, HCore.itemBuilder(Material.ARROW).name(true, "&aNext page").build(), (e) -> {
                        int number = page.getNextPage() + 1;
                        HInventory inv1 = new HInventory("page", guiName + " Page " + number, 6, InventoryType.CHEST);
                        inv1.open((Player) e.getWhoClicked());
                        page.getPage(page.getNextPage()).getItems().forEach((slot, item) -> {
                            inv1.setItem(slot, item);
                        });
                        page.setCurrentPage(page.getNextPage());
                        createPages(page, inv1, guiName);
                    });
                }
                if (page.isFirstPage())
                    return;
                if(page.isLastPage())
                    return;

                inv.setItem(45, HCore.itemBuilder(Material.ARROW).name(true, "&aPrevious Page").build(), (e) -> {
                    int number = page.getPreviousPage() + 1;
                    String title;
                    if (page.getPreviousPage() == page.getFirstPage()) {
                        title = guiName + " Page " + number;
                    } else {
                        title = guiName;
                    }
                    HInventory inv1 = new HInventory("page", title, 6, InventoryType.CHEST);
                    inv1.open((Player) e.getWhoClicked());
                        page.getPage(page.getPreviousPage()).getItems().forEach((slot, item) -> {
                            inv1.setItem(slot, item);
                        });
                        page.setCurrentPage(page.getPreviousPage());
                    if(page.isFirstPage()) {
                        inv1.setItem(49, HCore.itemBuilder(Material.ARROW).name(true, "&aBack").build(), (clickEvent) -> {
                            util.openMainMenu((Player) clickEvent.getWhoClicked());
                        });
                    }
                    createPages(page, inv1, guiName);
                });


                inv.setItem(53, HCore.itemBuilder(Material.ARROW).name(true, "&aNext page").build(), (e) -> {
                    int number = page.getNextPage() + 1;
                    HInventory inv1 = new HInventory("page", guiName + " Page " + number, 6, InventoryType.CHEST);
                    inv1.whenOpened((event) -> {
                        page.getPage(page.getNextPage()).getItems().forEach((slot, item) -> {
                            inv1.setItem(slot, item);
                        });
                        page.setCurrentPage(page.getNextPage());
                        createPages(page, inv1, guiName);
                    });
                });
            }
        }


        public static String getMSGLang(Player p, String path){
            BwcAPI api = new BwcAPI();
            if(api.isProxy()){
                return  com.andrei1058.bedwars.proxy.language.Language.getMsg(p, path);
            }
            return Language.getMsg(p, path);
        }

        public static List<String> getListLang(Player p, String path){
            BwcAPI api = new BwcAPI();
            if(api.isProxy()){
               return BedWarsProxy.getAPI().getLanguageUtil().getList(p, path);
            }
            return Language.getList(p, path);
            }

       public static void saveIfNotExistsLang(String path, Object ob){
           BwcAPI api = new BwcAPI();
           if(api.isProxy()){
               BedWarsProxy.getAPI().getLanguageUtil().saveIfNotExists(path, ob);
               return;
           }
           Language.saveIfNotExists(path, ob);
       }

       public static boolean isInArena(Player p){
        BwcAPI api = new BwcAPI();
        if(api.isProxy())
            return false;
        if(api.getBwAPI().getArenaUtil().getArenaByPlayer(p) != null)
            return true;
        return false;
       }

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
