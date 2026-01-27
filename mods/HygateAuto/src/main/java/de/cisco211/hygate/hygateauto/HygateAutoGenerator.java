package de.cisco211.hygate.hygateauto;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hypixel.hytale.logger.HytaleLogger;

public class HygateAutoGenerator
{
	protected static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	protected final String packageName;

	protected final HygateAutoPlugin plugin;

	/**
	 * <b>Constructor</b>
	 * @param plugin @Nonnull HygateAutoPlugin
	 */
	public HygateAutoGenerator(@Nonnull HygateAutoPlugin plugin)
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
	 * @param path Path
	 * @throws IOException
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
	 * @param path Path
	 * @throws IOException
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
	 * @param path Path
	 * @throws IOException
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
	 * @return boolean
	 * @throws IOException
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
	 * <b>Worlds</b>
	 * <br/>
	 * Read suitable worlds from universe/worlds directory.
	 * @return List<String>
	 * @throws IOException
	 */
	public List<String> worlds() throws IOException
	{
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
	 * @return List<String>
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
			String formatted = world.replace('_', ' ');
			formatted = formatted.substring(0, 1).toUpperCase() + formatted.substring(1);
			worlds.add(formatted);
		}
		worlds.sort(null);
		return worlds;
	}
}
