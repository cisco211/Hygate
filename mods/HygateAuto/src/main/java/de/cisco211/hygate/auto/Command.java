package de.cisco211.hygate.auto;

import java.util.Objects;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

import de.cisco211.hygate.auto.command.Collection;

/**
 * <b>Command</b>
 * <br/>
 * Hygate command {@code /hygate}.
 */
public class Command extends AbstractCommandCollection
{
	/**
	 * Constructor
	 * @param plugin {@link Plugin}
	 */
	public Command(Plugin plugin)
	{
		var description = Objects.requireNonNull(MessageEx.lngInfo("commands").toString());
		super("hygate", description); // /hygate
		this.addSubCommand(new Collection(plugin));
	}
}
