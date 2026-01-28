package de.cisco211.hygate.auto.command.collection;

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

import de.cisco211.hygate.auto.Plugin;
import de.cisco211.hygate.auto.MessageEx;

/**
 * <b>Generate</b>
 * <br/>
 * Hygate auto generate command {@code /hygate auto generate}.
 */
public class Generate extends AbstractPlayerCommand
{
	/**
	 * <b>Plugin</b>
	 */
	protected final Plugin plugin;

	/**
	 * <b>Constructor</b>
	 * @param plugin {@link Plugin}
	 */
	public Generate(Plugin plugin)
	{
		var paramPlugin = Objects.requireNonNull(plugin.getManifest().getName());
		var description = Objects.requireNonNull(MessageEx.lngInfo("command.generate.description").param("plugin", paramPlugin).toString());
		super("generate", description); // /hygate auto generate
		this.plugin = plugin;
	}

	/**
	 * <b>Execute</b>
	 * <br/>
	 * Executes the command.
	 * @param ctx {@link CommandContext}
	 * @param entityStore {@link Store} &lt;{@link EntityStore}&gt;
	 * @param entityRef {@link Ref} &lt;{@link EntityStore}&gt;
	 * @param playerRef {@link PlayerRef}
	 * @param world World
	 */
	@Override
	protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> entityStore, @Nonnull Ref<EntityStore> entityRef, @Nonnull PlayerRef playerRef, @Nonnull World world)
	{
		if (ctx.sender().hasPermission("OP")) // C: Not sure yet if its admin only or not.
		{
			try
			{
				var result =
					plugin.generator.generate()
					? MessageEx.lngOk("command.generate.success")
					: MessageEx.lngErr("command.generate.failed")
				;
				ctx.sendMessage(result);
			}
			catch (IOException e)
			{
				var message = Objects.requireNonNull(e.getMessage());
				var result = MessageEx.lngErr("command.generate.failed_with").param("message", message);
				ctx.sendMessage(result);
			}
		}
		else
			ctx.sendMessage(MessageEx.lngErr("command.generate.no_permission"));
	}
}
