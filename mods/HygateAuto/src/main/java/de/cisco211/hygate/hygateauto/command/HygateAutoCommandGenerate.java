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

public class HygateAutoCommandGenerate extends AbstractPlayerCommand
{
	protected final HygateAutoPlugin plugin;

	public HygateAutoCommandGenerate(HygateAutoPlugin plugin)
	{
		var paramPlugin = Objects.requireNonNull(plugin.getManifest().getName());
		var description = Objects.requireNonNull(plugin.translate("command.generate.description").param("plugin", paramPlugin).toString());
		super("generate", description); // /hygate auto generate
		this.plugin = plugin;
	}

	@Override
	protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> entityStore, @Nonnull Ref<EntityStore> entityRef, @Nonnull PlayerRef playerRef, @Nonnull World world)
	{
		if (ctx.sender().hasPermission("OP")) // C: Not sure yet if its admin only or not.
		{
			var result =
				plugin.generator.generate()
				? plugin.translate("command.generate.success")
				: plugin.translate("command.generate.failed")
			;
			ctx.sendMessage(result);
		}
		else
			ctx.sendMessage(plugin.translate("command.generate.no_permission"));
	}
}
