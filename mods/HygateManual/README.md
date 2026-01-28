# Hygate Manual Plugin
Simple, non-code mod to define custom portals by hand.
Pretty self explaining.

## Install
* Place this mod folder (`HygateManual`) into the mods location of your game.
* The location where to put the mod depends on your Operating System and
  Game type being used (Singleplayer/Dedicated Server).

## Usage
* Inside `Server/Item/Items/Portal` you will see some example portals.
  Just copy them, rename them and alter its contents.
* In `Server/Languages/en-US/hygate_manual.lang` you can configure the translations
  for the portals (name and description).
* You can even edit them while the game/server is running,
  the game detects the changes and automatically updates them.
* Be aware, when editing the language file,
  it might mess up the translations for everything,
  until you restart the game. This is normal.
* For more details about portal item files,
  see [Howto_AssetsCustomPortals.md](../../docs/Howto_AssetsCustomPortals.md)

~EOF
