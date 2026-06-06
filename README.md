# BedWars Cosmetics
This is an addon for BedWars servers that adds a full cosmetics system instead of just one or two random features stitched together. The plugin is currently maintained by [IamTheDefender](https://iamthedefender.xyz), it is open source, and it will stay that way. The project is licensed under [GPL-3.0](./LICENSE).

If you are running a BedWars server and want sprays, kill messages, final kill effects, projectile trails, shopkeeper skins, island toppers and the rest of it in one plugin, that is what this project is for.

## What this plugin does
This plugin adds all of the following cosmetic categories:

- Bed Break Effects
- Death Cries
- Final Kill Effects
- Glyphs
- Island Toppers
- Kill Messages
- Projectile Trails
- Shopkeeper Skins
- Sprays
- Victory Dances
- Wood Skins

It also includes:

- Main cosmetics menu
- Per-category GUIs
- Cosmetic previews
- Vault economy support for purchasing cosmetics
- Permission-based unlocking
- Database integration (MySQL, MariaDB, PostgreSQL, SQLite, Redis, MongoDB)
- Language file based messages, names and lore
- Support for multiple BedWars plugins

## Supported BedWars plugins
This addon is meant to work with:

- BedWars1058
- BedWars2023
- BedWarsProxy / BWProxy2023
- Screaming BedWars

If none of those are installed, this plugin will disable itself.

## Required dependencies
Before you install this, make sure you actually have the dependencies this addon needs. If you do not, the plugin will break or disable itself and there is nothing surprising about that.

Required in the current build:

- Vault
- PlaceholderAPI (Optional: for placeholders)
- PacketEvents (Auto-downloaded if not installed)
- WorldEdit or FastAsyncWorldEdit
- One supported BedWars plugin

Notes:

- Vault is used for economy and permission handling.
- WorldEdit or FAWE is required for Island Toppers.
- PlaceholderAPI is used for placeholder support.
- PacketEvents is used for entity and preview related packet handling and will be downloaded automatically if it is missing.

## Features
The point of this plugin is to keep the cosmetics system in one place instead of splitting it across several addons or forks. A server owner should be able to configure cosmetics, GUI layouts, prices and unlocks from the provided YAML files and language files without touching the source.

Current feature set includes:

- GUI based cosmetics browsing
- Sorting inside category menus
- Owned-first sorting toggle
- Preview support for supported cosmetic types
- Automatic permission grant when a player purchases a cosmetic
- Cosmetic selection persistence
- Preview location setup commands
- Team island topper position setup command
- Built-in default cosmetics and assets

## How unlocking works
Unlocking is permission based.

Each cosmetic category has its own permission prefix, for example:

- `beddestroy.<id>`
- `deathcry.<id>`
- `finalkilleffect.<id>`
- `glyph.<id>`
- `islandtopper.<id>`
- `killmessage.<id>`
- `projectiletrail.<id>`
- `shopkeeperskin.<id>`
- `spray.<id>`
- `victorydance.<id>`
- `woodskin.<id>`

The plugin checks permissions to decide what the player owns. If the cosmetic is purchaseable and the player has enough money, the plugin will attempt to grant the permission through Vault when they buy it.

If you are not using a permission plugin properly, do not expect purchases to behave properly either.

## Storage
The plugin supports:

- SQLite
- MySQL

SQLite is fine for regular Spigot/Paper setups.  
MySQL should be used if you want shared storage or you are running in proxy mode.

Proxy mode does not support SQLite. The plugin will refuse to continue with SQLite in Bungee mode.

## Configuration and file layout
The plugin uses multiple YAML files instead of stuffing everything into one file.

Main files:

- `config.yml` for general plugin settings, storage, feature toggles and preview locations
- `MainMenu.yml` for main menu structure, slots, items and actions
- Category files such as `DeathCries.yml`, `KillMessages.yml`, `Sprays.yml`, `VictoryDances.yml`, and the rest for category data

Important:

- Configuration files are for structure and behaviour.
- Messages, item names, lores and user-facing text belong in the language files.
- The plugin now migrates old text values into language files instead of keeping them in configuration files.

## Commands
Main aliases:

- `/bwc`
- `/bedwarscosmetics`
- `/cosmetics`
- `/cos`
- `/bwcos`
- `/bwcosmetic`
- `/bwcosmetics`

Available subcommands:

- `/bwc help`
- `/bwc reload`
- `/bwc menu`
- `/bwc km`
- `/bwc shopkeeper`
- `/bwc sprays`
- `/bwc dc`
- `/bwc glyphs`
- `/bwc bbe`
- `/bwc finalke`
- `/bwc pt`
- `/bwc vd`
- `/bwc ws`
- `/bwc it`
- `/bwc set <type> <cosmeticId> <player>`
- `/bwc setIslandTopperPosition <teamName>`
- `/bwc setupPlayerLocation`
- `/bwc setupPreviewLocation`

## Permissions
Command permissions:

- `bwcosmetics.help`
- `bwcosmetics.reload`
- `bwcosmetics.admin`

Cosmetic ownership permissions follow the category prefix format shown above.

Wildcard support depends on your permissions plugin, but the plugin also checks `<prefix>.*` in several places.

## Installation
1. Install one supported BedWars plugin.
2. Install Vault, PlaceholderAPI, and WorldEdit or FAWE.
3. Put the BedWars Cosmetics plugin jar in your `plugins` folder.
4. Start the server once. If PacketEvents is not already installed, BedWars Cosmetics will download and load the latest PacketEvents release automatically.
5. Configure `config.yml`, `MainMenu.yml`, and category files as needed.
6. Set preview positions using `/bwc setupPreviewLocation` and `/bwc setupPlayerLocation`.
7. If you use Island Toppers, set topper locations with `/bwc setIslandTopperPosition <teamName>`.
8. Reload the plugin with `/bwc reload` or restart the server.

## Building from source
This project uses Gradle and is split into multiple modules:

- `cosmetic-api`
- `cosmetic-plugin`
- `versionsupport_1_8_R3`
- `versionsupport_1_20`

Build with:

```bash
./gradlew build
```

## For developers
If you want to contribute, all contributions are welcome. It is better to improve the main project than to keep making random forks for every missing feature.

One request though: try not to use NMS unless it is absolutely necessary. The whole point of the version support split is to avoid turning the project into a maintenance mess.

## Documentation
A more detailed documentation / wiki is available here:

- [Wiki](https://dev-wiki.iamthedefender.xyz/bedwars-cosmetics)

Please read the wiki before opening an issue or asking for support. It saves both your time and mine.

## Support
If you have issues with the plugin, you can join the support server:

- [Discord Support Server](https://discord.iamthedefender.xyz)

Please check the wiki first. If the issue is already documented there, do not open a ticket just to ask the same thing again. If it is a real issue and you need help, then make a ticket and explain the problem properly.

Also, do not ping me. I check messages when I have the time.

## 3rd party libraries
This project uses, among others:

- [HikariCP](https://github.com/brettwooldridge/HikariCP)
- [XSeries](https://github.com/CryptoMorin/XSeries)

There are other libraries in the runtime setup as well, but these are the main ones people usually ask about.
