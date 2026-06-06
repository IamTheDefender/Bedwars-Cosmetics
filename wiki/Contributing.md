# Contributing
All contributions are welcome, but please make sure to check your code before opening PRs.

Especially with AI written code PRs.

## Main project rules
- keep user-facing text in language files, not config files
- prefer version-safe APIs and abstractions
- do not use NMS unless there is no sane alternative
- do not add random one-off hacks for one server version if the version support layer should handle it
- keep new config keys consistent with the current naming style
- do not break old installations without a migration path

## Code style expectations
- Java 11 compatible
- keep methods readable
- keep feature logic within limited packages
- do not copy-paste AI generated code without reviewing
- if you add a new message key, wire it through the message system properly


## If you add a new cosmetic category or expand an existing one
Please document:

- whether it is config-addable or code-only
- which files it uses
- which assets it needs
- which permissions it expects
- whether it supports preview

## If you add version-specific code
The project already has version support modules:

- `versionsupport_1_8_R3`
- `versionsupport_1_20`

Do check them and you can add new helper methods but make
sure you understand how they work and what versions they are
intended for.

## Build before you submit
Use:

```bash
./gradlew build
```

Make sure to test the changes in-game as well. To make sure nothing breaks.
## Documentation updates
If your change affects:

- commands
- config keys
- language keys
- dependencies
- API behaviour
- category setup

then please edit the wiki as well or let me know to do so.

## Why I ask for this
Because maintaining a plugin like this across multiple BedWars implementations and server versions is already quite complex and time-consuming, 
and a few of the recent PRs were mostly AI written without review
which made the v2.0 release delayed by a lot due to refactoring / changes
that should not have been needed to implement but which were by the PR.
