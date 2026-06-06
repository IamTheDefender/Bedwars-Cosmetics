# Troubleshooting
Had a lot of complaints about plugin not working when the plugin
was actually just fine but users faced common configuration issue or
incorrect setups

## The plugin disables itself on startup
Check:

- you have one supported BedWars plugin installed
- Vault is installed
- PlaceholderAPI is installed
- WorldEdit or FAWE is installed

If one of those is missing, the plugin may disable itself.

## Menus open but previews do not work
You probably forgot to set preview locations.

Use:

```text
/bwc setupPlayerLocation
/bwc setupPreviewLocation
```

Then reload.

## Island Toppers do nothing
Check all of this:

- WorldEdit or FAWE is installed
- the topper schematic file exists
- team topper positions were set
- the category is enabled

If any one of those is missing, island toppers will not work properly, it may even throw errors in the console.

## Sprays do not render
Check:

- the spray file exists in the configured spray directory
- if using URL, the URL is a direct image URL
- the file extension is valid

Also remember that the plugin can download spray assets asynchronously. Give it a moment if you just changed them.

## Glyphs do not line up or cause lag
Your image is probably too large or badly sized.

Use smaller images for optimal performance for now. 
I plan on improving this feature and auto-cap high-res
images but that's a TODO feature.

## Purchases do not unlock cosmetics
Check:

- Vault is installed
- your economy plugin is working
- your permission plugin is working
- the cosmetic is marked purchaseable
- the player has enough balance

The plugin grants ownership through permission nodes. If your permission system is broken, purchases will not work correctly.

## Proxy mode breaks with SQLite
That is not a bug. Proxy mode requires a non-SQLite backend.

Use one of:

- MySQL
- MariaDB
- PostgreSQL
- MongoDB

If you want the least surprising proxy setup, use MySQL or MariaDB.

## The wrong backend is loading
Check `config.yml`.

The backend is selected with:

```yml
database:
  type: "SQLITE"
```

If that value is wrong, the plugin will load the wrong backend.

## A cosmetic entry shows up but does nothing interesting
You probably added it to a config-addable category where the base logic is very limited, or you tried to fake a code-only category through config.

Read:

- [Cosmetic Categories](Cosmetic-Categories)
- [Adding Cosmetics from Config](Adding-Cosmetics-from-Config)
- [Creating Custom Cosmetics in Code](Creating-Custom-Cosmetics-in-Code)

## Names and lore are not updating
Check the language files, not the config files.

This plugin now resolves user-facing text from language files. If you keep editing config-side text keys from old setups, you are editing the wrong place.

## Reload did not fix it
Not everything should be debugged with a reload.

Do this instead:

1. check console errors
2. check the correct file
3. verify assets exist
4. verify permissions
5. verify category is enabled
6. try restarting

## Still broken
If you still need help:

- read the wiki properly
- gather the exact error
- gather the exact config section involved
- explain what plugin stack you use
- then ask for support

Please do not just say “plugin don't work”, make sure to describe
the issue, provide console logs or video with the issue visible if applicable.
