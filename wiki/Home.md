# BedWars Cosmetics Wiki
BedWars Cosmetics is a BedWars addon that adds a full cosmetics system in one plugin:

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

It also gives you:

- GUI menus
- previews
- economy purchases through Vault
- permission based unlocks
- multiple storage backends
- language-file based user text
- an API you can use from your own plugin

For server owners, you can follow the instructions below:

1. [Installation and First Setup](Installation-and-First-Setup)
2. [Configuration Reference](Configuration-Reference)
3. [Commands and Permissions](Commands-and-Permissions)

For server owners looking to customize the plugin, look here:

1. [Cosmetic Categories](Cosmetic-Categories)
2. [Adding Cosmetics from Config](Adding-Cosmetics-from-Config)
3. [Language Files and Messages](Language-Files-and-Messages)

If you are a developer and want to build on top of the plugin:

1. [Developer API](Developer-API)
2. [Creating Custom Cosmetics in Code](Creating-Custom-Cosmetics-in-Code)
3. [Libraries and Dependencies](Libraries-and-Dependencies)
4. [Contributing](Contributing)

If something is broken, read [Troubleshooting](Troubleshooting) before opening an issue or making a support ticket.

## What this plugin supports
Supported BedWars targets:

- BedWars1058
- BedWars2023
- BedWarsProxy / BWProxy2023
- Screaming BedWars
- More to come; have a suggestion? let me know.

If none of those are installed, the plugin disables itself.

## Main rules before you do anything
- Do not put user-facing text in config files. Messages, names and lore belong in language files.
- Do not use SQLite in proxy mode.
- Do not assume storage is only MySQL now. Read the storage section and set `database.type` correctly.
- Do not skip preview setup and then complain that previews do not work.
- Do not skip the wiki and then ask why a category that needs assets has no assets.

## Project links
- Main repo: [GitHub](https://github.com/IamTheDefender/BW1058-Cosmetics)
- Support server: [Discord](https://discord.iamthedefender.xyz)
- Author site: [iamthedefender.xyz](https://iamthedefender.xyz)
- License: [GPL-3.0](https://github.com/IamTheDefender/BW1058-Cosmetics/blob/main/LICENSE)
