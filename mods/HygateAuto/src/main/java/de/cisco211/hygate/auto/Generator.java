package de.cisco211.hygate.auto;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	 * <b>Create manifest</b>
	 * @param path {@link Path}
	 * @throws IOException If manifest file creation failed.
	 */
	protected void createManifest(Path path) throws IOException
	{
		Path manifestFile = path.resolve("manifest.json");
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
		Path translationFile = path.resolve("hygate_auto_gen.lang");
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

		// TODO: Do something with the worlds.
		if (plugin.debug)
			LOGGER.atInfo().log("%s", String.join(", ", worlds));

		// TODO: Rebuild items and their translation here.
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
				.filter(name -> name.startsWith("Hygate_Auto_"))
				.sorted()
				.collect(Collectors.toList())
			;
		}
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
