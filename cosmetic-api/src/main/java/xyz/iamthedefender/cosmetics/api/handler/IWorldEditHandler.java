package xyz.iamthedefender.cosmetics.api.handler;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import xyz.iamthedefender.cosmetics.api.util.BlockData;

import java.io.File;
import java.util.Map;

public interface IWorldEditHandler {

    void pasteSchematic(File file, Location location, @Nullable BlockFace rotated);

    Map<Location, BlockData> extractBlockData(File file, Location location, World world, @Nullable BlockFace rotated);

    default void pasteSchematic(File file, Location location, Player player) {
        pasteSchematic(file, location, (BlockFace) null);
    }

    default void pasteSchematic(File file, Location location) {
        pasteSchematic(file, location, (BlockFace) null);
    }
}
