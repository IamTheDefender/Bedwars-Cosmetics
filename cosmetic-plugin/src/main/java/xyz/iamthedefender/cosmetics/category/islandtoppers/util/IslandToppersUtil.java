package xyz.iamthedefender.cosmetics.category.islandtoppers.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Directional;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.handler.IArenaHandler;

import java.io.File;
import java.io.IOException;

public class IslandToppersUtil {

    public static void sendIslandTopper(World world, Location loc, Player p, File file)  {
        try {
            IArenaHandler arena = CosmeticsPlugin.getInstance().getHandler().getArenaUtil().getArenaByPlayer(p);

            if (arena != null) {
                Block block = arena.getTeam(p).getBed().getBlock();

                if (block.getType() == Material.BED_BLOCK) {
                    if (block.getState().getData() instanceof Directional) {
                        Directional directional = (Directional) block.getState().getData();
                        CosmeticsPlugin.getInstance().getWorldEditHandler().pasteSchematic(
                                file,
                                loc,
                                directional.getFacing()
                        );
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}