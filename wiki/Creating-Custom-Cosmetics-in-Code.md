# Creating Custom Cosmetics in Code
If the category is code-driven, config is not enough. You need to write a class, extend the correct category type, and register it.

## Categories that need code
- Bed Break Effects
- Final Kill Effects
- Victory Dances
- Wood Skins

You can also write code for the other categories if you want more control than the config path gives you.

## Basic pattern
Every cosmetic type extends `Cosmetics` through a category-specific abstract class.

The general pattern is:

1. create a class extending the right category base class
2. implement the required methods
3. call `register()` in your plugin startup

## Example: custom Final Kill Effect
```java
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.FinalKillEffect;

import java.util.List;

public class MyExplosionEffect extends FinalKillEffect {

    @Override
    public ItemStack getItem() {
        return XMaterial.TNT.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "my-explosion";
    }

    @Override
    public String getDisplayName() {
        return "My Explosion";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7A simple custom final kill effect.");
    }

    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.COMMON;
    }

    @Override
    public void execute(Player killer, Player victim, Location location, boolean onlyVictim) {
        location.getWorld().createExplosion(location, 0F, false, false);
    }
}
```

Register it on `onEnable()` or anytime after the BedWars-Cosmetics has been enabled and loaded.

```java
new MyExplosionEffect().register();
```

## Example: custom Bed Break Effect
```java
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.BedDestroy;
import xyz.iamthedefender.cosmetics.api.handler.ITeamHandler;

import java.util.List;

public class MyBedEffect extends BedDestroy {

    @Override
    public ItemStack getItem() {
        return XMaterial.BEDROCK.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "my-bed-effect";
    }

    @Override
    public String getDisplayName() {
        return "My Bed Effect";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Does a thing when a bed gets broken.");
    }

    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.COMMON;
    }

    @Override
    public void execute(Player player, Location bedLocation, ITeamHandler victimTeam) {
        bedLocation.getWorld().strikeLightningEffect(bedLocation);
    }
}
```

## Example: custom Victory Dance
```java
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;

import java.util.List;

public class MyVictoryDance extends VictoryDance {

    @Override
    public ItemStack getItem() {
        return XMaterial.FIREWORK_ROCKET.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "my-victory-dance";
    }

    @Override
    public String getDisplayName() {
        return "My Victory Dance";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7Launches fireworks around the winner.");
    }

    @Override
    public int getPrice() {
        return 10000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.RARE;
    }

    @Override
    public void execute(Player winner) {
        BukkitTask task = Run.every(() -> {
            Firework fw = winner.getWorld().spawn(winner.getLocation(), Firework.class);
            addEntity(winner, fw);
        }, 20L);

        addTask(winner, task);
    }
}
```

For victory dances, use `addTask` and `addEntity` so the base class can stop and clean things up properly.

## Registering from your own plugin
Example:

```java
public final class MyAddon extends JavaPlugin {
    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("BedWars-Cosmetics") == null) {
            getLogger().severe("BedWars Cosmetics is not installed.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        new MyExplosionEffect().register();
        new MyBedEffect().register();
    }
}
```

## What registration does
Calling `register()` will generally:

- seed missing config values
- seed missing language defaults
- add the cosmetic to the relevant runtime list

## Preview support
If your coded cosmetic needs preview support and the category already uses previews, you may also need to provide a preview implementation depending on what you are adding.

Existing preview handlers extend:

```java
CosmeticPreview
```

If you skip preview support for a category where players expect previews, the plugin may have errors / issues.

## Don'ts
- do not hardcode NMS unless there is no other sane option
- do not put names and lore into config instead of language
- do not leak spawned entities or repeating tasks
- do not use invalid identifiers with spaces and weird casing
