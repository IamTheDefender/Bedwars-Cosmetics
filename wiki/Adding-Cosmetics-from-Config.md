# Adding Cosmetics from Config
This page is for categories that are addable from config. If you try to use it for code-only categories like Final Kill Effects or Bed Break Effects, that will not work.
Refer to the [Cosmetic-Categories](Cosmetic-Categories.md).

## Categories you can add from config
- Death Cries
- Glyphs
- Island Toppers
- Kill Messages
- Projectile Trails
- Shopkeeper Skins
- Sprays

## Common rules for all config-added cosmetics
- every cosmetic needs a unique ID
- the ID becomes the permission suffix and config key
- use lowercase and hyphens unless you have a good reason not to
- `item` must be valid
- `rarity` must match a valid rarity enum
- user-facing name and lore should go into language files

## Rarity values
Common values you will see:

- `NONE`
- `COMMON`
- `RARE`
- `RANDOM`

## Example: adding a Death Cry
File: `DeathCries.yml`

```yml
death-cry:
  spooky-bell:
    item: "BELL:0"
    sound: "BLOCK_NOTE_BLOCK_BELL"
    volume: 1
    pitch: 1
    price: 5000
    rarity: "COMMON"
```

Then add the language entries:

```yml
cosmetics:
  death-cry:
    spooky-bell:
      name: "&eSpooky Bell"
      lore:
        - "&7Select Spooky Bell as your Death cry"
```

## Example: adding a Glyph
File: `Glyphs.yml`

```yml
glyph:
  ruby:
    item: "REDSTONE:0"
    price: 5000
    file: "ruby.png"
    rarity: "COMMON"
```

Then place `ruby.png` in the `Glyphs/` folder.

Language:

```yml
cosmetics:
  glyph:
    ruby:
      name: "&cRuby"
      lore:
        - "&7Select Ruby as your Glyph!"
```

Keep glyph images small. Around `50x50` is a good range.

## Example: adding an Island Topper
File: `IslandToppers.yml`

```yml
island-topper:
  dragon-statue:
    item: "DRAGON_EGG:0"
    price: 15000
    file: "dragon-statue.schematic"
    rarity: "RARE"
```

Then place the schematic into `IslandToppers/`.

Language:

```yml
cosmetics:
  island-topper:
    dragon-statue:
      name: "&5Dragon Statue"
      lore:
        - "&7Select Dragon Statue as your Island Topper!"
```

Do not forget the team position setup command, or the topper won't spawn.

## Example: adding a Kill Message
File: `KillMessages.yml`

```yml
kill-message:
  frostbite:
    item: "ICE:0"
    price: 5000
    rarity: "COMMON"
    PvP-Kill:
      - "{victim} &7was frozen by {killer}."
      - "{victim} &7was chilled to death by {killer}."
    Void-Kill:
      - "{victim} &7slipped into the void because of {killer}."
```

Language:

```yml
cosmetics:
  kill-message:
    frostbite:
      name: "&bFrostbite"
      lore:
        - "&7Select Frostbite as your Kill Message"
```

Supported message types:

- `PvP-Kill`
- `Void-Kill`
- `Shoot-Kill`
- `Explosion-Kill`

## Example: adding a Spray from file
File: `Sprays.yml`

```yml
sprays:
  my-logo:
    item: "PAPER:0"
    file: "my-logo.png"
    price: 5000
    rarity: "COMMON"
```

Put `my-logo.png` in your spray directory.

Default spray directory:

```text
Sprays/
```

Language:

```yml
cosmetics:
  sprays:
    my-logo:
      name: "&aMy Logo"
      lore:
        - "&7Select My Logo as your"
        - "&7Spray!"
```

## Example: adding a Spray from URL
```yml
sprays:
  remote-logo:
    item: "PAPER:0"
    url: "https://example.com/logo.png"
    price: 5000
    rarity: "COMMON"
```

Use a direct image URL. Not an image page. So the plugin can fetch the raw image or else
it may throw errors / not working properly.

## Example: adding a Shopkeeper Skin using entity type
File: `ShopKeeperSkins.yml`

```yml
shopkeeper-skins:
  blaze:
    price: 5000
    item: "BLAZE_POWDER:0"
    entity-type: "BLAZE"
    rarity: "COMMON"
```

## Example: adding a Shopkeeper Skin using skin texture
```yml
shopkeeper-skins:
  mage:
    price: 15000
    item: "player_head:0:<base64>"
    skin-value: "<skin-value>"
    skin-sign: "<skin-sign>"
    rarity: "RARE"
```

## Example: mirrored Shopkeeper Skin
```yml
shopkeeper-skins:
  mirror:
    price: 0
    item: "PLAYER_HEAD:0"
    mirror: true
    rarity: "COMMON"
```

This makes the NPC use the player's own skin.

## Example: adding a Projectile Trail entry
File: `ProjectileTrails.yml`

```yml
projectile-trails:
  emerald-trail:
    item: "EMERALD:0"
    price: 5000
    rarity: "COMMON"
```

Language:

```yml
cosmetics:
  projectile-trails:
    emerald-trail:
      name: "&aEmerald Trail"
      lore:
        - "&7Select Emerald Trail as your Projectile,"
        - "&7trail!"
```

Again, if you want a truly custom trail effect with unique logic, you will
have to either code a custom addon for the plugin or suggest it to me on any platform.

## Common mistakes
- forgetting the language key
- using a missing file name
- using a non-direct spray URL
- using an invalid material string
- using invalid sound names
- writing names and lore into config files
- adding an entry to a code-only category
