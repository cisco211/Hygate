package de.cisco211.hygate.auto;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hypixel.hytale.logger.HytaleLogger;

/**
 * <b>Generator</b>
 * <br/>
 * The mod pack generator.
 */
public class Generator
{
	/**
	 * <b>File manifest</b>
	 */
	public static final String FILE_MANIFEST = "manifest.json";

	/**
	 * <b>File world</b>
	 */
	public static final String FILE_WORLD = "config.json";

	/**
	 * <b>Logger</b>
	 */
	protected static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	/**
	 * <b>Package name</b>
	 */
	protected final String packageName;

	/**
	 * <b>Plugin</b>
	 */
	protected final Plugin plugin;

	/**
	 * <b>Constructor</b>
	 * @param plugin {@link Plugin}
	 */
	public Generator(@Nonnull Plugin plugin)
	{
		this.packageName = plugin.getManifest().getName() + "Gen";
		this.plugin = plugin;
		if (plugin.debug)
			LOGGER.atInfo().log("%s:Generator constructor", plugin.identifier);
	}

	/**
	 * <b>Create</b>
	 * <br/>
	 * Creates the package where generated items get put in.
	 */
	public void create()
	{
		// Debug
		if (plugin.debug)
		{
			LOGGER.atInfo().log("%s:Generator create", plugin.identifier);
		}

		// Try creation
		try
		{
			// Get package path
			Path packagePath = Paths.get("mods", packageName);

			// Package directory
			createDirectory(packagePath);

			// Manifest
			createManifest(packagePath);

			// Get server path
			Path serverPath = packagePath.resolve("Server");

			// Server directory
			createDirectory(serverPath);
			{
				// Get item path
				Path itemPath = serverPath.resolve("Item");

				// Item directory
				createDirectory(itemPath);
				{
					// Get items path
					Path itemsPath = itemPath.resolve("Items");

					// Items directory
					createDirectory(itemsPath);
					{
						// Get portal path
						Path portalPath = itemsPath.resolve("Portal");

						// Portal directory
						createDirectory(portalPath);
					}
				}
			}

			// Languages directory
			Path languagesPath = packagePath.resolve("Languages");

			// Languages directory
			createDirectory(languagesPath);
			{
				// Get en-US path
				Path enUsPath = languagesPath.resolve("en-US");

				// en-US directory
				createDirectory(enUsPath);
				{
					// Translation
					createTranslation(enUsPath);
				}
			}
		}

		// Catch creation failed
		catch (IOException e)
		{
			LOGGER.atSevere().withCause(e).log("Failed to create autogen package!");
		}
	}

	/**
	 * <b>Create directory</b>
	 * @param path {@link Path}
	 * @throws IOException If directory creation of given path failed.
	 */
	protected void createDirectory(Path path) throws IOException
	{
		if (Files.notExists(path))
		{
			Files.createDirectories(path);
			if (plugin.debug)
				LOGGER.atInfo().log("Created directory: %s", path.toAbsolutePath());
		}
	}

	/**
	 * <b>Create item</b>
	 * @param path {@link Path}
	 * @param object {@link JsonObject}
	 * @throws IOException If item file creation failed.
	 */
	protected void createItem(Path path, @Nonnull JsonObject object) throws IOException
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))
		{
			gson.toJson(object, writer);
			LOGGER.atInfo().log("Created item: %s", path.toAbsolutePath());
		}
	}

	/**
	 * <b>Create manifest</b>
	 * @param path {@link Path}
	 * @throws IOException If manifest file creation failed.
	 */
	protected void createManifest(Path path) throws IOException
	{
		Path manifestFile = path.resolve(FILE_MANIFEST);
		if (Files.notExists(manifestFile))
		{
			var mf = plugin.getManifest();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject oldObj = gson.toJsonTree(mf).getAsJsonObject();
			JsonObject newObj = new JsonObject();
			for (Map.Entry<String, JsonElement> entry : oldObj.entrySet())
			{
				String key = entry.getKey();
				if (key.equalsIgnoreCase("main")
					|| key.equalsIgnoreCase("serverVersion"))
				{
					continue;
				}
				String pascalKey = key.substring(0, 1).toUpperCase() + key.substring(1);
				JsonElement value = entry.getValue();
				if (key.equalsIgnoreCase("dependencies"))
				{
					newObj.add(pascalKey, new JsonObject());
				}
				else if (key.equalsIgnoreCase("name"))
				{
					newObj.addProperty(pascalKey, packageName);
				}
				else if (key.equalsIgnoreCase("version"))
				{
					newObj.addProperty(pascalKey, mf.getVersion().toString());
				}
				else
				{
					newObj.add(pascalKey, value);
				}
			}
			try (Writer writer = Files.newBufferedWriter(manifestFile))
			{
				gson.toJson(newObj, writer);
				LOGGER.atInfo().log("Created manifest: %s", manifestFile.toAbsolutePath());
			}
		}
	}

	/**
	 * <b>Create translation</b>
	 * @param path {@link Path}
	 * @throws IOException If translation file creation failed.
	 */
	protected void createTranslation(Path path) throws IOException
	{
		Path translationFile = path.resolve(Item.LANG_KEY + Item.LANG_EXT);
		if (Files.notExists(translationFile))
		{
			try (Writer writer = Files.newBufferedWriter(translationFile))
			{
				writer.write("# === " + packageName + " Items ===\n");
				writer.write("\n");
				writer.write("\n");
				writer.write("# ~EOF\n");
				LOGGER.atInfo().log("Created translation: %s", translationFile.toAbsolutePath());
			}
		}
	}

	/**
	 * <b>Generate</b>
	 * <br/>
	 * Generates the gateway portal items.
	 * @return boolean Generation succeeded or failed.
	 * @throws IOException If writing of Hygate item files failed.
	 */
	public boolean generate() throws IOException
	{
		// Debug
		if (plugin.debug)
		{
			LOGGER.atInfo().log("%s:Generator generate", plugin.identifier);
		}

		// Get available worlds
		var worlds = worlds();

		// Get path
		var path = Paths.get("mods", packageName, "Server", "Item", "Items", "Portal");

		// Iterate over worlds
		for (String world : worlds)
		{
			// Get path to item file
			Path file = path.resolve(Item.FILE_BEGIN + world + Item.FILE_END);

			// Item does exist
			if (Files.exists(file))
			{
				JsonElement element = readJson(file);
				if (element != null)
				{
					JsonObject obj = Objects.requireNonNull(element.getAsJsonObject());
					Item item = new Item().item(obj);
					try
					{
						JsonObject object = item.build();
						createItem(file, object);
					}
					catch (IllegalStateException e)
					{
						LOGGER.atSevere().log("Incomplete item to update for world " + world + ": " + e.getMessage());
					}
				}
			}

			// Item does not exist
			else
			{
				String w = Objects.requireNonNull(world);
				String worldGenType = worldGenType(w);
				Item item = new Item().setWorld(w).setWorldGenType(worldGenType);
				try
				{
					JsonObject object = item.build();
					createItem(file, object);
				}
				catch (IllegalStateException e)
				{
					LOGGER.atSevere().log("Incomplete item to create for world " + world + ": " + e.getMessage());
				}
			}
		}

		// Get available items
		var items = items();

		// Iterate over items
		for (String item : items)
		{
			// Get path to item file
			Path file = path.resolve(item);

			// Get world from item
			String world = Item.itemToWorld(Objects.requireNonNull(item));

			// Item does exist but not in worlds
			if (Files.exists(file) && !worlds.contains(world))
			{
				JsonElement element = readJson(file);
				if (element != null)
				{
					JsonObject obj = Objects.requireNonNull(element.getAsJsonObject());
					// TODO: Make portal somehow inaccessible.
					Item item2 = new Item().item(obj);
					try
					{
						JsonObject object = item2.build();
						createItem(file, object);
					}
					catch (IllegalStateException e)
					{
						LOGGER.atSevere().log("Incomplete item to update for orphan world " + world + ": " + e.getMessage());
					}
				}
			}
		}

		// Done
		return false;
	}

	/**
	 * <b>Items</b>
	 * <br/>
	 * Read available Hygate items from Portal directory.
	 * @return {@link List} &lt;{@link String}&gt; List of hygate item file names.
	 * @throws IOException If listing of Hygate item files in Item/Items/Portal directory failed.
	 */
	public List<String> items() throws IOException
	{
		if (plugin.debug)
		{
			LOGGER.atInfo().log("%s:Generator items", plugin.identifier);
		}
		Path rootPath = Paths.get("mods", packageName, "Server", "Item", "Items", "Portal");
		try (Stream<Path> stream = Files.list(rootPath))
		{
			return stream
				.filter(Files::isRegularFile)
				.map(path2 -> path2.getFileName().toString())
				.filter(name -> name.startsWith(Item.FILE_BEGIN))
				.sorted()
				.collect(Collectors.toList())
			;
		}
	}

	/**
	 * <b>Read JSON</b>
	 * <br/>
	 * Read given path as JSON into {@code JsonElement}.
	 * @param path {@link Path}
	 * @return {@link JsonElement}
	 * @throws IOException If failed to read file or not exist.
	 */
	public static JsonElement readJson(Path path) throws IOException
	{
		if (!Files.exists(path))
		{
			return null;
		}
		Gson gson = new Gson();
		try (Reader reader = Files.newBufferedReader(path))
		{
			JsonElement element = gson.fromJson(reader, JsonElement.class);
			if (element != null)
			{
				return element;
			}
		}
		return null;
	}

	/**
	 * <b>World generator type</b>
	 * <br/>
	 * Read world generator type from given world.
	 * @param world {@link String}
	 * @return {@link String}
	 * @throws IOException If failed to read file or not exist.
	 */
	public @Nonnull String worldGenType(@Nonnull String world) throws IOException
	{
		Path path = Paths.get("universe", "worlds", world, FILE_WORLD);
		var element = readJson(path);
		if (element != null)
		{
			JsonElement worldGenType = Item.getPath(element.getAsJsonObject(), "WorldGen.Type");
			if (worldGenType != null)
			{
				return Objects.requireNonNull(worldGenType.getAsString());
			}
		}
		return "Unknown";
	}

	/**
	 * <b>Worlds</b>
	 * <br/>
	 * Read suitable worlds from universe/worlds directory.
	 * @return {@link List} &lt;{@link String}&gt; List of world directory names.
	 * @throws IOException If listing of world directories in universe/worlds directory failed.
	 */
	public List<String> worlds() throws IOException
	{
		if (plugin.debug)
		{
			LOGGER.atInfo().log("%s:Generator worlds", plugin.identifier);
		}
		Path rootPath = Paths.get("universe", "worlds");
		try (Stream<Path> stream = Files.list(rootPath))
		{
			return stream
				.filter(Files::isDirectory)
				.map(path2 -> path2.getFileName().toString())
				.filter(name -> !name.startsWith("instance-"))
				.sorted()
				.collect(Collectors.toList())
			;
		}
	}

	/**
	 * <b>Worlds formatted</b>
	 * <br/>
	 * List suitable worlds being formatted nicely.
	 * @return {@link List} &lt;{@link String}&gt; List of formatted world directory names.
	 * TODO: Add list view, when items can fit in chat, else do string join.
	 */
	public List<String> worldsFormatted() throws IOException
	{
		var list = worlds();
		if (list.isEmpty())
		{
			return List.of();
		}
		var worlds = new ArrayList<String>(list.size());
		for (String world : list)
		{
			worlds.add(Item.worldToLabel(Objects.requireNonNull(world)));
		}
		worlds.sort(null);
		return worlds;
	}
}
