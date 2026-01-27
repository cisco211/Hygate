package de.cisco211.hygate.hygateauto;

import javax.annotation.Nonnull;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import de.cisco211.hygate.hygateauto.command.HygateCommand;

public class HygateAutoPlugin extends JavaPlugin
{
	protected static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	public final boolean debug = true;

	public final HygateAutoGenerator generator;

	public final String identifier;

	public HygateAutoPlugin(@Nonnull JavaPluginInit init) // Called after plugin being loaded in
	{
		super(init);
		this.generator = new HygateAutoGenerator(this);
		this.identifier = this.getName() + " v" + this.getManifest().getVersion().toString();
		if (debug)
			LOGGER.atInfo().log("%s constructor", this.identifier);
		generator.create();
	}

	// Setup
	// C: Called after asset monitor.
	@Override
	protected void setup()
	{
		this.getCommandRegistry().registerCommand(new HygateCommand(this));
		if (debug)
			LOGGER.atInfo().log("%s setup", this.identifier);
	}

	// Shutdown
	// C: Called when unloading plugins.
	@Override
	protected void shutdown()
	{
		if (debug)
			LOGGER.atInfo().log("%s shutdown", this.identifier);
	}

	// Start
	// C: Called after loading worlds.
	@Override
	protected void start()
	{
		if (debug)
			LOGGER.atInfo().log("%s start", this.identifier);
	}
}
