// 
// @author IamTheDefender
// 

package me.defender.cosmetics.IslandToppers;

import java.io.IOException;

import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.world.registry.WorldData;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.mask.ExistingBlockMask;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.WorldEdit;

import java.io.FileInputStream;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.Vector;
import java.io.File;

import me.defender.Main;
import me.defender.api.utils.util;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class IslandToppersUtil
{
    public static void sendIslandTopper(World world, int x, int y, int z, Player p, File file) throws MaxChangedBlocksException, IOException {
        Main plugin = util.plugin();
        Vector to = new Vector(x, y, z);
        com.sk89q.worldedit.world.World weWorld = new BukkitWorld(world);
        WorldData worldData = weWorld.getWorldData();
        Clipboard clipboard = ClipboardFormat.SCHEMATIC.getReader(new FileInputStream(file)).read(worldData);
        EditSession extent = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, -1);
        AffineTransform transform = new AffineTransform();
        ForwardExtentCopy copy = new ForwardExtentCopy(clipboard, clipboard.getRegion(), clipboard.getOrigin(), extent, to);

        if (!transform.isIdentity()) {
            copy.setTransform(transform);
        }
        copy.setSourceMask(new ExistingBlockMask(clipboard));
        Operations.completeLegacy(copy);

        extent.flushQueue();
    }
}
