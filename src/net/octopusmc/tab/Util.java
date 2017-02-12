package net.octopusmc.tab;

import org.bukkit.entity.Player;

public class Util {

	public static Object serializeChat(String msg) {
		try {
			return NMSClass.ChatSerializer.getDeclaredMethod("a", String.class).invoke(null, msg);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void sendPacket(Player p, Object packet) throws Exception {
		if (p == null || packet == null) return;
		Object handle = Reflection.getHandle(p);
		Object connection = TransformerAccessUtil.setAccessible(handle.getClass().getDeclaredField("playerConnection")).get(handle);
		Reflection.getMethod(connection.getClass(), "sendPacket", new Class[0]).invoke(connection, new Object[] { packet });
	}

}