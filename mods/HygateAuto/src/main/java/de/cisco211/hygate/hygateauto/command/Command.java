package de.cisco211.hygate.hygateauto.command;

import java.util.Objects;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

import de.cisco211.hygate.hygateauto.Plugin;
import de.cisco211.hygate.hygateauto.MessageEx;

public class Command extends AbstractCommandCollection
{
	public Command(Plugin plugin)
	{
		var description = Objects.requireNonNull(MessageEx.lngInfo("commands").toString());
		super("hygate", description); // /hygate
		this.addSubCommand(new Collection(plugin));
	}
}
