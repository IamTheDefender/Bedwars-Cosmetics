# Installation and First Setup
Please make sure to read this before reporting "plugin not working" issues...

## Requirements
You need:

- one supported BedWars plugin
- [Vault](https://github.com/MilkBowl/Vault)
- [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)
- [PacketEvents](https://github.com/retrooper/packetevents)
- [WorldEdit](https://github.com/EngineHub/WorldEdit) or [FastAsyncWorldEdit](https://github.com/IntellectualSites/FastAsyncWorldEdit)

What each dependency is used for:

- Vault: economy and permission handling
- PlaceholderAPI: placeholders in menus and messages
- PacketEvents: preview and packet related handling
- WorldEdit / FAWE: island topper schematics

## Basic install steps
1. Install your BedWars plugin.
2. Install the required dependencies other than PacketEvents.
3. Drop the BedWars Cosmetics jar into `plugins/`.
4. Start the server once. If **PacketEvents** is missing, the plugin will download and load the latest PacketEvents release automatically.
5. Let the plugin generate its config and category files.
6. Stop the server or reload carefully after editing.

## What files get created
Main files:

- `config.yml`
- `MainMenu.yml`
- category files like `DeathCries.yml`, `KillMessages.yml`, `Sprays.yml`, `ShopKeeperSkins.yml`, and the rest

Important directories created by the plugin:

- `Glyphs/`
- `IslandToppers/`
- your spray directory, which defaults to `Sprays/`

## Initial setup
There are two preview locations used by cosmetic previews:

- player preview location
- cosmetic preview location

Set them with:

```text
/bwc setupPlayerLocation
/bwc setupPreviewLocation
```

If you do not set them, previews will fail and throw errors.

## Island topper setup
Island toppers need manual location setting for every arena and every team.

Use:

```text
/bwc setIslandTopperPosition <teamName>
```

Example:

```text
/bwc setIslandTopperPosition Red
```

Make sure you are in the setup mode for the arena and use valid team names.

## Storage setup
You can use:

- SQLite (default)
- MySQL
- MariaDB
- PostgreSQL
- MongoDB

The storage backend is selected with:

```yml
database:
  type: "SQLITE"
```

Supported values:

- `SQLITE`
- `MYSQL`
- `MARIADB`
- `POSTGRESQL`
- `MONGODB`
- `REDIS`

### When to use SQLite
Use SQLite if:

- you run a single normal server
- you do not need shared storage
- you want the easiest setup

### When to use MySQL
Use MySQL if:

- you run proxy mode
- you want shared data
- you know what you are doing

### When to use MariaDB
Use MariaDB if:

- you already run MariaDB instead of MySQL
- 
### When to use PostgreSQL
Use PostgreSQL if:

- your stack already uses Postgres
- you want a proper SQL backend that is not MySQL or MariaDB

### When to use MongoDB
Use MongoDB if:

- you want document storage
- you already have Mongo in your stack

Proxy mode does not support SQLite. The plugin explicitly blocks that setup.

### Example config
Current storage keys are under `database.*`.

#### Main selector
```yml
database:
  type: "SQLITE"
```

#### MySQL

```yml
database:
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
```

#### MariaDB
```yml
database:
  mariadb:
    host: "localhost"
    port: 3306
    database: "name"
    username: "root"
    password: "none"
    use-ssl: false
    max-pool-size: 50
    max-lifetime: 1024
```

#### PostgreSQL
```yml
database:
  postgresql:
    host: "localhost"
    port: 5432
    database: "name"
    username: "postgres"
    password: "none"
    use-ssl: false
    max-pool-size: 50
    max-lifetime: 1024
```

#### MongoDB
```yml
database:
  mongodb:
    uri: "mongodb://localhost:27017"
    database: "bwcosmetics"
```

## Recommended setup order
If you want to avoid issues please follow this order:

1. Install dependencies.
2. Start the server.
3. Configure `database.type` and the matching storage section.
4. Configure preview locations.
5. Configure island topper positions if you use island toppers.
6. Configure categories and menus.
7. Configure language keys.
8. Reload with `/bwc reload` or restart.

## Gameplay function check
After setup, verify:

- `/bwc menu` opens
- previews work
- a player can select a default cosmetic
- a player can buy a cosmetic if they have money
- shopkeeper skins spawn correctly
- sprays and glyphs have real image files

If you face any issues, that are not covered within this wiki
feel free to reach out to me on any platform (SpigotMC, GitHub, or my Discord server).

But please note, DM support will not be provided for non-critical issues.
I check the discord server every so often, so do not worry, I will address your
issue as soon as I am able to. Pinging for me critical issues is okay but do not
ping me excessively. Thank you for understanding.
