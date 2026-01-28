package de.cisco211.hygate.hygateauto.command;

import java.util.Objects;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

import de.cisco211.hygate.hygateauto.Plugin;
import de.cisco211.hygate.hygateauto.MessageEx;
import de.cisco211.hygate.hygateauto.command.collection.Generate;
import de.cisco211.hygate.hygateauto.command.collection.Hello;
import de.cisco211.hygate.hygateauto.command.collection.Items;
import de.cisco211.hygate.hygateauto.command.collection.Worlds;

public class Collection extends AbstractCommandCollection
{
	public Collection(Plugin plugin)
	{
		var paramPlugin = Objects.requireNonNull(plugin.getManifest().getName());
		var description = Objects.requireNonNull(MessageEx.lngInfo("collection").param("plugin", paramPlugin).toString());
		super("auto", description); // /hygate auto
		this.addSubCommand(new Generate(plugin));
		this.addSubCommand(new Hello(plugin));
		this.addSubCommand(new Items(plugin));
		this.addSubCommand(new Worlds(plugin));
	}
}
