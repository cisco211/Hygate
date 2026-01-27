package de.cisco211.hygate.hygateauto;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import de.cisco211.hygate.hygateauto.command.HygateCommand;

public class HygateAutoPlugin extends JavaPlugin
{
	protected static final String LANG_KEY = "hygate_auto";

	protected static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	public final boolean debug = true;

	public final HygateAutoGenerator generator;

	public final String identifier;

	public HygateAutoPlugin(@Nonnull JavaPluginInit init) // Called after plugin being loaded in
	{
		super(init);
		this.identifier = this.getName(); // C: Must happen at very first
		if (debug)
			LOGGER.atInfo().log("%s:Plugin constructor", this.identifier);

		this.generator = new HygateAutoGenerator(this);
		generator.create();
	}

	// Setup
	// C: Called after asset monitor.
	@Override
	protected void setup()
	{
		this.getCommandRegistry().registerCommand(new HygateCommand(this));
		if (debug)
			LOGGER.atInfo().log("%s:Plugin setup", this.identifier);
	}

	// Shutdown
	// C: Called when unloading plugins.
	@Override
	protected void shutdown()
	{
		if (debug)
			LOGGER.atInfo().log("%s:Plugin shutdown", this.identifier);
	}

	// Start
	// C: Called after loading worlds.
	@Override
	protected void start()
	{
		if (debug)
			LOGGER.atInfo().log("%s:Plugin start", this.identifier);
	}

	// Translate
	public @Nonnull Message translate(String key)
	{
		return Message.translation(LANG_KEY + "." + Objects.requireNonNull(key));
	}
}
