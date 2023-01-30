

package me.defender.cosmetics.api.categories.islandtoppers.util;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.*;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

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
            CuboidClipboard clipboard = schematicFormat.load(file);
            // Move the schematic to the target location
            clipboard.setOrigin(new Vector(loc.getX(), loc.getY() + clipboard.getHeight() + 1, loc.getZ()));
            // Paste the schematic
            clipboard.paste(editSession, new Vector(loc.getX(), loc.getY() + clipboard.getHeight() + 1, loc.getZ()), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
