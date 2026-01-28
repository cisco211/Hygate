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
 * <b>Worlds</b>
 * <br/>
 * Hygate auto worlds command {@code /hygate auto worlds}.
 */
public class Worlds extends AbstractPlayerCommand
{
	/**
	 * <b>Plugin</b>
	 */
	protected final Plugin plugin;

	/**
	 * <b>Constructor</b>
	 * @param plugin {@link Plugin}
	 */
	public Worlds(Plugin plugin)
	{
		var paramPlugin = Objects.requireNonNull(plugin.getManifest().getName());
		var description = Objects.requireNonNull(MessageEx.lngInfo("command.worlds.description").param("plugin", paramPlugin).toString());
		super("worlds", description); // /hygate auto worlds
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
	 * @param world {@link World}
	 */
	@Override
	protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> entityStore, @Nonnull Ref<EntityStore> entityRef, @Nonnull PlayerRef playerRef, @Nonnull World world)
	{
		try
		{
			var list = plugin.generator.worldsFormatted();
			if (list.isEmpty())
			{
				ctx.sendMessage(MessageEx.lngWarn("command.items.empty"));
				return;
			}
			var count = Objects.requireNonNull(String.valueOf(list.size()));
			var worlds = Objects.requireNonNull(String.join(", ", list));
			ctx.sendMessage(MessageEx.lngOk("command.worlds.success").param("count", count).param("worlds", worlds));
		}
		catch (IOException e)
		{
			var error = Objects.requireNonNull(e.toString());
			ctx.sendMessage(MessageEx.lngErr("command.worlds.error").param("error", error));
		}
	}
}
