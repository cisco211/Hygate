package de.cisco211.hygate.auto;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.annotation.Nonnull;

import com.hypixel.hytale.common.plugin.PluginManifest;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.asset.AssetModule;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

/**
 * <b>Plugin</b>
 * <br/>
 * HygateAuto plugin main entry point.
 */
public class Plugin extends JavaPlugin
{
	/**
	 * <b>Logger</b>
	 */
	protected static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	/**
	 * <b>Debug</b>
	 * <br/>
	 * Debug flag for call hints in logs.
	 */
	public final boolean debug = true;

	/**
	 * <b>Generator</b>
	 * <br/>
	 * The mod pack generator.
	 */
	public final Generator generator;

	/**
	 * <b>Identifier</b>
	 * <br/>
	 * Plugin identifier.
	 */
	public final String identifier;

	/**
	 * <b>Constructor</b>
	 * <br/>
	 * Called after plugin being loaded in.
	 * @param init {@link JavaPluginInit}
	 */
	public Plugin(@Nonnull JavaPluginInit init)
	{
		super(init);
		this.identifier = this.getName(); // C: Must happen at very first
		if (debug)
			LOGGER.atInfo().log("%s:Plugin constructor", this.identifier);

		this.generator = new Generator(this);
		generator.create();
		try
		{
			generator.generate();
		}
		catch (IOException e)
		{
			LOGGER.atSevere().log("Generator failed: %s", e.getMessage());
		}
	}

	/**
	 * <b>Setup</b>
	 * <br/>
	 * Called after asset monitor.
	 */
	@Override
	protected void setup()
	{
		// Debug
		if (debug)
			LOGGER.atInfo().log("%s:Plugin setup", this.identifier);

		// Super
		super.setup();

		// Register command
		this.getCommandRegistry().registerCommand(new Command(this));

		// Get asset module
		var module = AssetModule.get();

		// Pack name
		String name = Objects.requireNonNull(generator.packageName);

		// Register pack
		if (module.getAssetPack(name) == null)
		{
			PluginManifest manifest = new PluginManifest();
			manifest.setName(name);
			manifest.setGroup("Hygate");
			Path root = Objects.requireNonNull(Paths.get("mods", name));
			module.registerPack(name, root, Objects.requireNonNull(manifest));
			var pack = module.getAssetPack(name);
			if (pack == null)
				LOGGER.atInfo().log("pack is null");
			else
				LOGGER.atInfo().log("%s", pack.toString());
		}
	}

	/**
	 * <b>Shutdown</b>
	 * <br/>
	 * Called when unloading plugins.
	 */
	@Override
	protected void shutdown()
	{
		if (debug)
			LOGGER.atInfo().log("%s:Plugin shutdown", this.identifier);
		super.shutdown();
	}

	/**
	 * <b>Start</b>
	 * <br/>
	 * Called after loading worlds.
	 */
	@Override
	protected void start()
	{
		if (debug)
			LOGGER.atInfo().log("%s:Plugin start", this.identifier);
		super.start();
	}
}
