package xyz.iamthedefender.cosmetics.data.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonInt32;
import org.bson.Document;
import xyz.iamthedefender.cosmetics.api.database.DatabaseType;
import xyz.iamthedefender.cosmetics.api.database.IDatabase;
import xyz.iamthedefender.cosmetics.api.database.PlayerCosmeticsData;
import xyz.iamthedefender.cosmetics.api.database.PlayerOwnedCosmeticsData;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.UUID;

public class MongoDB implements IDatabase {

    private MongoClient client;
    private MongoDatabase database;

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MONGODB;
    }

    @Override
    public String getDisplayName() {
        return "MongoDB";
    }

    @Override
    public void connect() {
        String uri = StartupUtils.getString("database.mongodb.uri", null, "mongodb://localhost:27017");
        String dbName = StartupUtils.getString("database.mongodb.database", null, "bwcosmetics");
        ConnectionString connectionString = new ConnectionString(uri);
        client = MongoClients.create(MongoClientSettings.builder().applyConnectionString(connectionString).build());
        database = client.getDatabase(dbName);
    }

    @Override
    public void createTable() {
        if (database == null) {
            return;
        }
        try {
            database.createCollection("cosmetics_player_data");
        } catch (Exception ignored) {
        }
        try {
            database.createCollection("player_owned_data");
        } catch (Exception ignored) {
        }
    }

    @Override
    public void validateConnection() {
        if (database == null) {
            connect();
            return;
        }
        database.runCommand(new Document("ping", new BsonInt32(1)));
    }

    @Override
    public void close() {
        if (client != null) {
            client.close();
        }
    }

    private MongoCollection<Document> playerCollection() {
        return database.getCollection("cosmetics_player_data");
    }

    private MongoCollection<Document> ownedCollection() {
        return database.getCollection("player_owned_data");
    }

    @Override
    public PlayerCosmeticsData loadPlayerData(UUID uuid) {
        Document document = playerCollection().find(new Document("uuid", uuid.toString())).first();
        if (document == null) {
            return null;
        }
        return new PlayerCosmeticsData(
                document.getString("wood_skin"),
                document.getString("bed_destroy"),
                document.getString("victory_dance"),
                document.getString("shopkeeper_skin"),
                document.getString("glyph"),
                document.getString("spray"),
                document.getString("projectile_trail"),
                document.getString("kill_message"),
                document.getString("final_kill_effect"),
                document.getString("island_topper"),
                document.getString("death_cry")
        );
    }

    @Override
    public void createPlayerData(UUID uuid, PlayerCosmeticsData data) {
        playerCollection().insertOne(playerDocument(uuid, data));
    }

    @Override
    public void savePlayerData(UUID uuid, PlayerCosmeticsData data) {
        playerCollection().replaceOne(new Document("uuid", uuid.toString()), playerDocument(uuid, data));
    }

    @Override
    public PlayerOwnedCosmeticsData loadOwnedData(UUID uuid) {
        Document document = ownedCollection().find(new Document("uuid", uuid.toString())).first();
        if (document == null) {
            return null;
        }
        return new PlayerOwnedCosmeticsData(
                document.getInteger("bed_destroy", 0),
                document.getInteger("death_cry", 0),
                document.getInteger("final_kill_effect", 0),
                document.getInteger("glyph", 0),
                document.getInteger("island_topper", 0),
                document.getInteger("kill_message", 0),
                document.getInteger("projectile_trail", 0),
                document.getInteger("shopkeeper_skin", 0),
                document.getInteger("spray", 0),
                document.getInteger("victory_dance", 0),
                document.getInteger("wood_skin", 0)
        );
    }

    @Override
    public void createOwnedData(UUID uuid, PlayerOwnedCosmeticsData data) {
        ownedCollection().insertOne(ownedDocument(uuid, data));
    }

    @Override
    public void saveOwnedData(UUID uuid, PlayerOwnedCosmeticsData data) {
        ownedCollection().replaceOne(new Document("uuid", uuid.toString()), ownedDocument(uuid, data));
    }

    private Document playerDocument(UUID uuid, PlayerCosmeticsData data) {
        return new Document("uuid", uuid.toString())
                .append("bed_destroy", data.getBedDestroy())
                .append("wood_skin", data.getWoodSkin())
                .append("victory_dance", data.getVictoryDance())
                .append("shopkeeper_skin", data.getShopkeeperSkin())
                .append("glyph", data.getGlyph())
                .append("spray", data.getSpray())
                .append("projectile_trail", data.getProjectileTrail())
                .append("kill_message", data.getKillMessage())
                .append("final_kill_effect", data.getFinalKillEffect())
                .append("island_topper", data.getIslandTopper())
                .append("death_cry", data.getDeathCry());
    }

    private Document ownedDocument(UUID uuid, PlayerOwnedCosmeticsData data) {
        return new Document("uuid", uuid.toString())
                .append("bed_destroy", data.getBedDestroy())
                .append("death_cry", data.getDeathCry())
                .append("final_kill_effect", data.getFinalKillEffect())
                .append("glyph", data.getGlyph())
                .append("island_topper", data.getIslandTopper())
                .append("kill_message", data.getKillMessage())
                .append("projectile_trail", data.getProjectileTrail())
                .append("shopkeeper_skin", data.getShopkeeperSkin())
                .append("spray", data.getSpray())
                .append("victory_dance", data.getVictoryDance())
                .append("wood_skin", data.getWoodSkin());
    }
}
