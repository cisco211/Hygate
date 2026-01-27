package de.cisco211.hygate.hygateauto;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hypixel.hytale.logger.HytaleLogger;

public class HygateAutoGenerator
{
	protected static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	protected final HygateAutoPlugin plugin;

	public HygateAutoGenerator(HygateAutoPlugin plugin)
	{
		this.plugin = plugin;
	}

	// Create
	// Creates the package where generated items get put in.
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
			Path packagePath = Paths.get("mods", plugin.getManifest().getName());

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

	// Create directory
	protected void createDirectory(Path path) throws IOException
	{
		if (Files.notExists(path))
		{
			Files.createDirectories(path);
			if (plugin.debug)
				LOGGER.atInfo().log("Created directory: %s", path.toAbsolutePath());
		}
	}

	// Create manifest
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
				if (key.equalsIgnoreCase("version"))
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
				LOGGER.atInfo().log("Created json: %s", manifestFile.toAbsolutePath());
			}
		}
	}

	// Create translation
	protected void createTranslation(Path path) throws IOException
	{
		Path translationFile = path.resolve("hygate_auto_items.lang");
		if (Files.notExists(translationFile))
		{
			try (Writer writer = Files.newBufferedWriter(translationFile))
			{
				writer.write("# === " + plugin.getManifest().getName() + " Items ===\n\n");
				LOGGER.atInfo().log("Created translation: %s", translationFile.toAbsolutePath());
			}
		}
	}

	// Generate
	// Generates the gateway portal items.
	public boolean generate()
	{
		if (plugin.debug)
			LOGGER.atInfo().log("%s:Generator generate", plugin.identifier);
		// TODO: Rebuild items and their translation here.
		return true;
	}
}
