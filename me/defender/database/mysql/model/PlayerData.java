// 
// @author IamTheDefender
// 

package me.defender.database.mysql.model;

public class PlayerData
{
    private String name;
    private String deathcries;
    private String sprays;
    private String shopkeeperskins;
    private String glyphs;
    private String victorydances;
    private String killmessages;
    private String woodskins;
    
    public PlayerData(final String name, final String deathcries, final String sprays, final String shopkeeperskins, final String glyphs, final String victorydances, final String killmessages, final String woodskins) {
        this.name = name;
        this.deathcries = deathcries;
        this.sprays = sprays;
        this.shopkeeperskins = shopkeeperskins;
        this.glyphs = glyphs;
        this.victorydances = victorydances;
        this.killmessages = killmessages;
        this.woodskins = woodskins;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDeathcries() {
        return this.deathcries;
    }
    
    public void setDeathcries(final String deathcries) {
        this.deathcries = deathcries;
    }
    
    public String getSprays() {
        return this.sprays;
    }
    
    public void setSprays(final String sprays) {
        this.sprays = sprays;
    }
    
    public String getShopkeeperskins() {
        return this.shopkeeperskins;
    }
    
    public void setShopkeeperskins(final String shopkeeperskins) {
        this.shopkeeperskins = shopkeeperskins;
    }
    
    public String getGlyphs() {
        return this.glyphs;
    }
    
    public void setGlyphs(final String glyphs) {
        this.glyphs = glyphs;
    }
    
    public String getVictorydances() {
        return this.victorydances;
    }
    
    public void setVictorydances(final String victorydances) {
        this.victorydances = victorydances;
    }
    
    public String getKillmessages() {
        return this.killmessages;
    }
    
    public void setKillmessages(final String killmessages) {
        this.killmessages = killmessages;
    }
    
    public String getWoodskins() {
        return this.woodskins;
    }
    
    public void setWoodskins(final String woodskins) {
        this.woodskins = woodskins;
    }
}
