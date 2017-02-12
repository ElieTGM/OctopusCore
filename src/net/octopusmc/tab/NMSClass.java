package net.octopusmc.tab;

public class NMSClass {

	private static   boolean init    = false;
	protected static int     version = 170;

	public static Class<?> PacketPlayOutPlayerListHeaderFooter;

	public static Class<?> IChatBaseComponent;
	public static Class<?> ChatSerializer;

	public static    Class<?> PacketPlayOutPlayerInfo;
	public static    Class<?> PlayerInfoData;
	protected static Class<?> EnumPlayerInfoAction;

	public static Class<?> GameProfile;
	public static Class<?> EnumGamemode;

	public static Class<?> TileEntitySkull;

	public static Class<?> LoadingCache;

	static {
		if (!init) {
			if (Reflection.getVersion().contains("1_7")) {
				version = 170;
			}
			if (Reflection.getVersion().contains("1_8")) {
				version = 180;
			}
			if (Reflection.getVersion().contains("1_8_R1")) {
				version = 181;
			}
			if (Reflection.getVersion().contains("1_8_R2")) {
				version = 182;
			}
			if (Reflection.getVersion().contains("1_8_R3")) {
				version = 183;
			}
			if (Reflection.getVersion().contains("1_9_R1")) {
				version = 184;
			}

			if (version >= 180) {
				PacketPlayOutPlayerListHeaderFooter = Reflection.getNMSClass("PacketPlayOutPlayerListHeaderFooter");
			}

			IChatBaseComponent = Reflection.getNMSClass("IChatBaseComponent");
			if (version < 181) {
				ChatSerializer = Reflection.getNMSClass("ChatSerializer");
			} else {
				ChatSerializer = Reflection.getNMSClass("IChatBaseComponent$ChatSerializer");
			}

			PacketPlayOutPlayerInfo = Reflection.getNMSClass("PacketPlayOutPlayerInfo");
			if (version >= 180) {
				PlayerInfoData = Reflection.getNMSClass("PacketPlayOutPlayerInfo$PlayerInfoData");
			}

			if (version <= 181) {
				EnumPlayerInfoAction = Reflection.getNMSClass("EnumPlayerInfoAction");
			} else {
				EnumPlayerInfoAction = Reflection.getNMSClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
			}

			try {
				if (version < 180) {
					GameProfile = Class.forName("net.minecraft.util.com.mojang.authlib.GameProfile");
				} else {
					GameProfile = Class.forName("com.mojang.authlib.GameProfile");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			if (version < 182) {
				EnumGamemode = Reflection.getNMSClass("EnumGamemode");
			} else {
				EnumGamemode = Reflection.getNMSClass("WorldSettings$EnumGamemode");
			}

			TileEntitySkull = Reflection.getNMSClass("TileEntitySkull");

			try {
				if (version < 180) {
					LoadingCache = Class.forName("net.minecraft.util.com.google.common.cache.LoadingCache");
				} else {
					LoadingCache = Class.forName("com.google.common.cache.LoadingCache");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			init = true;
		}
	}

}