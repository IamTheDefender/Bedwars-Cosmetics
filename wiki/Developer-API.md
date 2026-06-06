# Developer API
This page is for developers who want to integrate with BedWars Cosmetics from another plugin.

## Java version
The project targets Java 11 or above. Please note, this is due to the
requirement of the supported BedWars plugins.

## Setting your plugin up
At minimum, your plugin should soft-depend or depend on BedWars Cosmetics.
As to avoid issues with race conditions.

### `plugin.yml`
> Use either one only!
```yml
softdepend:
  - BedWars-Cosmetics
```
```yml
depend:
  - BedWars-Cosmetics
```

### Gradle setup
The BedWars-Cosmetics API is provided via JitPack. 
You can find the respective configuration for your pick of 
toolkit at [JitPack](https://jitpack.io/#IamTheDefender/Bedwars-Cosmetics).


## Main API entry
The main API interface is:

```java
xyz.iamthedefender.cosmetics.api.CosmeticsAPI
```

You can get it from Bukkit services:

```java
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import xyz.iamthedefender.cosmetics.api.CosmeticsAPI;

RegisteredServiceProvider<CosmeticsAPI> rsp =
        Bukkit.getServicesManager().getRegistration(CosmeticsAPI.class);

CosmeticsAPI api = rsp != null ? rsp.getProvider() : null;
```

If `api` is `null`, the plugin is not loaded or not registered yet.

You should usually do that lookup in `onEnable()` after dependency checks, and cache the result.

## What the API gives you
Main things you can do:

- read the selected cosmetic for a player
- set the selected cosmetic for a player
- access category lists
- access the menu data
- access the plugin instance
- access the current handler
- access version support
- access the preview registry
- access the GUI manager
- access the active database implementation
- register new cosmetics

## Database backends
The plugin now supports these backend types:

- `SQLITE`
- `MYSQL`
- `MARIADB`
- `POSTGRESQL`
- `MONGODB`

The active backend can be inspected through:

```java
api.getDatabase().getDatabaseType();
api.getDatabase().getDisplayName();
```

Do not assume the backend is JDBC anymore. Make sure to check type if you
are dealing with storage-related addon.

## Reading the selected cosmetic
```java
String selected = api.getSelectedCosmetic(player, CosmeticType.SPRAYS);
```

Useful categories are in:

```java
CosmeticType
```

Examples:

- `CosmeticType.SPRAYS`
- `CosmeticType.KILL_MESSAGES`
- `CosmeticType.FINAL_KILL_EFFECTS`

> Important note: CosmeticType is no longer an enum class. It is now a normal Java class with static values.

## Setting the selected cosmetic
```java
api.setSelectedCosmetic(player, CosmeticType.KILL_MESSAGES, "frostbite");
```

This saves asynchronously through the plugin's player data flow.

Do note, this method does not check if the set cosmetic is valid,
make sure to validate the ID before setting the value for a player. 
As this may cause game-breaking issues. You may utilize the `CosmeticRegistry#cosmeticExist`.

## Accessing loaded cosmetics
Examples:

```java
api.getSprayList();
api.getDeathCryList();
api.getFinalKillList();
api.getShopKeeperSkinList();
```
###### (or you may use `CosmeticRegistry` as well)

These give you the registered cosmetic objects, not just strings.

## Reading cosmetic fields
Most cosmetic category classes expose a `getField` method.

Example:

```java
Spray spray = api.getSprayList().get(0);
String name = (String) spray.getField(FieldsType.NAME, player);
```

Useful field types:

- `FieldsType.NAME`
- `FieldsType.LORE`
- `FieldsType.PRICE`
- `FieldsType.RARITY`
- `FieldsType.ITEM_STACK`
- `FieldsType.FILE`
- `FieldsType.URL`
- `FieldsType.ENTITY_TYPE`
- `FieldsType.SKIN_VALUE`
- `FieldsType.SKIN_SIGN`
- `FieldsType.MIRROR`

## Example: check if player uses a specific cosmetic
```java
boolean usingMySpray = api.getSelectedCosmetic(player, CosmeticType.SPRAYS).equals("my-logo");
);
```

## Example: grant a default selection on join
```java
if (api.getSelectedCosmetic(player, CosmeticType.DEATH_CRIES) == null) {
    api.setSelectedCosmetic(player, CosmeticType.DEATH_CRIES, "none");
}
```

## Events you can listen to
Available API events include:

- `CosmeticPurchaseEvent`
- `BedBreakEffectExecuteEvent`
- `FinalKillEffectsExecuteEvent`
- `VictoryDancesExecuteEvent`

### Purchase event example
```java
@EventHandler
public void onPurchase(CosmeticPurchaseEvent event) {
    if (event.getCategory() == CosmeticType.SPRAYS) {
        event.getPlayer().sendMessage("You bought a spray.");
    }
}
```

### Cancel a purchase
```java
@EventHandler
public void onPurchase(CosmeticPurchaseEvent event) {
    if (event.getCategory() == CosmeticType.FINAL_KILL_EFFECTS) {
        event.setCancelled(true);
    }
}
```

## GUI system
The API also exposes a small GUI framework:

- `SystemGui`
- `SystemGuiManager`
- `ClickableItem`

You can access the manager through:

```java
SystemGuiManager manager = api.getSystemGuiManager();
```

This is mainly useful if you are building custom UI behavior around the plugin.

## Preview system
Preview handlers are tracked in:

```java
api.getPreviewList()
```

Custom preview handlers extend:

```java
CosmeticPreview
```

That is only relevant if you are adding new coded cosmetics and want proper preview behaviour.

## Version support
Current version-specific abstraction is exposed through:

```java
api.getVersionSupport()
```

Use that when you need skull generation or version aware rendering helpers instead of hardcoding server-specific methods.
Need additional version specific methods? You may open a PR or suggest them to me
on any platform.

## Menu data and config access
You can access the main menu config manager with:

```java
ConfigManager menuData = api.getMenuData();
```

The API module also exposes `ConfigUtils` and `ConfigType`, but if you use them from an external plugin.
