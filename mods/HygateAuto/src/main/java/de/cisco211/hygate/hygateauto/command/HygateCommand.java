package de.cisco211.hygate.hygateauto.command;

import java.util.Objects;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

import de.cisco211.hygate.hygateauto.HygateAutoPlugin;
import de.cisco211.hygate.hygateauto.MessageEx;

public class HygateCommand extends AbstractCommandCollection
{
	public HygateCommand(HygateAutoPlugin plugin)
	{
		var description = Objects.requireNonNull(MessageEx.lngInfo("commands0").toString());
		super("hygate", description); // /hygate
		this.addSubCommand(new HygateAutoCommand(plugin));
	}
}
