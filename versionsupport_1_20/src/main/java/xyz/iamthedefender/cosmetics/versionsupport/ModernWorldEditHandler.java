package xyz.iamthedefender.cosmetics.versionsupport;

import com.cryptomorin.xseries.XMaterial;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BlockState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import xyz.iamthedefender.cosmetics.api.handler.IWorldEditHandler;
import xyz.iamthedefender.cosmetics.api.util.BlockData;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ModernWorldEditHandler implements IWorldEditHandler {

    @Override
    public void pasteSchematic(File file, Location location, @Nullable BlockFace rotated) {
        com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(location.getWorld());

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(adaptedWorld)) {
            ClipboardFormat format = ClipboardFormats.findByFile(file);
            if (format == null) {
                Bukkit.getLogger().severe("Schematic format is null! most probably file is invalid! (" + file.getName() + ")");
                return;
            }

            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                Clipboard clipboard = reader.read();
                ClipboardHolder holder = new ClipboardHolder(clipboard);

                if (rotated != null) {
                    AffineTransform transform = getRotationTransform(rotated);
                    holder.setTransform(transform);
                }

                BlockVector3 to = BlockVector3.at(location.getX(), location.getY(), location.getZ());
                Operation operation = holder.createPaste(editSession).to(to).build();
                Operations.complete(operation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<Location, BlockData> extractBlockData(File file, Location location, World world, @Nullable BlockFace rotated) {
        try {
            ClipboardFormat format = ClipboardFormats.findByFile(file);
            if (format == null) {
                Bukkit.getLogger().severe("Invalid schematic format for file: " + file.getName());
                return new HashMap<>();
            }

            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                Clipboard clipboard = reader.read();

                if (rotated != null) {
                    AffineTransform transform = getRotationTransform(rotated);
                    clipboard = clipboard.transform(transform);
                }

                Map<Location, BlockData> blockLocations = new LinkedHashMap<>();
                BlockVector3 origin = clipboard.getOrigin();
                BlockVector3 offset = BlockVector3.at(location.getX(), location.getY(), location.getZ());

                for (BlockVector3 point : clipboard.getRegion()) {
                    BlockState blockState = clipboard.getBlock(point);

                    if (blockState.getBlockType().getMaterial().isAir()) continue;

                    Optional<XMaterial> xMaterial = getXMaterial(blockState);
                    if (xMaterial.isPresent()) {
                        BlockVector3 relative = point.subtract(origin);
                        BlockVector3 worldPos = offset.add(relative);
                        Location targetLoc = new Location(world, worldPos.x(), worldPos.y(), worldPos.z());
                        BlockData blockData = new BlockData(xMaterial.get().parseMaterial(), (byte) 0);
                        blockLocations.put(targetLoc, blockData);
                    }
                }

                return blockLocations;
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Error loading schematic file " + file.getName() + ": " + e.getMessage());
            return new HashMap<>();
        }
    }

    @Override
    public void pasteSchematic(File file, Location location, Player player) {
        pasteSchematic(file, location, (BlockFace) null);
    }

    private static Optional<XMaterial> getXMaterial(BlockState blockState) {
        try {
            Material bukkitMaterial = BukkitAdapter.adapt(blockState.getBlockType());
            return Optional.of(XMaterial.matchXMaterial(bukkitMaterial));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static AffineTransform getRotationTransform(BlockFace direction) {
        AffineTransform transform = new AffineTransform();
        switch (direction) {
            case NORTH:
                return transform.rotateY(180);
            case EAST:
                return transform.rotateY(270);
            case WEST:
                return transform.rotateY(90);
            default:
                return transform;
        }
    }
}
