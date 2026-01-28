package de.cisco211.hygate.hygateauto.command;

import java.util.Objects;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

import de.cisco211.hygate.hygateauto.HygateAutoPlugin;
import de.cisco211.hygate.hygateauto.MessageEx;

public class HygateAutoCommand extends AbstractCommandCollection
{
	public HygateAutoCommand(HygateAutoPlugin plugin)
	{
		var paramPlugin = Objects.requireNonNull(plugin.getManifest().getName());
		var description = Objects.requireNonNull(MessageEx.lngInfo("commands1").param("plugin", paramPlugin).toString());
		super("auto", description); // /hygate auto
		this.addSubCommand(new HygateAutoCommandGenerate(plugin));
		this.addSubCommand(new HygateAutoCommandHello(plugin));
		this.addSubCommand(new HygateAutoCommandItems(plugin));
		this.addSubCommand(new HygateAutoCommandWorlds(plugin));
	}
}
