package xyz.iamthedefender.cosmetics.versionsupport;

import com.cryptomorin.xseries.XMaterial;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.SchematicFormat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.Nullable;
import xyz.iamthedefender.cosmetics.api.handler.IWorldEditHandler;
import xyz.iamthedefender.cosmetics.api.util.BlockData;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class LegacyWorldEditHandler implements IWorldEditHandler {

    @Override
    public void pasteSchematic(File file, Location location, @Nullable BlockFace rotated) {
        com.sk89q.worldedit.world.World bukkitWorld = new BukkitWorld(location.getWorld());
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(bukkitWorld, -1);

        try {
            SchematicFormat schematicFormat = SchematicFormat.getFormat(file);
            if (schematicFormat == null) {
                Bukkit.getLogger().severe("Schematic format is null! most probably file is invalid! (" + file.getName() + ")");
                return;
            }

            CuboidClipboard clipboard = schematicFormat.load(file);

            if (rotated != null) {
                flipDirection(clipboard, rotated);
            }

            clipboard.setOrigin(new Vector(location.getX(), location.getY(), location.getZ()));

            clipboard.paste(editSession, new Vector(location.getX(), location.getY(), location.getZ()), true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<Location, BlockData> extractBlockData(File file, Location location, World world, @Nullable BlockFace rotated) {
        try {
            SchematicFormat schematicFormat = SchematicFormat.getFormat(file);
            if (schematicFormat == null) {
                Bukkit.getLogger().severe("Invalid schematic format for file: " + file.getName());
                return null;
            }
            CuboidClipboard clipboard = schematicFormat.load(file);

            Vector origin = new Vector(location.getX(), location.getY(), location.getZ());
            origin = origin.add(0, clipboard.getHeight() + 1, 0);
            origin = origin.add(clipboard.getOffset());

            if (rotated != null) {
                flipDirection(clipboard, rotated);
            }

            Map<Location, BlockData> blockLocations = new LinkedHashMap<>();

            for (int x = 0; x < clipboard.getSize().getBlockX(); x++) {
                for (int y = 0; y < clipboard.getSize().getBlockY(); y++) {
                    for (int z = 0; z < clipboard.getSize().getBlockZ(); z++) {
                        BaseBlock baseBlock = clipboard.getBlock(new Vector(x, y, z));

                        if (baseBlock.isAir()) continue;

                        Optional<XMaterial> xMaterial = getXMaterial(baseBlock);
                        if (xMaterial.isPresent()) {
                            Vector targetVec = new Vector(x, y, z).add(origin);
                            Location targetLoc = new Location(world, targetVec.getX(), targetVec.getY(), targetVec.getZ());
                            BlockData blockData = new BlockData(xMaterial.get().parseMaterial(), xMaterial.get().getData());
                            blockLocations.put(targetLoc, blockData);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Error loading schematic file " + file.getName() + ": " + e.getMessage());
            return new HashMap<>();
        }
        return Map.of();
    }

    @Override
    public void pasteSchematic(File file, Location location, Player player) {

    }

    private static Optional<XMaterial> getXMaterial(BaseBlock baseBlock) {
        try {
            return XMaterial.matchXMaterial(Material.getMaterial(baseBlock.getId()).name());
        } catch (Exception e) {
            return Optional.of(XMaterial.matchXMaterial(new MaterialData(baseBlock.getId(), (byte) baseBlock.getData()).getItemType()));
        }
    }

    private static void flipDirection(CuboidClipboard clipboard, BlockFace direction) {
        switch (direction) {
            case NORTH:
                clipboard.rotate2D(180);
                break;
            case EAST:
                clipboard.rotate2D(270);
                break;
            case WEST:
                clipboard.rotate2D(90);
                break;
        }
    }
}

