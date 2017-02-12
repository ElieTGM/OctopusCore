package net.octopusmc.tab;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Tab {

	protected static Object EMPTY_COMPONENT;

	static {
		try {
			EMPTY_COMPONENT = Reflection.getNMSClass("ChatComponentText").getConstructor(String.class).newInstance("");
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	protected final Player player;

	// Header + Footer
	protected String[] header;
	protected String[] footer;

	// Items
	protected List<TabItem> items = new ArrayList<>();

	protected Tab(Player p) {
		this.player = p;
	}

	public void clearItems() {
		updateItems(4);
	}

	public void updateItems() {
		updateItems(0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateItems(int action) {
		try {
			List<Object> itemPackets = new ArrayList<>();
			for (TabItem item : items) {
				itemPackets.add(item.toPacket(this, action));
			}

			List<Object> packets = new ArrayList<>();

			if (NMSClass.version < 180) {
				packets.addAll(itemPackets);
			} else {
				Object playerInfoPacket = NMSClass.PacketPlayOutPlayerInfo.newInstance();
				TransformerAccessUtil.setAccessible(NMSClass.PacketPlayOutPlayerInfo.getDeclaredField("a")).set(playerInfoPacket, NMSClass.EnumPlayerInfoAction.getEnumConstants()[action]);
				List<Object> dataList = (List) TransformerAccessUtil.setAccessible(NMSClass.PacketPlayOutPlayerInfo.getDeclaredField("b")).get(playerInfoPacket);
				dataList.addAll(itemPackets);
				packets.add(playerInfoPacket);
			}

			for (Object packet : packets) {
				Util.sendPacket(player, packet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateHeaderFooter() {
		if (header == null && footer == null) { return; }
		try {
			Object packet = NMSClass.PacketPlayOutPlayerListHeaderFooter.getConstructor(NMSClass.IChatBaseComponent).newInstance(new Object[] { null });

			Object serHeader = header == null ? EMPTY_COMPONENT : Util.serializeChat(mergeJSON(header));
			Object serFooter = footer == null ? EMPTY_COMPONENT : Util.serializeChat(mergeJSON(footer));

			TransformerAccessUtil.setAccessible(NMSClass.PacketPlayOutPlayerListHeaderFooter.getDeclaredField("a")).set(packet, serHeader);
			TransformerAccessUtil.setAccessible(NMSClass.PacketPlayOutPlayerListHeaderFooter.getDeclaredField("b")).set(packet, serFooter);

			Util.sendPacket(player, packet);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private String mergeJSON(String... json) {
		String merged = "";
		for (String s : json) {
			merged += s + ",";
		}
		merged = merged.substring(0, merged.length() - 1);
		return String.format("{\"text\":\"\",\"extra\":[%s]}", merged);
	}

}