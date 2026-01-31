package de.cisco211.hygate.auto;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * <b>Item</b>
 * <br/>
 * Represents a modular Hytale Item structure using GSON.
 */
public class Item
{
	/**
	 * <b>File begin</b>
	 */
	public static final String FILE_BEGIN = "Hygate_Manual_";

	/**
	 * <b>File end</b>
	 */
	public static final String FILE_END = ".json";

	/**
	 * <b>Language extension</b>
	 */
	public static final String LANG_EXT = ".lang";

	/**
	 * <b>Language key</b>
	 * <br/>
	 * File name used for all embedded translations in this plugin.
	 */
	public static final String LANG_KEY = "hygate_manual";

	/**
	 * <b>Object</b>
	 * <br/>
	 * The JSON object holding the item data.
	 */
	protected JsonObject object;

	/**
	 * <b>World</b>
	 * <br/>
	 * World that defines where the portal will point to.
	 */
	protected String world = "";

	/**
	 * <b>World gen type</b>
	 * <br/>
	 * World generator type.
	 */
	protected String worldGenType = "";

	/**
	 * <b>Constructor</b>
	 */
	public Item()
	{}

	/**
	 * <b>Get path</b>
	 * <br/>
	 * Get JSON element from given path in input.
	 * @param input {@link JsonObject}
	 * @param path {@link String}
	 * @return {@link JsonElement} Found element or null, when not found.
	 */
	public static JsonElement getPath(JsonObject input, String path)
	{
		if (input == null || path == null || path.isEmpty())
		{
			return null;
		}
		String[] parts = path.split("\\.");
		JsonElement current = input;
		for (String part : parts)
		{
			if (current == null || current.isJsonNull())
			{
				return null;
			}
			if (current.isJsonObject())
			{
				current = current.getAsJsonObject().get(part);
			}
			else if (current.isJsonArray())
			{
				try
				{
					int index = Integer.parseInt(part);
					JsonArray array = current.getAsJsonArray();
					current = (index >= 0 && index < array.size()) ? array.get(index) : null;
				}
				catch (NumberFormatException e)
				{
					return null;
				}
			}
			else
			{
				return null;
			}
		}
		return current;
	}

	/**
	 * <b>Item</b>
	 * <br/>
	 * Extracts and sets members from members found in given, object.
	 * @param object {@link JsonObject}
	 * @return {@link Item}
	 */
	public @Nonnull Item item(@Nonnull JsonObject object)
	{
		// Extract world name
		var worldName = getPath(object, "BlockType.Interactions.CollisionEnter.Interactions.0.WorldName");
		if (worldName != null)
		{
			this.world = worldName.getAsString();
		}

		// Extract world gen type
		var worldGenType = getPath(object, "BlockType.Interactions.CollisionEnter.Interactions.0.WorldName");
		if (worldGenType != null)
		{
			this.worldGenType = worldGenType.getAsString();
		}

		// Done
		return this;
	}

	/**
	 * <b>Item to world</b>
	 * <br/>
	 * Turns an item (file) into a world.
	 * @param item {@link String}
	 * @return {@link String}
	 */
	public static @Nonnull String itemToWorld(@Nonnull String item)
	{
		var world = item
			.replace(Item.FILE_BEGIN, "")
			.replace(Item.FILE_END, "")
		;
		return Objects.requireNonNull(world);
	}

	/**
	 * <b>Build</b>
	 * <br/>
	 * Build {@code JsonObject} from given instance state.
	 * @return {@link JsonObject}
	 * @throws IllegalStateException If required world or worldGenType is not set.
	 */
	public @Nonnull JsonObject build() throws IllegalStateException
	{
		// Initialize/reset object
		object = new JsonObject();

		// Require world
		if (world.isEmpty())
		{
			throw new IllegalStateException("World not set!");
		}

		// Require worldGenType
		if (worldGenType.isEmpty())
		{
			throw new IllegalStateException("World generator type not set!");
		}

		// TranslationProperties
		{
			JsonObject translationProperties = new JsonObject();
			{
				// C: Temporary hack to make it work without translation stuff.
				String name = worldToLabel(Objects.requireNonNull(world));
				translationProperties.addProperty("Name", String.format("Hygate: '%s'", name));
				translationProperties.addProperty("Description", String.format("Teleports you to the world '%s'", name));
				// TODO: Figure out why the hell translations are not working at all.
				// {
				// 	String name = LANG_KEY + ".items." + FILE_BEGIN + world + ".name";
				// 	translationProperties.addProperty("Name", name);
				// }
				// {
				// 	String description = LANG_KEY + ".items." + FILE_BEGIN + world + ".description";
				// 	translationProperties.addProperty("Description", description);
				// }
			}
			object.add("TranslationProperties", translationProperties);
		}

		// Icon
		{
			String icon = "Icons/ItemsGenerated/Portal_Device.png";
			object.addProperty("Icon", icon);
		}

		// Categories
		{
			JsonArray categories = new JsonArray();
			{
				categories.add("Blocks.Portals");
			}
			object.add("Categories", categories);
		}

		// BlockType
		{
			JsonObject blockType = new JsonObject();
			{
				// DrawType
				{
					String drawType = "Model";
					blockType.addProperty("DrawType", drawType);
				}

				// Material
				{
					String material = "Solid";
					blockType.addProperty("Material", material);
				}

				// Opacity
				{
					String opacity = "Transparent";
					blockType.addProperty("Opacity", opacity);
				}

				// CustomModel
				{
					String customModel = "Blocks/Miscellaneous/Platform_MagicInactive.blockymodel";
					blockType.addProperty("CustomModel", customModel);
				}

				// CustomModelTexture
				{
					JsonArray customModelTexture = new JsonArray();
					{
						JsonObject entry = new JsonObject();
						{
							{
								String texture = "Blocks/Miscellaneous/Platform_Magic_Blue2.png";
								entry.addProperty("Texture", texture);
							}
							{
								Number weight = 1;
								entry.addProperty("Weight", weight);
							}
						}
						customModelTexture.add(entry);
					}
					blockType.add("CustomModelTexture", customModelTexture);
				}

				// HitboxType
				{
					String hitboxType = "Pad_Portal";
					blockType.addProperty("HitboxType", hitboxType);
				}

				// BlockParticleSetId
				{
					String blockParticleSetId = "Stone";
					blockType.addProperty("BlockParticleSetId", blockParticleSetId);
				}

				// BlockSoundSetId
				{
					String blockSoundSetId = "Stone";
					blockType.addProperty("BlockSoundSetId", blockSoundSetId);
				}

				// VariantRotation
				{
					String variantRotation = "NESW";
					blockType.addProperty("VariantRotation", variantRotation);
				}

				// Flags
				{
					JsonObject flags = new JsonObject();
					{
						boolean isUsable = false;
						flags.addProperty("IsUsable", isUsable);
					}
					blockType.add("Flags", flags);
				}

				// Gathering
				{
					JsonObject gathering = new JsonObject();
					{
						JsonObject breaking = new JsonObject();
						{
							String gatherType = "Unbreakable";
							breaking.addProperty("GatherType", gatherType);
						}
						gathering.add("Breaking", breaking);
					}
					blockType.add("Gathering", gathering);
				}

				// Light
				{
					JsonObject light = new JsonObject();
					{
						{
							String color = "#333333";
							light.addProperty("Color", color);
						}
						{
							Number radius = 3;
							light.addProperty("Radius", radius);
						}
					}
					blockType.add("Light", light);
				}

				// AmbientSoundEventId
				{
					String ambientSoundEventId = "SFX_Portal_Neutral";
					blockType.addProperty("AmbientSoundEventId", ambientSoundEventId);
				}

				// Particles
				{
					JsonArray particles = new JsonArray();
					{
						JsonObject entry = new JsonObject();
						{
							{
								String systemId = "MagicPortal";
								entry.addProperty("SystemId", systemId);
							}
							{
								JsonObject positionOffset = new JsonObject();
								{
									Number y = 2.0;
									positionOffset.addProperty("Y", y);
								}
								entry.add("PositionOffset", positionOffset);
							}
							{
								Number scale = 0.8;
								entry.addProperty("Scale", scale);
							}
						}
						particles.add(entry);
					}
					blockType.add("Particles", particles);
				}

				// Interactions
				{
					JsonObject interactions = new JsonObject();
					{
						// CollisionEnter
						JsonObject collisionEnter = new JsonObject();
						{
							// Interactions
							JsonArray interactions2 = new JsonArray();
							{
								// {}
								JsonObject entry = new JsonObject();
								{
									// Type
									{
										String type = "HubPortal";
										entry.addProperty("Type", type);
									}

									// WorldName
									{
										String worldName = world;
										entry.addProperty("WorldName", worldName);
									}

									// WorldGenType
									{
										String worldGenType = this.worldGenType;
										entry.addProperty("WorldGenType", worldGenType);
									}

									// Next
									{
										JsonObject next = new JsonObject();
										{
											{
												String type = "Simple";
												next.addProperty("Type", type);
											}
											{
												JsonObject effects = new JsonObject();
												{
													String localSoundEventId = "SFX_Portal_Neutral_Teleport_Local";
													effects.addProperty("LocalSoundEventId", localSoundEventId);
												}
												next.add("Effects", effects);
											}
										}
										entry.add("Next", next);
									}
								}
								interactions2.add(entry);
							}
							collisionEnter.add("Interactions", interactions2);
						}
						interactions.add("CollisionEnter", collisionEnter);
					}
					blockType.add("Interactions", interactions);
				}

				// CustomModelAnimation
				{
					String customModelAnimation = "Blocks/Miscellaneous/Platform_Magic_Idle.blockyanim";
					blockType.addProperty("CustomModelAnimation", customModelAnimation);
				}
			}
			object.add("BlockType", blockType);
		}

		// PlayerAnimationsId
		{
			String playerAnimationsId = "Block";
			object.addProperty("PlayerAnimationsId", playerAnimationsId);
		}

		// Tags
		{
			JsonObject tags = new JsonObject();
			{
				JsonArray type = new JsonArray();
				{
					type.add("Portal");
					type.add("HubPortal");
				}
				tags.add("Type", type);
			}
			object.add("Tags", tags);
		}

		// Quality
		{
			String quality = "Uncommon";
			object.addProperty("Quality", quality);
		}

		// IconProperties
		{
			JsonObject iconProperties = new JsonObject();
			{
				{
					Number scale = 0.35;
					iconProperties.addProperty("Scale", scale);
				}
				{
					JsonArray rotation = new JsonArray();
					{
						rotation.add(22.5);
						rotation.add(45.0);
						rotation.add(22.5);
					}
					iconProperties.add("Rotation", rotation);
				}
				{
					JsonArray translation = new JsonArray();
					{
						translation.add(0.0);
						translation.add(-13.5);
					}
					iconProperties.add("Translation", translation);
				}
			}
			object.add("IconProperties", iconProperties);
		}

		// ItemSoundSetId
		{
			String itemSoundSetId = "ISS_Blocks_Stone";
			object.addProperty("ItemSoundSetId", itemSoundSetId);
		}

		// Done
		return Objects.requireNonNull(object);
	}

	/**
	 * <b>Set world</b>
	 * <br/>
	 * Set world that defines where the portal will point to.
	 * @param world {@link String}
	 * @return {@link Item}
	 */
	public @Nonnull Item setWorld(@Nonnull String world)
	{
		this.world = world;
		return this;
	}

	/**
	 * <b>Set world gen type</b>
	 * <br/>
	 * Set world generator type.
	 * @param worldGenType {@link String}
	 * @return {@link Item}
	 */
	public @Nonnull Item setWorldGenType(@Nonnull String worldGenType)
	{
		this.worldGenType = worldGenType;
		return this;
	}

	/**
	 * <b>World to label</b>
	 * <br/>
	 * Turns a world into a label.
	 * @param world {@link String}
	 * @return {@link String}
	 */
	public static @Nonnull String worldToLabel(@Nonnull String world)
	{
		String[] words = world.split("[_-]");
		StringBuilder sb = new StringBuilder();
		for (String w : words)
		{
			if (!w.isEmpty())
			{
				sb.append(Character.toUpperCase(w.charAt(0)))
				.append(w.substring(1).toLowerCase())
				.append(" ");
			}
		}
		return Objects.requireNonNull(sb.toString().trim());
	}

}
