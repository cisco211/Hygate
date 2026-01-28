package de.cisco211.hygate.auto;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.hypixel.hytale.server.core.Message;

/**
 * <b>MessageEx</b>
 * <br/>
 * Helper class with convenient static functions.
 */
public class MessageEx
{
	/**
	 * <b>Constructor</b>
	 * <br/>
	 * Utility class with static functions only.
	 */
	private MessageEx()
	{}

	/**
	 * <b>Language key</b>
	 * <br/>
	 * File name used for all embedded translations in this plugin.
	 */
	protected static final String LANG_KEY = "hygate_auto";

	/**
	 * <b>Lng</b>
	 * <br/>
	 * Translate into language by key.
	 * @param key {@link String}
	 * @return Message
	 */
	public static @Nonnull Message lng(String key)
	{
		return Message.translation(LANG_KEY + "." + Objects.requireNonNull(key));
	}

	/**
	 * <b>LngErr</b>
	 * <br/>
	 * Translate into language as error.
	 * @param key {@link String}
	 * @return Message
	 */
	public static @Nonnull Message lngErr(String key)
	{
		return lng(key).color("#CC0000");
	}

	/**
	 * <b>LngInfo</b>
	 * <br/>
	 * Translate into language as info.
	 * @param key {@link String}
	 * @return Message
	 */
	public static @Nonnull Message lngInfo(String key)
	{
		return lng(key).color("#AAAAFF");
	}

	/**
	 * <b>LngWarn</b>
	 * <br/>
	 * Translate into language as warning.
	 * @param key {@link String}
	 * @return Message
	 */
	public static @Nonnull Message lngWarn(String key)
	{
		return lng(key).color("#FF6600");
	}

	/**
	 * <b>LngOk</b>
	 * <br/>
	 * Translate into language as success.
	 * @param key {@link String}
	 * @return Message
	 */
	public static @Nonnull Message lngOk(String key)
	{
		return lng(key).color("#008800");
	}
}
