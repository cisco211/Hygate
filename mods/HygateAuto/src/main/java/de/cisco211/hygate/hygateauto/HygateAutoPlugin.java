package de.cisco211.hygate.hygateauto;

import javax.annotation.Nonnull;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import de.cisco211.hygate.hygateauto.command.HygateCommand;

public class HygateAutoPlugin extends JavaPlugin
{
	private static final boolean DEBUG = true;

	protected static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	public final String identifier;

	public HygateAutoPlugin(@Nonnull JavaPluginInit init) // Called after plugin being loaded in
	{
		super(init);
		this.identifier = this.getName() + " v" + this.getManifest().getVersion().toString();
		if (DEBUG)
			LOGGER.atInfo().log("%s constructor", this.identifier);
		create();
	}

	// Create
	// Creates the package where generated items get put in.
	protected void create()
	{
		if (DEBUG)
			LOGGER.atInfo().log("%s create", this.identifier);
		// TODO: Create the autogen pack here.
	}

	// Generate
	// Generates the gateway portal items.
	public boolean generate()
	{
		if (DEBUG)
			LOGGER.atInfo().log("%s generate", this.identifier);
		// TODO: Rebuild items and their translation here.
		return true;
	}

	// Setup
	// C: Called after asset monitor.
	@Override
	protected void setup()
	{
		this.getCommandRegistry().registerCommand(new HygateCommand(this));
		if (DEBUG)
			LOGGER.atInfo().log("%s setup", this.identifier);
	}

	// Shutdown
	// C: Called when unloading plugins.
	@Override
	protected void shutdown()
	{
		if (DEBUG)
			LOGGER.atInfo().log("%s shutdown", this.identifier);
	}

	// Start
	// C: Called after loading worlds.
	@Override
	protected void start()
	{
		if (DEBUG)
			LOGGER.atInfo().log("%s start", this.identifier);
	}
}
