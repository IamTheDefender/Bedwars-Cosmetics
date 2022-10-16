package me.defender.support.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.defender.api.BwcAPI;
import me.defender.Main;
import me.defender.api.utils.util;
import me.defender.api.enums.Cosmetics;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {
    private final Main plugin;

    public Placeholders(Main plugin) {
        this.plugin = plugin;
    }


    @Override
    public String getIdentifier() {
        return "bwc";
    }

    @Override
    public String getAuthor() {
        return "IamTheDefender";
    }

    @Override
    public String getVersion() {
        return "1.1";
    }

    @Override
    public boolean persist() {
        return true;
    }



    public String onPlaceholderRequest(Player p, String params) {
        BwcAPI api = new BwcAPI();
        if(params.equalsIgnoreCase("selected_dc")){
            return api.getSelectedCosmetic(p, Cosmetics.DeathCries);
        }

        if(params.equalsIgnoreCase("selected_vd")) {
           return api.getSelectedCosmetic(p, Cosmetics.VictoryDances);
        }


        if(params.equalsIgnoreCase("selected_spray")) {
            return api.getSelectedCosmetic(p, Cosmetics.Sprays);
        }

        if(params.equalsIgnoreCase("selected_km")) {
            return api.getSelectedCosmetic(p, Cosmetics.KillMessage);
        }

        if(params.equalsIgnoreCase("selected_skin")) {
            return api.getSelectedCosmetic(p, Cosmetics.ShopKeeperSkin);
        }


        if(params.equalsIgnoreCase("selected_ws")) {
            return api.getSelectedCosmetic(p, Cosmetics.WoodSkins);
        }

        if(params.equalsIgnoreCase("selected_glyph")) {
            return api.getSelectedCosmetic(p, Cosmetics.Glyphs);
        }

        if(params.equalsIgnoreCase("selected_bbe")) {
           return api.getSelectedCosmetic(p, Cosmetics.BedBreakEffects);
        }

        if(params.equalsIgnoreCase("selected_pt")) {
            return api.getSelectedCosmetic(p, Cosmetics.ProjectileTrails);
        }

        if(params.equalsIgnoreCase("selected_finalkill")) {
            return api.getSelectedCosmetic(p, Cosmetics.FinalKillEffects);
        }

        if(params.equalsIgnoreCase("owned_vd")){
            return util.getOwned(p, "VD");
        }

        if(params.equalsIgnoreCase("owned_ws")){
            return util.getOwned(p, "WS");
        }

        if(params.equalsIgnoreCase("owned_shopkeeper")){
            return util.getOwned(p, "ShopKeeper");
        }

        if(params.equalsIgnoreCase("owned_pt")){
            return util.getOwned(p, "PT");
        }

        if(params.equalsIgnoreCase("owned_km")){
            return util.getOwned(p, "KM");
        }

        if(params.equalsIgnoreCase("owned_gly")){
            return util.getOwned(p, "GLY");
        }

        if(params.equalsIgnoreCase("owned_finalkill")){
            return util.getOwned(p, "FinalKill");
        }

        if(params.equalsIgnoreCase("owned_dc")){
            return util.getOwned(p, "DC");
        }

        if(params.equalsIgnoreCase("owned_bbe")){
            return util.getOwned(p, "BBE");
        }

        if(params.equalsIgnoreCase("owned_spray")){
            return util.getOwned(p, "SPRAY");
        }

        if(params.equalsIgnoreCase("total_vd")){
            return util.vd.size() + "";
        }

        if(params.equalsIgnoreCase("total_bbe")){
            return util.bbe.size() + "";
        }
        if(params.equalsIgnoreCase("total_dc")){
            return util.dc.size() + "";
        }
        if(params.equalsIgnoreCase("total_finalkill")){
            return util.finalkill.size() + "";
        }
        if(params.equalsIgnoreCase("total_gly")){
            return util.gly.size() + "";
        }
        if(params.equalsIgnoreCase("total_km")){
            return util.km.size() + "";
        }
        if(params.equalsIgnoreCase("total_pt")){
            return util.pt.size() + "";
        }
        if(params.equalsIgnoreCase("total_shopkeeper")){
            return util.shopkeeper.size() + "";
        }
        if(params.equalsIgnoreCase("total_spray")){
            return util.spray.size() + "";
        }
        if(params.equalsIgnoreCase("total_ws")){
            return util.ws.size() + "";
        }


        return null;
    }
}
