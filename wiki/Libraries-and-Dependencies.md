# Libraries and Dependencies

## Runtime dependencies
These are required by the plugin at runtime:

- [Vault](https://github.com/MilkBowl/Vault)
- [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)
- [PacketEvents](https://github.com/retrooper/packetevents)
- [WorldEdit](https://github.com/EngineHub/WorldEdit) or [FastAsyncWorldEdit](https://github.com/IntellectualSites/FastAsyncWorldEdit)
- one supported BedWars plugin

> Everything below this is not for server owners, ignore that. 
> That is extra information for anyone looking to understand what all
> libraries come pre-compiled into the plugin JAR.

## Main libraries used in the project
- [HikariCP](https://github.com/brettwooldridge/HikariCP)
- [XSeries](https://github.com/CryptoMorin/XSeries)
- [Aikar Commands Framework](https://github.com/aikar/commands)
- [Libby](https://github.com/Byteflux/libby)
- [Lombok](https://projectlombok.org/)
- [MongoDB Java Driver](https://github.com/mongodb/mongo-java-driver)
- [MariaDB Java Client](https://github.com/mariadb-corporation/mariadb-connector-j)
- [PostgreSQL JDBC Driver](https://github.com/pgjdbc/pgjdbc)
- [SQLite JDBC](https://github.com/xerial/sqlite-jdbc)

## Build repositories used by the project
The Gradle setup currently uses repositories such as:

- [Maven Central](https://repo1.maven.org/maven2/)
- [CodeMC](https://repo.codemc.io/repository/nms/)
- [CodeMC Releases](https://repo.codemc.io/repository/maven-releases/)
- [Sonatype OSS](https://oss.sonatype.org/content/groups/public/)
- [JitPack](https://jitpack.io)
- [PlaceholderAPI Repo](https://repo.extendedclip.com/content/repositories/placeholderapi/)
- [EngineHub Repo](https://maven.enginehub.org/repo/)
- [Aikar Repo](https://repo.aikar.co/content/groups/aikar/)
- [ScreamingSandals Repo](https://repo.screamingsandals.org/public/)

## Gradle coordinates used in the project
Useful ones from the build scripts:

- `com.github.cryptomorin:XSeries:11.3.0`
- `com.github.MilkBowl:VaultAPI:1.7.1`
- `me.clip:placeholderapi:2.11.6`
- `com.github.retrooper:packetevents-spigot:2.12.1`
- `com.zaxxer:HikariCP:5.1.0`
- `co.aikar:acf-paper:0.5.1-SNAPSHOT`
- `net.byteflux:libby-bukkit:1.3.0`
- `org.mongodb:mongodb-driver-sync:5.1.0`

## API module
If you only need the public API, the relevant module is:

- `cosmetic-api`

If you are working locally from source, that is the module you usually depend on from another plugin.
