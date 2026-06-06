# Configuration Reference
The plugin uses multiple YAML files because there is a lot of
stuff to configure and yeah..

## File layout
Main files:

- `config.yml`
- `MainMenu.yml`
- `BedBreakEffect.yml`
- `DeathCries.yml`
- `FinalKillEffects.yml`
- `Glyphs.yml`
- `IslandToppers.yml`
- `KillMessages.yml`
- `ProjectileTrails.yml`
- `ShopKeeperSkins.yml`
- `Sprays.yml`
- `VictoryDances.yml`
- `WoodSkins.yml`

## `config.yml`
This file controls the general plugin behaviour.

### Debug
```yml
debug:
  enabled: false
```

Turns debug logging on or off.

### Storage
```yml
storage:
  sprays:
    directory: "Sprays"
```

Changes where spray image files are loaded from.

### Database
```yml
database:
  type: "SQLITE"
  mysql:
    enabled: false
    host: "localhost"
    port: 3306
    database: "name"
    username: "root"
    password: "none"
    use-ssl: false
    max-pool-size: 50
    max-lifetime: 1024
  mariadb:
    host: "localhost"
    port: 3306
    database: "name"
    username: "root"
    password: "none"
    use-ssl: false
    max-pool-size: 50
    max-lifetime: 1024
  postgresql:
    host: "localhost"
    port: 5432
    database: "name"
    username: "postgres"
    password: "none"
    use-ssl: false
    max-pool-size: 50
    max-lifetime: 1024
  mongodb:
    uri: "mongodb://localhost:27017"
    database: "bwcosmetics"
  redis:
    host: "localhost"
    port: 6379
    password: ""
    database: 0
    max-pool-size: 50
```

`database.type` defines the database type now (since v2.0).

Valid values:

- `SQLITE`
- `MYSQL`
- `MARIADB`
- `POSTGRESQL`
- `MONGODB`

The old `database.mysql.enabled` and legacy `mysql.*` keys have
been removed in the newer versions, if you still have those,
backup your current configuration folder and restart to regenerate
the configurations, you will need to reconfigure, though I can help with
that if you send me your backed up configuration and new generated one.

### Storage backend notes
- `SQLITE` is local file storage through JDBC.
- `MYSQL`, `MARIADB`, and `POSTGRESQL` are SQL backends.
- `MONGODB` stores player data as documents.
- Proxy mode will block `SQLITE`
> If you do not understand what all this means, stick to
> SQLite for non-Proxy servers and MySQL for Proxy servers

### Preview locations
```yml
preview:
  locations:
    cosmetic:
      world:
      x:
      y:
      z:
      yaw:
      pitch:
    player:
      world:
      x:
      y:
      z:
      yaw:
      pitch:
```

These are normally set with commands. Do not hand-edit them unless you know exactly why.

### Menu / feature toggles
```yml
menus:
  category:
    navigation:
      back:
        enabled: true

features:
  bed-break-effects:
    enabled: true
  death-cries:
    enabled: true
  final-kill-effects:
    enabled: true
  glyphs:
    enabled: true
  island-toppers:
    enabled: true
    use-team-order: true
  kill-messages:
    enabled: true
  projectile-trails:
    enabled: true
  packet-npcs:
    shopkeeper-entities: true
  shopkeeper-skins:
    enabled: true
    look-close: false
  sprays:
    enabled: true
  victory-dances:
    enabled: true
  wood-skins:
    enabled: true
```

`look-close` affects shopkeeper NPCs.  

`shopkeeper-entities` switches entity-based shopkeeper skins between packet NPCs and physical Bukkit entities.
(NOTE: physical entities have not been tested as much as the packet NPCs! stick to the default unless you know
what you are doing)

`use-team-order` affects how island topper selection is resolved in team modes.

## `MainMenu.yml`
This file controls the main menu layout, slots, icons and behaviour.

What it should contain:

- menu rows
- fill item config
- item material / skull
- item slot
- disable flags
- back button action command

What it should not contain anymore:

- display names
- lore
- titles

Those go in language files as of v2.0.

### Example structure
```yml
main-menu:
  layout:
    rows: 6
  fill-empty:
    enabled: true
    item: "BLACK_STAINED_GLASS_PANE:0"
  items:
    Sprays:
      item: "PAPER:0"
      slot: 15
      disabled: false
```

## Category files
Every category file has two kinds of settings:

- GUI settings
- cosmetic entries

### GUI section example
```yml
gui:
  layout:
    rows: 6
    item-slots:
      - 10
      - 11
      - 12
  fill-empty:
    enabled: true
    item: "BLACK_STAINED_GLASS_PANE:0"
  navigation:
    back:
      enabled: true
      slot: 49
      item: "ARROW:0"
    next:
      enabled: true
      slot: 47
      item: "ARROW:0"
    previous:
      enabled: true
      slot: 51
      item: "ARROW:0"
  sort:
    slot: 50
```

The plugin still reads some older fallback layouts, but do not rely on that for new installs.

### Cosmetic entry basics
Most category entries look like this:

```yml
some-category:
  some-id:
    item: "DIAMOND:0"
    price: 5000
    rarity: "COMMON"
```

Then category-specific keys are added depending on the cosmetic type.

## Material format
The addon uses strings like:

```text
STONE:0
PLAYER_HEAD:0:<base64>
```

For skull items, the full value is:

```text
player_head:0:<base64>
```

## Legacy keys
Some old keys are still supported for migration compatibility. That does not mean you should keep using them though.

Examples:

- `Debug`
- `BackItemInCosmeticsMenu`
- `Spray-Dir`
- old preview location sections

Use the current keys. The old ones are there so old installs do not break upon load.
