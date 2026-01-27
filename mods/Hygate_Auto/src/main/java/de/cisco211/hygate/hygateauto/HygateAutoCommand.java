package de.cisco211.hygate.hygateauto;

import javax.annotation.Nonnull;

import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;

public class HygateAutoCommand extends CommandBase
{
	private final String pluginName;
	private final String pluginVersion;

	public HygateAutoCommand(String pluginName, String pluginVersion)
	{
		super("test", "Prints a test message from the " + pluginName + " plugin.");
		this.setPermissionGroup(GameMode.Adventure);
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
	}

	@Override
	protected void executeSync(@Nonnull CommandContext ctx)
	{
		ctx.sendMessage(Message.raw("Hello from the " + pluginName + " v" + pluginVersion + " plugin!"));
	}
}
