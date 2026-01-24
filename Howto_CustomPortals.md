# Howto: Custom Portals
Found a way to create custom portals to any world you like to have a portal to.
This solution works fully server side, means no client modification is required.


## Requirements
* Dedicated Hytale server. I don't know, if this can work with single player.
* Read/Write access to the files:
  * `Assets.zip` - To add new portals, which are actually just items.
  * Run script, to tell the server to load the unpacked assets instead the zip file.
* Ability to start/stop the server.
* Having a clue what you are doing, what JSON files are and such.


## How to do this

### Step 1
On your server, right next to the `Assets.zip`, create a folder called `Assets` and unzip the contents of `Assets.zip` into that folder.

### Step 2
In your run script, add/change `--assets` pointing to the Assets folder, instead of the zip file.
For example on my end my script now looks like this:
```bash
#!/bin/bash
cd /path/to/Hytale
java -Xms6G -Xmx6G -XX:+UseG1GC -XX:ParallelGCThreads=4 -XX:ConcGCThreads=2 -XX:AOTCache=Server/HytaleServer.aot -jar Server/HytaleServer.jar --assets Assets --backup-dir backups
```
*Im totally aware the second patch after early access release has its own start script now,
but im still using my own script.*

### Step 3
* Now go to `Hytale/Assets/Server/Item/Items/Portal`,
  you will see a `Hub_Portal_Default.json`.
  You can basically use this file as a template, to make your own portals.
* Copy that file and give it a different name,
  f.e. i like to make them like `Hub_Portal_Custom_WORLDNAME.json`,
  where `WORLDNAME` is actually the directory name of the world,
  you want the portal to lead to.
* All stored worlds can be found at `Hytale/universe/worlds`.

### Step 4
Now open your json file, you will find a section looking like this:
```jsonc
{
  /* ... */
  "Interactions": {
    "CollisionEnter": {
      "Interactions": [
        {
          "Type": "HubPortal",
          "WorldName": "default_world",
          "WorldGenType": "Hytale",
          "Next": {
            "Type": "Simple",
            "Effects": {
              "LocalSoundEventId": "SFX_Portal_Neutral_Teleport_Local"
            }
          }
        }
      ]
    }
  },
  /* ... */
}
```
* At `WorldName` you basically specify the directory name of that
  world `(WORLDNAME)`, you want the portal to lead to.  
  *That `_world` suffix is missleading here, you have to specify exact directory name.*
* At `WorldGenType`, you have to specify the exact value,
  that is defined at `WorldGen.Type` in `Hytale/universe/worlds/WORLDNAME/config.json`:
```jsonc
{
 /* ... */
 "WorldGen": {
   "Type": "Hytale",
 },
 /* ... */
}

```
  This setting is used to generate the world, if it doesn't exist yet.

### Step 5
* Now run the server with the unpacked assets.
* The server will recognize it as an item with ID `Hub_Portal_Custom_WORLDNAME`.
* Ingame, you can now do `/give YOURNAME Hub_Portal_Custom_WORLDNAME`,
  to give yourself the custom portal.
* Place it where you wan't, if you did everything correctly, it will just work.

### Bonus step
When you examine the portal json file carefully you will notice,
that you can f.e. edit the portal light color at `BlockType.Light.Color`.


## What happens when an update is coming out?
1. Make sure server is not running
2. Backup all your custom portal item files from the assets folder.
3. Empty that folder.
4. Now unzip `Assets.zip` again, like explained above.
5. Put all your portal item files back to where they go, like explained above.
6. Start server, enjoy.

*I know, that solution isn't the best, but it works without any programming.*  
*I asked several time's if its possible to "shadow" custom additions
 into the game assets, but no answer yet.*  
*In the long run, i will probably make a server plugin to do this.*


## What if it stops working?
Then it must be figured out again, to make it work :P

~EOF
