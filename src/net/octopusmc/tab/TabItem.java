package net.octopusmc.tab;

import java.util.UUID;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;

public class TabItem {

	protected UUID     uuid;
	protected String   name;
	protected String   skin;
	protected int      ping;
	protected GameMode gamemode;

	protected Object profile;

	/**
	 * Constructs a new TabItem
	 */
	public TabItem(@Nonnull UUID uuid, @Nonnull String name, @Nullable String skin, @Nonnegative int ping, @Nonnull GameMode mode) {
		if (name.length() > 16) {
			throw new IllegalArgumentException("name can only be 16 characters long");
		}
		if (skin != null && skin.length() > 16) {
			throw new IllegalArgumentException("skin can only be 16 characters long");
		}

		this.uuid = uuid;
		this.name = name;
		this.skin = skin;
		this.ping = ping;
		this.gamemode = mode;
	}

	/**
	 * Constructs a new TabItem
	 */
	public TabItem(@Nonnull String name, @Nullable String skin, @Nonnegative int ping, @Nonnull GameMode mode) {
		this(UUID.randomUUID(), name, skin, ping, mode);
	}

	/**
	 * Constructs a new TabItem
	 */
	public TabItem(@Nonnull String name, @Nonnegative int ping, @Nonnull GameMode mode) {
		this(name, name, ping, mode);
	}

	/**
	 * Constructs a new TabItem
	 */
	public TabItem(@Nonnull String name) {
		this(name, 0, Bukkit.getDefaultGameMode());
	}

	protected Object toPacket(final Tab tab, final int action) {
		if (profile == null) {
			try {
				profile = NMSClass.GameProfile.getConstructor(UUID.class, String.class).newInstance(this.uuid, this.name);

				if (skin != null) {
					Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("TabListAPI"), new Runnable() {
						@Override
						public void run() {
							try {
								Object cache = NMSClass.TileEntitySkull.getDeclaredField("skinCache").get(null);

								Object skinProfile = NMSClass.LoadingCache.getDeclaredMethod("getUnchecked", Object.class).invoke(cache, (Object) skin.toLowerCase());

								TransformerAccessUtil.setAccessible(NMSClass.GameProfile.getDeclaredField("id")).set(skinProfile, uuid);
								TransformerAccessUtil.setAccessible(NMSClass.GameProfile.getDeclaredField("name")).set(skinProfile, name);

								profile = skinProfile;

								TabAPI.updateTab(tab.player);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (Reflection.getVersion().contains("1_7")) {
			try {
				Object packet = NMSClass.PacketPlayOutPlayerInfo.newInstance();
				TransformerAccessUtil.setAccessible(NMSClass.PacketPlayOutPlayerInfo.getDeclaredField("action")).set(packet, action);
				TransformerAccessUtil.setAccessible(NMSClass.PacketPlayOutPlayerInfo.getDeclaredField("username")).set(packet, name);
				TransformerAccessUtil.setAccessible(NMSClass.PacketPlayOutPlayerInfo.getDeclaredField("gamemode")).set(packet, gamemode.ordinal());
				TransformerAccessUtil.setAccessible(NMSClass.PacketPlayOutPlayerInfo.getDeclaredField("ping")).set(packet, ping);
				TransformerAccessUtil.setAccessible(NMSClass.PacketPlayOutPlayerInfo.getDeclaredField("player")).set(packet, profile);
				return packet;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Object gMode = NMSClass.EnumGamemode.getDeclaredMethod("getById", int.class).invoke(null, gamemode.ordinal());
				Object packet = NMSClass.PlayerInfoData.getConstructor(NMSClass.PacketPlayOutPlayerInfo, NMSClass.GameProfile, int.class, NMSClass.EnumGamemode, NMSClass.IChatBaseComponent).newInstance(null, profile, ping, gMode, Util.serializeChat(TabAPI.convertJSON(name)));
				return packet;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}