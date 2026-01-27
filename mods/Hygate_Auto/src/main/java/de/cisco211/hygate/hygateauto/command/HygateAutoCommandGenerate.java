package de.cisco211.hygate.hygateauto.command;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
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
		super("generate", plugin.identifier + " generate command."); // /hygate auto generate
		this.plugin = plugin;
	}

	@Override
	protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> entityStore, @Nonnull Ref<EntityStore> entityRef, @Nonnull PlayerRef playerRef, @Nonnull World world)
	{
		if (ctx.sender().hasPermission("OP"))
		{
			if (this.plugin.generate())
				ctx.sendMessage(Message.raw("Hygate's generated!"));
			else
				ctx.sendMessage(Message.raw("Error: Failed to generate Hygate's!"));
		}
		else
			ctx.sendMessage(Message.raw("Error: No permission to use this command!"));
	}
}
