package de.cisco211.hygate.hygateauto.command;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

import de.cisco211.hygate.hygateauto.HygateAutoPlugin;

public class HygateCommand extends AbstractCommandCollection
{
	public HygateCommand(HygateAutoPlugin plugin)
	{
		super("hygate", "Hygate commands."); // /hygate
		this.addSubCommand(new HygateAutoCommand(plugin));
	}
}
