package de.cisco211.hygate.hygateauto.command;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

import de.cisco211.hygate.hygateauto.HygateAutoPlugin;

public class HygateAutoCommandCollection extends AbstractCommandCollection
{
	public HygateAutoCommandCollection(HygateAutoPlugin plugin)
	{
		super("auto", "HygateAuto commands."); // /hygate auto
		this.addSubCommand(new HygateAutoCommandGenerate(plugin));
		this.addSubCommand(new HygateAutoCommandHello(plugin));
	}
}
