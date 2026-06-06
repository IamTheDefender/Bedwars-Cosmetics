# Language Files and Messages
As of v2.0 most of the user-facing messages have been moved from
configuration files to the language file(s) instead.

## What belongs in language files
Put these in language files:

- menu titles
- cosmetic names
- cosmetic lore
- button names
- button lore
- status text like selected / locked / click to purchase
- cooldown messages
- final kill suffix

Do not put those in config files.

## Language key format
Most keys live under `cosmetics.*`.

Examples:

```yml
cosmetics:
  title: "&8Cosmetics"
  menu:
    category-title-format: "&8{category}"
  selected: "&aSELECTED!"
  click-to-select: "&eClick to select."
  click-to-purchase: "&eClick to purchase."
  not-purchase-able: "&cLOCKED."
  no-coins: "&cYou don't have enough coins!"
  spray-msg: "&cYou must wait 3 seconds between spray uses!"
```

Main menu items use keys like:

```yml
cosmetics:
  main-menu:
    Sprays:
      name: "&aSprays"
      lore:
        - "&7Select a spray..."
```

Category cosmetics use keys like:

```yml
cosmetics:
  death-cry:
    bazinga:
      name: "&aBazinga"
      lore:
        - "&7Select Bazinga as your Death cry"
```

## Migration behaviour
The plugin still reads some old message values from config files during migration and seeds them into the language file if needed.

That is for old installs.

For new installs, please follow the wiki on customizing the messages or adding new ones.
Do not rely on auto-migration as this may be removed in future versions to come.

## Developer side message wrappers
The API includes:

- `Message`
- `Messages`

`Message` holds:

- the language path
- the default value

`Messages` is a static registry of common built-in message keys.

### Example
```java
String selected = Messages.SELECTED.value(player);
List<String> nextLore = Messages.GUI_NEXT_LORE.list(player);
```

### Custom message
```java
Message custom = Messages.of("my-plugin.warning", "&cSomething went wrong.");
player.sendMessage(custom.value(player));
```

## Placeholder support
If PlaceholderAPI is installed, message resolution runs placeholders where relevant.

That means placeholders can be used in:

- menu names
- lore
- other resolved message strings
