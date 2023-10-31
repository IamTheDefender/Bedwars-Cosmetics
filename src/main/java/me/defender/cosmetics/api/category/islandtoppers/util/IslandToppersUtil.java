

package me.defender.cosmetics.api.category.islandtoppers.util;

import com.andrei1058.bedwars.api.arena.IArena;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.SchematicFormat;
import me.defender.cosmetics.api.util.DebugUtil;
import me.defender.cosmetics.api.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.material.Bed;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;

import java.io.File;
import java.io.IOException;

public class IslandToppersUtil
{
    public static void sendIslandTopper(World world, Location loc, Player p, File file) throws MaxChangedBlocksException, IOException {
        com.sk89q.worldedit.world.World world1 = new BukkitWorld(world);
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world1, -1);
        try {
            // Load the schematic from the file
            SchematicFormat schematicFormat = SchematicFormat.getFormat(file);
            if(schematicFormat == null){
                Bukkit.getLogger().severe("Schematic format is null! most probably file is invalid! (" + file.getName() + ")");
                return;
            }
            if (!Utility.plugin().isBw2023()){
                IArena arena = Utility.plugin().getBedWars1058API().getArenaUtil().getArenaByPlayer(p);
                if(arena != null) {
                    Block block = arena.getTeam(p).getBed().getBlock();
                    if (block.getType() == Material.BED_BLOCK){
                        if (block.getState().getData() instanceof Directional){
                            Directional directional = (Directional) block.getState().getData();
                            CuboidClipboard clipboard = schematicFormat.load(file);
                            clipboard = flipDirection(clipboard, directional.getFacing());
                            // Move the schematic to the target location
                            clipboard.setOrigin(new Vector(loc.getX(), loc.getY(), loc.getZ()));
                            // Paste the schematic
                            clipboard.paste(editSession, new Vector(loc.getX(), loc.getY(), loc.getZ()), true);
                        }
                    }
                }
            } else {
                com.tomkeuper.bedwars.api.arena.IArena arena = Utility.plugin().getBedWars2023API().getArenaUtil().getArenaByPlayer(p);
                if(arena != null) {
                    Block block = arena.getTeam(p).getBed().getBlock();
                    if (block.getType() == Material.BED_BLOCK){
                        if (block.getState().getData() instanceof Directional){
                            Directional directional = (Directional) block.getState().getData();
                            CuboidClipboard clipboard = schematicFormat.load(file);
                            clipboard = flipDirection(clipboard, directional.getFacing());
                            // Move the schematic to the target location
                            clipboard.setOrigin(new Vector(loc.getX(), loc.getY(), loc.getZ()));
                            // Paste the schematic
                            clipboard.paste(editSession, new Vector(loc.getX(), loc.getY(), loc.getZ()), true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static CuboidClipboard flipDirection(CuboidClipboard clipboard, BlockFace direction) {
        return switch (direction) {
            case NORTH -> {
                clipboard.rotate2D(180);
                yield clipboard;
            }
            case EAST -> {
                clipboard.rotate2D(270);
                yield clipboard;
            }
            case WEST -> {
                clipboard.rotate2D(90);
                yield clipboard;
            }
            default -> clipboard;
        };
    }
}