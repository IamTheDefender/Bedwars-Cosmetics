# Cosmetic Categories
This page mostly covers what and how each cosmetics category can and can't do.

## Category list
| Category | Config-addable | Needs external assets | Preview support | Notes |
| --- | --- | --- | --- | --- |
| Bed Break Effects | No | No | Yes | Code-based effect logic |
| Death Cries | Yes | No | Yes | Sound, pitch, volume |
| Final Kill Effects | No | No | Yes | Code-based effect logic |
| Glyphs | Yes | Yes | Yes | Needs image file |
| Island Toppers | Yes | Yes | Yes | Needs schematic file and team locations |
| Kill Messages | Yes | No | Yes | Message lists per kill type |
| Projectile Trails | Yes | No | Limited | API execute path is basically a stub for config-added items |
| Shopkeeper Skins | Yes | Sometimes | Yes | Can use entity type or skin texture/signature |
| Sprays | Yes | Yes | Yes | Needs file or downloadable URL |
| Victory Dances | No | No | No normal preview path | Code-based effect logic |
| Wood Skins | No | No | No | Code-based block replacement logic |

## What “config-addable” means here
Config-addable means you can add more entries by editing the category YAML file instead of writing a new Java class.

That does not mean every category can gain entirely new behaviour from config.

Example:

- a new Death Cry entry can be added from config because the logic is always “play this sound with this pitch and volume”
- a new Final Kill Effect cannot be added from config because the effect logic itself is code

## Bed Break Effects
- category key: `bed-destroy`
- config file: `BedBreakEffect.yml`
- extension mode: code

Use this category if you want to run custom logic when a bed is broken.

## Death Cries
- category key: `death-cry`
- config file: `DeathCries.yml`
- extension mode: config

Each entry defines:

- `item`
- `price`
- `rarity`
- `sound`
- `pitch`
- `volume`

## Final Kill Effects
- category key: `finalkill-effect`
- config file: `FinalKillEffects.yml`
- extension mode: code

Use this category for actual kill effect logic like lightning, particles, spawned entities, sounds and similar.

## Glyphs
- category key: `glyph`
- config file: `Glyphs.yml`
- extension mode: config

Each glyph uses an image file from the `Glyphs/` folder.

The default file comments already tell you something important:

- keep the image around `50x50`

If you ignore that and feed it garbage, you get lag or bad alignment.

## Island Toppers
- category key: `island-topper`
- config file: `IslandToppers.yml`
- extension mode: config

Each topper points to a schematic file in the `IslandToppers/` folder.

This category also depends on:

- WorldEdit or FAWE
- team topper positions being configured

## Kill Messages
- category key: `kill-message`
- config file: `KillMessages.yml`
- extension mode: config

Each entry can define lists for different kill types:

- `PvP-Kill`
- `Void-Kill`
- `Shoot-Kill`
- `Explosion-Kill`

Use `{victim}` and `{killer}` placeholders inside the message lines.

## Projectile Trails
- category key: `projectile-trails`
- config file: `ProjectileTrails.yml`
- extension mode: config

This is config-addable in terms of registration and menu entry, but the generic config item execute path is not where interesting trail logic lives. If you want a real new trail effect, you will usually end up writing code.

## Shopkeeper Skins
- category key: `shopkeeper-skins`
- config file: `ShopKeeperSkins.yml`
- extension mode: config

Supported entry styles:

- entity type based skin using `entity-type`
- player skin texture based skin using `skin-value` and `skin-sign`
- mirrored player skin using `mirror: true`

## Sprays
- category key: `sprays`
- config file: `Sprays.yml`
- extension mode: config

Each spray can use:

- `file`
- or `url`

If you use `url`, use a direct image URL that actually ends in something like `.png` or `.jpg`.

## Victory Dances
- category key: `victory-dance`
- config file: `VictoryDances.yml`
- extension mode: code

This category is meant for actual scripted behaviour and task/entity management.

## Wood Skins
- category key: `wood-skins`
- config file: `WoodSkins.yml`
- extension mode: code

This category is not a simple cosmetic entry file you can expand with raw config only. The actual block replacement behaviour is code-based.
