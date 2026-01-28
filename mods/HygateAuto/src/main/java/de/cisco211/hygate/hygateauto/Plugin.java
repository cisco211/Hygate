package de.cisco211.hygate.hygateauto;

import javax.annotation.Nonnull;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import de.cisco211.hygate.hygateauto.command.Command;

public class Plugin extends JavaPlugin
{
	protected static final String LANG_KEY = "hygate_auto";

	protected static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	public final boolean debug = true;

	public final Generator generator;

	public final String identifier;

	/**
	 * <b>Constructor</b>
	 * <br/>
	 * Called after plugin being loaded in.
	 * @param init @Nonnull JavaPluginInit
	 */
	public Plugin(@Nonnull JavaPluginInit init)
	{
		super(init);
		this.identifier = this.getName(); // C: Must happen at very first
		if (debug)
			LOGGER.atInfo().log("%s:Plugin constructor", this.identifier);

		this.generator = new Generator(this);
		generator.create();
	}

	/**
	 * <b>Setup</b>
	 * <br/>
	 * Called after asset monitor.
	 */
	@Override
	protected void setup()
	{
		this.getCommandRegistry().registerCommand(new Command(this));
		if (debug)
			LOGGER.atInfo().log("%s:Plugin setup", this.identifier);
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
	}
}
