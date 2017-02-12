package net.octopusmc.tab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class TabAPI {

	private static final Map<UUID, Tab> tabMap = new ConcurrentHashMap<>();

	/* Header & Footer */

	/**
	 * Updates the Tablist header
	 *
	 * @param p
	 * @param header lines of the header
	 */
	public static void setHeader(@Nonnull Player p, @Nullable String... header) {
		setHeaderFooter(p, header, getFooter(p));
	}

	/**
	 * @param p {@link Player}
	 * @return The header, or null
	 */
	@Nullable
	public static String[] getHeader(@Nonnull Player p) {
		Tab tab = tabMap.get(p.getUniqueId());
		return tab != null ? tab.header : null;
	}

	/**
	 * Updates the Tablist footer
	 *
	 * @param p      {@link Player}
	 * @param footer lines of the footer
	 */
	public static void setFooter(@Nonnull Player p, @Nullable String... footer) {
		setHeaderFooter(p, getHeader(p), footer);
	}

	/**
	 * @param p {@link Player}
	 * @return The footer, or null
	 */
	@Nullable
	public static String[] getFooter(Player p) {
		Tab tab = tabMap.get(p.getUniqueId());
		return tab != null ? tab.footer : null;
	}

	/**
	 * Updates the header and footer
	 *
	 * @param p      {@link Player}
	 * @param header the header
	 * @param footer the footer
	 */
	public static void setHeaderFooter(@Nonnull Player p, @Nullable String header, @Nullable String footer) {
		setHeaderFooter(p, header == null ? null : new String[] { header }, footer == null ? null : new String[] { footer });
	}

	/**
	 * Updates the header and footer
	 *
	 * @param p      {@link Player}
	 * @param header lines of the header
	 * @param footer lines of the footer
	 */
	public static void setHeaderFooter(@Nonnull Player p, @Nullable String[] header, @Nullable String[] footer) {
		if (!p.isOnline()) { return; }
		if (!tabMap.containsKey(p.getUniqueId())) {
			tabMap.put(p.getUniqueId(), new Tab(p));
		}
		Tab tab = tabMap.get(p.getUniqueId());
		tab.header = convertJSON(header);
		tab.footer = convertJSON(footer);
		tab.updateHeaderFooter();
	}

	/* Content */

	/**
	 * Adds a TabItem
	 *
	 * @param p     {@link Player}
	 * @param items item to add
	 */
	public static void addItem(@Nonnull Player p, @Nonnull TabItem... items) {
		if (!p.isOnline()) { return; }
		if (!tabMap.containsKey(p.getUniqueId())) {
			tabMap.put(p.getUniqueId(), new Tab(p));
		}
		Tab tab = tabMap.get(p.getUniqueId());
		tab.items.addAll(Arrays.asList(items));
		tab.updateItems();
	}

	/**
	 * Sets the displayed TabItems
	 *
	 * @param p     {@link Player}
	 * @param items TabItems collection
	 */
	public static void setItems(@Nonnull Player p, @Nonnull Collection<TabItem> items) {
		if (!p.isOnline()) { return; }
		if (!tabMap.containsKey(p.getUniqueId())) {
			tabMap.put(p.getUniqueId(), new Tab(p));
		}
		Tab tab = tabMap.get(p.getUniqueId());

		tab.clearItems();

		tab.items.clear();
		tab.items.addAll(items);
		tab.updateItems();
	}

	/**
	 * @param p {@link Player}
	 * @return TabItems collection
	 */
	@Nonnull
	public static Collection<TabItem> getItems(@Nonnull Player p) {
		Tab tab = tabMap.get(p.getUniqueId());
		return tab != null ? new ArrayList<>(tab.items) : new ArrayList<TabItem>();
	}

	/**
	 * Removes an item
	 *
	 * @param p     {@link Player}
	 * @param items TabItems to remove
	 */
	public static void removeItem(@Nonnull Player p, @Nonnull TabItem... items) {
		Tab tab = tabMap.get(p.getUniqueId());
		if (tab != null) {
			tab.clearItems();

			tab.items.removeAll(Arrays.asList(items));
			tab.updateItems();
		}
	}

	/**
	 * Adds the default items to the list (online players)
	 *
	 * @param player {@link Player}
	 */
	public static void fillDefaultItems(@Nonnull Player player) {
		List<TabItem> items = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (player.canSee(p)) {
				items.add(new TabItem(p.getUniqueId(), p.getPlayerListName(), p.getName(), 0, p.getGameMode()));
			}
		}
		setItems(player, items);
	}

	/**
	 * Removes all displayed items from the list (including the player)
	 *
	 * @param player {@link Player}
	 */
	public static void clearAllItems(@Nonnull Player player) {
		fillDefaultItems(player);
		Tab tab = tabMap.get(player.getUniqueId());
		if (tab != null) {
			tab.clearItems();
		}
	}

	/**
	 * Forces an update for the list
	 *
	 * @param player {@link Player}
	 */
	public static void updateTab(@Nonnull Player player) {
		Tab tab = tabMap.get(player.getUniqueId());
		if (tab != null) {
			tab.updateItems();
			tab.updateHeaderFooter();
		}
	}

	/**
	 * Removes a player's tab list (the default one will be used)
	 *
	 * @param player {@link Player}
	 */
	public static void removeTab(@Nonnull Player player) {
		tabMap.remove(player.getUniqueId());
	}

	//A ton of horrible boring stuff here.

	protected static String convertJSON(String noJson) {
		if (noJson == null) { return null; }
		if (noJson.startsWith("{") && noJson.endsWith("}")) {
			return noJson;
		}
		return String.format("{\"text\":\"%s\"}", noJson);
	}

	protected static String[] convertJSON(String... noJson) {
		if (noJson == null) { return null; }
		String[] converted = new String[noJson.length];
		for (int i = 0; i < noJson.length; i++) {
			if (i != 0 && (!noJson[i].startsWith("{") || !noJson[i].endsWith("}"))) {
				noJson[i] = "\\n" + noJson[i];
			}
			converted[i] = convertJSON(noJson[i]);
		}
		return converted;
	}

}