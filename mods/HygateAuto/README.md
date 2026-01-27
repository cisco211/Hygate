# Hygate Auto Plugin

Generates ancient gateway items based on available worlds (not instances) in the game.

### Configuring the Plugin
If you installed the game in a non-standard location,
you will need to tell the project about that.
The recommended way is to create a file
at `%USERPROFILE%/.gradle/gradle.properties` to set these properties globally.

```properties
# Set a custom game install location
hytale.install_dir=path/to/Hytale

# Speed up the decompilation process significantly, by only including the core hytale packages.
# Recommended if decompiling the game takes a very long time on your PC.
hytale.decompile_partial=true
```

On windows, i figured out, you must also make sure,
that `GRADLE_USER_HOME` environment var is pointing to `%USERPROFILE%/.gradle/`,
or it might not work at all.

## Building the Plugin
For VSCode, there is a `.vscode/tasks.json` working with `TaskDeck` extension,
that defines `gradlew` commands for specific mods.

Otherwise, `cd` into the specific mod directory and call `gradlew` there.

## Using the Plugin
To be done...
