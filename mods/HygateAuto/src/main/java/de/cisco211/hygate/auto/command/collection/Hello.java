package de.cisco211.hygate.auto.command.collection;

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
 * <b>Hello</b>
 * <br/>
 * Hygate auto hello command {@code /hygate auto hello}.
 */
public class Hello extends AbstractPlayerCommand
{
	/**
	 * <b>Plugin</b>
	 */
	protected final Plugin plugin;

	/**
	 * <b>Constructor</b>
	 * @param plugin {@link Plugin}
	 */
	public Hello(Plugin plugin)
	{
		var paramPlugin = Objects.requireNonNull(plugin.getManifest().getName());
		var description = Objects.requireNonNull(MessageEx.lngInfo("command.hello.description").param("plugin", paramPlugin).toString());
		super("hello", description); // /hygate auto hello
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
		var paramPlugin = Objects.requireNonNull(plugin.getManifest().getName());
		ctx.sendMessage(MessageEx.lngInfo("command.hello.output").param("plugin", paramPlugin));
	}
}
