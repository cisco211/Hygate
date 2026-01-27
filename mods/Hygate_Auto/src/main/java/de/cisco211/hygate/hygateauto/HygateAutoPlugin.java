package de.cisco211.hygate.hygateauto;

import javax.annotation.Nonnull;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import de.cisco211.hygate.hygateauto.command.HygateCommand;

public class HygateAutoPlugin extends JavaPlugin
{
	protected static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	public final String identifier;

	public HygateAutoPlugin(@Nonnull JavaPluginInit init) // Called after plugin being loaded in
	{
		super(init);
		this.identifier = this.getName() + " v" + this.getManifest().getVersion().toString();
		LOGGER.atInfo().log("%s constructor", this.identifier);
		create();
	}

	protected void create()
	{
		LOGGER.atInfo().log("%s create", this.identifier);
		// TODO: Create the autogen pack here.
	}

	public boolean generate()
	{
		LOGGER.atInfo().log("%s generate", this.identifier);
		// TODO: Rebuild items and their translation here.
		return true;
	}

	@Override
	protected void setup() // Called after asset monitor
	{
		this.getCommandRegistry().registerCommand(new HygateCommand(this));
		LOGGER.atInfo().log("%s setup", this.identifier);
	}

	@Override
	protected void shutdown() // Called when unloading plugins
	{
		LOGGER.atInfo().log("%s shutdown", this.identifier);
	}

	@Override
	protected void start() // Called after loading worlds
	{
		LOGGER.atInfo().log("%s start", this.identifier);
	}
}
