# Commands and Permissions
## Main aliases
- `/bwc`
- `/bedwarscosmetics`
- `/cosmetics`
- `/cos`
- `/bwcos`
- `/bwcosmetic`
- `/bwcosmetics`

## Player commands
These are the commands normal players will actually use.

### `/bwc menu`
Opens the main cosmetics menu.

### `/bwc km`
Opens the Kill Messages category menu.

### `/bwc shopkeeper`
Opens the Shopkeeper Skins category menu.

### `/bwc sprays`
Opens the Sprays category menu.

### `/bwc dc`
Opens the Death Cries category menu.

### `/bwc glyphs`
Opens the Glyphs category menu.

### `/bwc bbe`
Opens the Bed Break Effects category menu.

### `/bwc finalke`
Opens the Final Kill Effects category menu.

### `/bwc pt`
Opens the Projectile Trails category menu.

### `/bwc vd`
Opens the Victory Dances category menu.

### `/bwc ws`
Opens the Wood Skins category menu.

### `/bwc it`
Opens the Island Toppers category menu.

## Admin commands

### `/bwc help`
Shows command help.

Permission:

```text
bwcosmetics.help
```

### `/bwc reload`
Reloads YAML files.

Permission:

```text
bwcosmetics.reload
```

### `/bwc set <type> <cosmeticId> <player>`
Force sets a selected cosmetic for a player.

Permission:

```text
bwcosmetics.admin
```

### `/bwc setupPreviewLocation`
Saves the cosmetic preview location using your current position.

Permission:

```text
bwcosmetics.admin
```

### `/bwc setupPlayerLocation`
Saves the player preview location using your current position.

Permission:

```text
bwcosmetics.admin
```

### `/bwc setIslandTopperPosition <teamName>`
Saves an island topper location for a team while in arena setup.

Permission:

```text
bwcosmetics.admin
```

## Cosmetic ownership permissions
Cosmetic ownership is permission based.

### Permission prefixes by category
- Bed Break Effects: `beddestroy.<id>`
- Death Cries: `deathcry.<id>`
- Final Kill Effects: `finalkilleffect.<id>`
- Glyphs: `glyph.<id>`
- Island Toppers: `islandtopper.<id>`
- Kill Messages: `killmessage.<id>`
- Projectile Trails: `projectiletrail.<id>`
- Shopkeeper Skins: `shopkeeperskin.<id>`
- Sprays: `spray.<id>`
- Victory Dances: `victorydance.<id>`
- Wood Skins: `woodskin.<id>`

### Wildcards
The plugin also checks `<prefix>.*` in some ownership logic.

Examples:

```text
spray.*
killmessage.*
shopkeeperskin.*
```

This also depends on your permission plugin to actually handle wildcard nodes properly.

## Restrictions
Players cannot open cosmetics menus while in a game arena.
