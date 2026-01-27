package de.cisco211.hygate.hygateauto.command;

import java.io.IOException;
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

public class HygateAutoCommandWorlds extends AbstractPlayerCommand
{
	protected final HygateAutoPlugin plugin;

	public HygateAutoCommandWorlds(HygateAutoPlugin plugin)
	{
		var paramPlugin = Objects.requireNonNull(plugin.getManifest().getName());
		var description = Objects.requireNonNull(plugin.translate("command.worlds.description").param("plugin", paramPlugin).toString());
		super("worlds", description); // /hygate auto worlds
		this.plugin = plugin;
	}

	@Override
	protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> entityStore, @Nonnull Ref<EntityStore> entityRef, @Nonnull PlayerRef playerRef, @Nonnull World world)
	{
		try
		{
			var list = plugin.generator.worldsFormatted();
			var count = Objects.requireNonNull(String.valueOf(list.size()));
			var worlds = Objects.requireNonNull(String.join(", ", plugin.generator.worldsFormatted()));
			ctx.sendMessage(plugin.translate("command.worlds.success").param("count", count).param("worlds", worlds));
		}
		catch (IOException e)
		{
			var error = Objects.requireNonNull(e.toString());
			ctx.sendMessage(plugin.translate("command.worlds.error").param("error", error));
		}
	}
}
