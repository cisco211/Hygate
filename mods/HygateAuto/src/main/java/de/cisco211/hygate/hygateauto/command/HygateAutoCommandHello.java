package de.cisco211.hygate.hygateauto.command;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import de.cisco211.hygate.hygateauto.HygateAutoPlugin;
import de.cisco211.hygate.hygateauto.MessageEx;

public class HygateAutoCommandHello extends AbstractPlayerCommand
{
	protected final HygateAutoPlugin plugin;

	public HygateAutoCommandHello(HygateAutoPlugin plugin)
	{
		var paramPlugin = Objects.requireNonNull(plugin.getManifest().getName());
		var description = Objects.requireNonNull(MessageEx.lngInfo("command.hello.description").param("plugin", paramPlugin).toString());
		super("hello", description); // /hygate auto hello
		this.plugin = plugin;
	}

	@Override
	protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> entityStore, @Nonnull Ref<EntityStore> entityRef, @Nonnull PlayerRef playerRef, @Nonnull World world)
	{
		var paramPlugin = Objects.requireNonNull(plugin.getManifest().getName());
		ctx.sendMessage(MessageEx.lngInfo("command.hello.output").param("plugin", paramPlugin));
	}
}
