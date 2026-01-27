package de.cisco211.hygate.hygateautoplugin;

import javax.annotation.Nonnull;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

public class HygateAutoPlugin extends JavaPlugin
{
	private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	public HygateAutoPlugin(@Nonnull JavaPluginInit init)
	{
		super(init);
		LOGGER.atInfo().log("Hello from %s version %s", this.getName(), this.getManifest().getVersion().toString());
	}

	@Override
	protected void setup()
	{
		this.getCommandRegistry().registerCommand(new HygateAutoCommand(this.getName(), this.getManifest().getVersion().toString()));
	}
}
