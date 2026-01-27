package de.cisco211.hygate.hygateauto;

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
		if (plugin.debug)
			LOGGER.atInfo().log("%s create", plugin.identifier);
		// TODO: Create the autogen pack here.
	}

	// Generate
	// Generates the gateway portal items.
	public boolean generate()
	{
		if (plugin.debug)
			LOGGER.atInfo().log("%s generate", plugin.identifier);
		// TODO: Rebuild items and their translation here.
		return true;
	}
}
